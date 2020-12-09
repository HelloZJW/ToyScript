package main.toy.compiler;

import main.toy.ToyScriptBaseListener;
import main.toy.ToyScriptParser;

public class TypeAndScopeScanner extends ToyScriptBaseListener {
    private AnnotatedTree at;

    public TypeAndScopeScanner(AnnotatedTree at){
        this.at = at;
    }

    @Override
    public void enterProg(ToyScriptParser.ProgContext ctx) {
        super.enterProg(ctx);
    }

    @Override
    public void exitProg(ToyScriptParser.ProgContext ctx) {

    }


    @Override
    public void enterFunctionDeclaration(ToyScriptParser.FunctionDeclarationContext ctx) {
        String idName = ctx.IDENTIFIER().getText();
    }

    @Override
    public void exitFunctionDeclaration(ToyScriptParser.FunctionDeclarationContext ctx) {

    }
}
