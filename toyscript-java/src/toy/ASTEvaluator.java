package toy;

import toy.parser.ToyScriptBaseVisitor;
import toy.parser.ToyScriptParser;
import toy.scope.AnnotatedTree;
import toy.scope.BlockScope;
import toy.scope.StackFrame;

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

    @Override
    public Object visitBlock(ToyScriptParser.BlockContext ctx) {
        BlockScope scope = (BlockScope) at.node2Scope.get(ctx);
        if (scope != null){  //有些block是不对应scope的，比如函数底下的block.
            StackFrame frame = new StackFrame(scope);
            // frame.parentFrame = stack.peek();
//            pushStack(frame);
        }


        Object rtn = visitBlockStatements(ctx.blockStatements());

        if (scope !=null) {
//            popStack();
        }

        return rtn;
    }
}
