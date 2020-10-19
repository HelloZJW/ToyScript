package toy.compiler;

import org.antlr.v4.runtime.ParserRuleContext;
import toy.compiler.type.Function;
import toy.parser.ToyScriptBaseListener;
import toy.parser.ToyScriptParser.*;

import java.util.Stack;

/**
 * 第一遍扫描：识别出所有类型（包括类和函数)，以及Scope。（但函数的参数信息要等到下一个阶段才会添加进去。）
 */
public class TypeAndScopeScanner extends ToyScriptBaseListener {

    private AnnotatedTree at;

    private Stack<Scope> scopeStack = new Stack<>();

    public TypeAndScopeScanner(AnnotatedTree at) {
        this.at = at;
    }

    private Scope pushScope(Scope scope, ParserRuleContext ctx) {
        at.node2Scope.put(ctx, scope);
        scope.ctx = ctx;

        scopeStack.push(scope);
        return scope;
    }

    //从栈中弹出scope
    private void popScope() {
        scopeStack.pop();
    }

    //在遍历树的过程中，当前的Scope
    private Scope currentScope() {
        if (scopeStack.size() > 0) {
            return scopeStack.peek();
        } else {
            return null;
        }
    }

    @Override
    public void enterProg(ProgContext ctx) {
        NameSpace scope = new NameSpace("", currentScope(), ctx);
        at.nameSpace = scope; //scope的根
        pushScope(scope, ctx);
    }

    @Override
    public void exitProg(ProgContext ctx) {
        popScope();
    }


    @Override
    public void enterFunctionDeclaration(FunctionDeclarationContext ctx) {
        String idName = ctx.IDENTIFIER().getText();

        Scope current = currentScope();
        //注意：目前funtion的信息并不完整，参数要等到TypeResolver.java中去确定。
        Function function = new Function(idName, current, ctx);

        current.addSymbol(function);

        // 创建一个新的scope
        pushScope(function, ctx);
    }

    @Override
    public void exitFunctionDeclaration(FunctionDeclarationContext ctx) {
        popScope();
    }
}