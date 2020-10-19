package toy.compiler;

import org.antlr.v4.runtime.tree.ParseTreeWalker;
import toy.compiler.type.Function;
import toy.compiler.type.Type;
import toy.parser.ToyScriptParser.*;
import toy.parser.ToyScriptBaseListener;

import java.util.LinkedList;
import java.util.List;

/**
 * 语义解析的第三步：引用消解和类型推断
 * 1.解析所有的本地变量引用、函数调用和类成员引用。
 * 2.类型推断：从下而上推断表达式的类型。
 * 这两件事要放在一起做，因为：
 * (1)对于变量，只有做了消解，才能推断出类型来。
 * (2)对于FunctionCall，只有把参数（表达式)的类型都推断出来，才能匹配到正确的函数（方法)。
 * (3)表达式里包含FunctionCall,所以要推导表达式的类型，必须知道是哪个Function，从而才能得到返回值。
 */
public class RefResolver extends ToyScriptBaseListener {

    private AnnotatedTree at = null;

    ParseTreeWalker typeResolverWalker = new ParseTreeWalker();
    TypeResolver localVariableEnter = null;

    //this()和super()构造函数留到最后去消解，因为它可能引用别的构造函数，必须等这些构造函数都消解完。
    private List<FunctionCallContext> thisConstructorList = new LinkedList<>();
    private List<FunctionCallContext> superConstructorList = new LinkedList<>();

    public RefResolver(AnnotatedTree at) {
        this.at = at;
        localVariableEnter = new TypeResolver(at, true);
    }

    @Override
    public void enterVariableDeclarators(VariableDeclaratorsContext ctx) {
        Scope scope = at.enclosingScopeOfNode(ctx);
        if (scope instanceof BlockScope){
            typeResolverWalker.walk(localVariableEnter, ctx);
        }
    }

    @Override
    public void exitPrimary(PrimaryContext ctx) {
        Scope scope = at.enclosingScopeOfNode(ctx);
        Type type = null;

        //标识符
        if (ctx.IDENTIFIER() != null) {
            String idName = ctx.IDENTIFIER().getText();

            Variable variable = at.lookupVariable(scope, idName);
            if (variable == null) {
                // 看看是不是函数，因为函数可以作为值来传递。这个时候，函数重名没法区分。
                // 因为普通Scope中的函数是不可以重名的，所以这应该是没有问题的。  //TODO 但如果允许重名，那就不行了。
                // TODO 注意，查找function的时候，可能会把类的方法包含进去
                Function function = at.lookupFunction(scope, idName);
                if (function != null) {
                    at.symbolOfNode.put(ctx, function);
                    type = function;
                } else {
                    at.log("unknown variable or function: " + idName, ctx);
                }

            } else {
                at.symbolOfNode.put(ctx, variable);

                type = variable.type;
            }
        }
        //字面量
        else if (ctx.literal() != null) {
            type = at.typeOfNode.get(ctx.literal());
        }
        //括号里的表达式
        else if (ctx.expression() != null) {
            type = at.typeOfNode.get(ctx.expression());
        }
        //类型推断、冒泡
        at.typeOfNode.put(ctx, type);
    }

    @Override
    public void enterFunctionCall(FunctionCallContext ctx) {
        super.enterFunctionCall(ctx);
    }

    @Override
    public void exitFunctionCall(FunctionCallContext ctx) {
        //TODO 临时代码，支持println
        if (ctx.IDENTIFIER().getText().equals("println")) {
            return;
        }

        String idName = ctx.IDENTIFIER().getText();

        // 获得参数类型，这些类型已经在表达式中推断出来
        List<Type> paramTypes = getParamTypes(ctx);

        boolean found = false;

        Scope scope = at.enclosingScopeOfNode(ctx);

        //从当前Scope逐级查找函数(或方法)
        Function function = at.lookupFunction(scope, idName, paramTypes);
        if (function != null) {
            at.symbolOfNode.put(ctx, function);
            at.typeOfNode.put(ctx, function.returnType);
        }
    }

    /**
     * 获得函数的参数列表
     *
     * @param ctx
     * @return
     */
    private List<Type> getParamTypes(FunctionCallContext ctx) {
        List<Type> paramTypes = new LinkedList<Type>();
        if (ctx.expressionList() != null) {
            for (ExpressionContext exp : ctx.expressionList().expression()) {
                Type type = at.typeOfNode.get(exp);
                paramTypes.add(type);
            }
        }
        return paramTypes;
    }

}