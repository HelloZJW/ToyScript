package toy.compiler;

import toy.compiler.type.*;
import toy.parser.ToyScriptBaseVisitor;
import toy.parser.ToyScriptParser;
import toy.parser.ToyScriptParser.*;

import java.util.LinkedList;
import java.util.List;
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


    public LValue getLValue(Variable variable) {
        StackFrame f = stack.peek();

        ToyObject valueContainer = null;
        while (f != null) {
            if (f.scope.containsSymbol(variable)) { //对于对象来说，会查找所有父类的属性
                valueContainer = f.object;
                break;
            }
            f = f.parentFrame;
        }

        //通过正常的作用域找不到，就从闭包里找
        //原理：PlayObject中可能有一些变量，其作用域跟StackFrame.scope是不同的。
        if (valueContainer == null){
            f = stack.peek();
            while (f != null) {
                if (f.contains(variable)) {
                    valueContainer = f.object;
                    break;
                }
                f = f.parentFrame;
            }
        }

        MyLValue lvalue = new MyLValue(valueContainer, variable);

        return lvalue;
    }

    //自己实现的左值对象。

    private final class MyLValue implements LValue {
        private Variable variable;
        private ToyObject valueContainer;

        public MyLValue(ToyObject valueContainer, Variable variable) {
            this.valueContainer = valueContainer;
            this.variable = variable;
        }

        @Override
        public Object getValue() {
            return valueContainer.getValue(variable);
        }

        @Override
        public void setValue(Object value) {
            valueContainer.setValue(variable, value);
        }

        @Override
        public Variable getVariable() {
            return variable;
        }

        @Override
        public String toString() {
            return "LValue of " + variable.name + " : " + getValue();
        }

        @Override
        public ToyObject getValueContainer() {
            return valueContainer;
        }
    }

    @Override
    public Object visitBlockStatements(BlockStatementsContext ctx) {
        Object rtn = null;
        for (BlockStatementContext child : ctx.blockStatement()) {
            rtn = visitBlockStatement(child);
        }
        return rtn;
    }

    @Override
    public Object visitProg(ProgContext ctx) {
        Object rtn = null;
        pushStack(new StackFrame((BlockScope) at.node2Scope.get(ctx)));

        rtn = visitBlockStatements(ctx.blockStatements());

        popStack();

        return rtn;
    }

    @Override
    public Object visitPrimary(PrimaryContext ctx) {
        Object rtn = null;
        //字面量
        if (ctx.literal() != null) {
            rtn = visitLiteral(ctx.literal());
        }
        else if (ctx.IDENTIFIER() != null){
            Symbol symbol = at.symbolOfNode.get(ctx);
            if (symbol instanceof Variable){
                rtn = getLValue((Variable) symbol);
            } else if (symbol instanceof Function){
                FunctionObject obj = new FunctionObject((Function) symbol);
                rtn = obj;
            }
        }

        //括号括起来的表达式
        else if (ctx.expression() != null){
            rtn = visitExpression(ctx.expression());
        }
        return rtn;
    }

    @Override
    public Object visitPrimitiveType(PrimitiveTypeContext ctx) {
        Object rtn = null;
        if (ctx.INT() != null) {
            rtn = ToyScriptParser.INT;
        } else if (ctx.LONG() != null) {
            rtn = ToyScriptParser.LONG;
        } else if (ctx.FLOAT() != null) {
            rtn = ToyScriptParser.FLOAT;
        } else if (ctx.DOUBLE() != null) {
            rtn = ToyScriptParser.DOUBLE;
        } else if (ctx.BOOLEAN() != null) {
            rtn = ToyScriptParser.BOOLEAN;
        } else if (ctx.CHAR() != null) {
            rtn = ToyScriptParser.CHAR;
        } else if (ctx.SHORT() != null) {
            rtn = ToyScriptParser.SHORT;
        } else if (ctx.BYTE() != null) {
            rtn = ToyScriptParser.BYTE;
        }
        return rtn;
    }

    /// visit每个节点

    @Override
    public Object visitBlock(BlockContext ctx) {
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
    public Object visitBlockStatement(BlockStatementContext ctx) {
        Object rtn = null;
        if (ctx.variableDeclarators() != null) {
            rtn = visitVariableDeclarators(ctx.variableDeclarators());
        } else if (ctx.statement() != null) {
            rtn = visitStatement(ctx.statement());
        }
        return rtn;
    }


    @Override
    public Object visitStatement(StatementContext ctx){
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
    public Object visitTypeType(TypeTypeContext ctx) {
        return visitPrimitiveType(ctx.primitiveType());
    }

    @Override
    public Object visitVariableInitializer(VariableInitializerContext ctx) {
        Object rtn = null;
        if (ctx.expression() != null) {
            rtn = visitExpression(ctx.expression());
        }
        return rtn;
    }

    @Override
    public Object visitVariableDeclarator(VariableDeclaratorContext ctx) {
        Object rtn = null;
        LValue lValue = (LValue) visitVariableDeclaratorId(ctx.variableDeclaratorId());
        if (ctx.variableInitializer() != null) {
            rtn = visitVariableInitializer(ctx.variableInitializer());
            if (rtn instanceof LValue) {
                rtn = ((LValue) rtn).getValue();
            }
            lValue.setValue(rtn);
        }
        return rtn;
    }

    @Override
    public Object visitVariableDeclaratorId(VariableDeclaratorIdContext ctx) {
        Object rtn = null;

        Symbol symbol = at.symbolOfNode.get(ctx);
        rtn = getLValue((Variable) symbol);
        return rtn;
    }

    @Override
    public Object visitVariableDeclarators(VariableDeclaratorsContext ctx) {
        Object rtn = null;
        // Integer typeType = (Integer)visitTypeType(ctx.typeType()); //后面要利用这个类型信息
        for (VariableDeclaratorContext child : ctx.variableDeclarator()) {
            rtn = visitVariableDeclarator(child);
        }
        return rtn;
    }

    @Override
    public Object visitExpression(ExpressionContext ctx) {
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
        } else if (ctx.functionCall() != null){
            rtn = visitFunctionCall(ctx.functionCall());
        }

        return rtn;
    }

    @Override
    public Object visitLiteral(LiteralContext ctx) {
        Object rtn = null;

        //整数
        if (ctx.integerLiteral() != null) {
            rtn = visitIntegerLiteral(ctx.integerLiteral());
        }

        return rtn;
    }

    @Override
    public Object visitIntegerLiteral(IntegerLiteralContext ctx) {
        Object rtn = null;
        if (ctx.DECIMAL_LITERAL() != null) {
            rtn = Integer.valueOf(ctx.DECIMAL_LITERAL().getText());
        }
        return rtn;
    }


    ///////////////////////////////////////////////////////////
    /// 函数相关

    @Override
    public Object visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        return visitFunctionBody(ctx.functionBody());
    }

    @Override
    public Object visitFunctionBody(FunctionBodyContext ctx) {
        Object rtn = null;
        if (ctx.block() != null) {
            rtn = visitBlock(ctx.block());
        }
        return rtn;
    }

    @Override
    public Object visitFunctionCall(FunctionCallContext ctx) {
        Object rtn = null;

        String functionName = ctx.IDENTIFIER().getText();  //这是调用时的名称，不一定是真正的函数名，还可能是函数尅性的变量名

        if(functionName.equals("println")){
            // TODO 临时代码，用于打印输出
            println(ctx);
            return rtn;
        }

        //在上下文中查找出函数，并根据需要创建FunctionObject
        FunctionObject functionObject = getFunctionObject(ctx);
        //计算参数值
        List<Object> paramValues = calcParamValues(ctx);

        if (traceFunctionCall){
            System.out.println("\n>>FunctionCall : " + ctx.getText());
        }

        rtn = functionCall(functionObject, paramValues);

        return rtn;
    }


    /**
     * 计算某个函数调用时的参数值
     * @param ctx
     * @return
     */
    private List<Object> calcParamValues(FunctionCallContext ctx){
        List<Object> paramValues = new LinkedList<Object>();
        if (ctx.expressionList() != null) {
            for (ExpressionContext exp : ctx.expressionList().expression()) {
                Object value = visitExpression(exp);
                if (value instanceof LValue) {
                    value = ((LValue) value).getValue();
                }
                paramValues.add(value);
            }
        }
        return paramValues;
    }

    /**
     * 根据函数调用的上下文，返回一个FunctionObject。
     * 对于函数类型的变量，这个functionObject是存在变量里的；
     * 对于普通的函数调用，此时创建一个。
     * @param ctx
     * @return
     */
    private FunctionObject getFunctionObject(FunctionCallContext ctx){
        if (ctx.IDENTIFIER() == null) return null;

        Function function = null;
        FunctionObject functionObject = null;

        Symbol symbol = at.enclosingScopeOfNode(ctx);
        //函数类型的变量
        if (symbol instanceof Variable) {
            Variable variable = (Variable) symbol;
            LValue lValue = getLValue(variable);
            Object value = lValue.getValue();
            if (value instanceof FunctionObject) {
                functionObject = (FunctionObject) value;
                function = functionObject.function;
            }
        }
        //普通函数
        else if (symbol instanceof Function) {
            function = (Function) symbol;
        }
        //报错
        else {
            String functionName = ctx.IDENTIFIER().getText();  //这是调用时的名称，不一定是真正的函数名，还可能是函数类型的变量名
            at.log("unable to find function or function variable " + functionName, ctx);
            return null;
        }

        if (functionObject == null) {
            functionObject = new FunctionObject(function);
        }

        return functionObject;
    }

    /**
     * 执行一个函数的方法体。需要先设置参数值，然后再执行代码。
     * @param functionObject
     * @param paramValues
     * @return
     */
    private Object functionCall(FunctionObject functionObject, List<Object> paramValues){
        Object rtn = null;

        //添加函数的栈桢
        StackFrame functionFrame = new StackFrame(functionObject);
        pushStack(functionFrame);

        // 给参数赋值，这些值进入functionFrame
        FunctionDeclarationContext functionCode = (FunctionDeclarationContext) functionObject.function.ctx;
        if (functionCode.formalParameters().formalParameterList() != null) {
            for (int i = 0; i < functionCode.formalParameters().formalParameterList().formalParameter().size(); i++) {
                FormalParameterContext param = functionCode.formalParameters().formalParameterList().formalParameter(i);
                LValue lValue = (LValue) visitVariableDeclaratorId(param.variableDeclaratorId());
                lValue.setValue(paramValues.get(i));
            }
        }

        // 调用函数（方法）体
        rtn = visitFunctionDeclaration(functionCode);

        // 弹出StackFrame
        popStack(); //函数的栈桢

        //如果由一个return语句返回，真实返回值会被封装在一个ReturnObject里。
        if (rtn instanceof ReturnObject){
            rtn = ((ReturnObject)rtn).returnValue;
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


    //自己硬编码的println方法
    private void println(FunctionCallContext ctx){
        if (ctx.expressionList() != null) {
            Object value = visitExpressionList(ctx.expressionList());
            if (value instanceof LValue) {
                value = ((LValue) value).getValue();
            }
            System.out.println(value);
        }
        else{
            System.out.println();
        }
    }
}