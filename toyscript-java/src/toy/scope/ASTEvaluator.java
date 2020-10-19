package toy.scope;

import toy.parser.ToyScriptBaseVisitor;
import toy.parser.ToyScriptParser;
import java.util.Stack;

public class ASTEvaluator extends ToyScriptBaseVisitor<Object> {
    // 之前的编译结果
    private AnnotatedTree at = null;

    // 堆，用于保存对象
    public ASTEvaluator(AnnotatedTree at) {
        this.at = at;
    }

    protected boolean traceStackFrame = false;

    protected boolean traceFunctionCall = false;

    ///////////////////////////////////////////////////////////
    /// 栈桢的管理
    private Stack<StackFrame> stack = new Stack<StackFrame>();

    /**
     * 栈桢入栈。
     * 其中最重要的任务，是要保证栈桢的parentFrame设置正确。否则，
     * (1)随着栈的变深，查找变量的性能会降低；
     * (2)甚至有可能找错栈桢，比如在递归(直接或间接)的场景下。
     * @param frame
     */
    private void pushStack(StackFrame frame) {
        if (stack.size() > 0) {
            //从栈顶到栈底依次查找
            for (int i = stack.size()-1; i>0; i--){
                StackFrame f = stack.get(i);

                /*
                如果新加入的栈桢，跟某个已有的栈桢的enclosingScope是一样的，那么这俩的parentFrame也一样。
                因为它们原本就是同一级的嘛。
                比如：
                void foo(){};
                void bar(foo());

                或者：
                void foo();
                if (...){
                    foo();
                }
                */
                if (f.scope.enclosingScope == frame.scope.enclosingScope){
                    frame.parentFrame = f.parentFrame;
                    break;
                }

                /*
                如果新加入的栈桢，是某个已有的栈桢的下一级，那么就把把这个父子关系建立起来。比如：
                void foo(){
                    if (...){  //把这个块往栈桢里加的时候，就符合这个条件。
                    }
                }
                再比如,下面的例子:
                class MyClass{
                    void foo();
                }
                MyClass c = MyClass();  //先加Class的栈桢，里面有类的属性，包括父类的
                c.foo();                //再加foo()的栈桢
                 */
                else if (f.scope == frame.scope.enclosingScope){
                    frame.parentFrame = f;
                    break;
                }

                /*
                这是针对函数可能是一等公民的情况。这个时候，函数运行时的作用域，与声明时的作用域会不一致。
                我在这里设计了一个“receiver”的机制，意思是这个函数是被哪个变量接收了。要按照这个receiver的作用域来判断。
                 */
//                else if (frame.object instanceof FunctionObject){
//                    FunctionObject functionObject = (FunctionObject)frame.object;
//                    if (functionObject.receiver != null && functionObject.receiver.enclosingScope == f.scope) {
//                        frame.parentFrame = f;
//                        break;
//                    }
//                }
            }

            if (frame.parentFrame == null){
                frame.parentFrame = stack.peek();
            }
        }

        stack.push(frame);

        if (traceStackFrame){
            dumpStackFrame();
        }
    }

    private void popStack(){
        stack.pop();
    }

    private void dumpStackFrame(){
        System.out.println("\nStack Frames ----------------");
        for (StackFrame frame : stack){
            System.out.println(frame);
        }
        System.out.println("-----------------------------\n");
    }

    @Override
    public Object visitBlockStatements(ToyScriptParser.BlockStatementsContext ctx) {
        Object rtn = null;
        for (ToyScriptParser.BlockStatementContext child : ctx.blockStatement()) {
            rtn = visitBlockStatement(child);
        }
        return rtn;
    }

    @Override
    public Object visitPrimary(ToyScriptParser.PrimaryContext ctx) {
        Object rtn = null;
        //字面量
        if (ctx.literal() != null) {
            rtn = visitLiteral(ctx.literal());
        }

        //括号括起来的表达式
        else if (ctx.expression() != null){
            rtn = visitExpression(ctx.expression());
        }
        return rtn;
    }

    /// visit每个节点

    @Override
    public Object visitBlock(ToyScriptParser.BlockContext ctx) {
        BlockScope scope = (BlockScope) at.node2Scope.get(ctx);
        if (scope != null){  //有些block是不对应scope的，比如函数底下的block.
            StackFrame frame = new StackFrame(scope);
            frame.parentFrame = stack.peek();
            pushStack(frame);
        }


        Object rtn = visitBlockStatements(ctx.blockStatements());

        if (scope !=null) {
            popStack();
        }

        return rtn;
    }

    @Override
    public Object visitBlockStatement(ToyScriptParser.BlockStatementContext ctx) {
        Object rtn = null;
        if (ctx.variableDeclarators() != null) {
            rtn = visitVariableDeclarators(ctx.variableDeclarators());
        } else if (ctx.statement() != null) {
            rtn = visitStatement(ctx.statement());
        }
        return rtn;
    }


    @Override
    public Object visitStatement(ToyScriptParser.StatementContext ctx){
        Object rtn = null;
        if (ctx.statementExpression != null) {
            rtn = visitExpression(ctx.statementExpression);
        } else if (ctx.IF() != null) {
            Boolean condition = (Boolean) visitParExpression(ctx.parExpression());
            if (Boolean.TRUE == condition) {
                rtn = visitStatement(ctx.statement(0));
            } else if (ctx.ELSE() != null) {
                rtn = visitStatement(ctx.statement(1));
            }
        }

        return rtn;
    }

    @Override
    public Object visitExpression(ToyScriptParser.ExpressionContext ctx) {
        Object rtn = null;
        if (ctx.bop != null && ctx.expression().size() >= 2) {
            Object left = visitExpression(ctx.expression(0));
            Object right = visitExpression(ctx.expression(1));
            Object leftObject = left;
            Object rightObject = right;

            //本节点期待的数据类型
            Type type = at.typeOfNode.get(ctx);

            switch (ctx.bop.getType()) {
                case ToyScriptParser.ADD:
                    rtn = add(leftObject, rightObject, type);
                    break;
                case ToyScriptParser.SUB:
                    rtn = minus(leftObject, rightObject, type);
                    break;
                case ToyScriptParser.MUL:
                    rtn = mul(leftObject, rightObject, type);
                    break;
                case ToyScriptParser.DIV:
                    rtn = div(leftObject, rightObject, type);
                    break;
                default:
                    break;
            }
        } else if (ctx.primary() != null) {
            rtn = visitPrimary(ctx.primary());
        }

        return rtn;
    }


    @Override
    public Object visitLiteral(ToyScriptParser.LiteralContext ctx) {
        Object rtn = null;

        //整数
        if (ctx.integerLiteral() != null) {
            rtn = visitIntegerLiteral(ctx.integerLiteral());
        }

        return rtn;
    }

    @Override
    public Object visitIntegerLiteral(ToyScriptParser.IntegerLiteralContext ctx) {
        Object rtn = null;
        if (ctx.DECIMAL_LITERAL() != null) {
            rtn = Integer.valueOf(ctx.DECIMAL_LITERAL().getText());
        }
        return rtn;
    }

    ///////////////////////////////////////////////////////////
    /// 各种运算
    private Object add(Object obj1, Object obj2, Type targetType) {
        Object rtn = ((Number) obj1).intValue() + ((Number) obj2).intValue();
        return rtn;
    }

    private Object minus(Object obj1, Object obj2, Type targetType) {
        Object rtn = ((Number) obj1).intValue() - ((Number) obj2).intValue();;
        return rtn;
    }

    private Object mul(Object obj1, Object obj2, Type targetType) {
        Object rtn = ((Number) obj1).intValue() * ((Number) obj2).intValue();
        return rtn;
    }

    private Object div(Object obj1, Object obj2, Type targetType) {
        Object rtn = ((Number) obj1).intValue() / ((Number) obj2).intValue();
        return rtn;
    }
}