package main.toy.compiler;

import main.toy.ToyScriptLexer;
import main.toy.ToyScriptParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class ToyScriptCompiler {
    AnnotatedTree at;
    ToyScriptLexer lexer;
    ToyScriptParser parser;

    public void compile(String script){
        at = new AnnotatedTree();

        // 1. 词法分析，获得 Tokens
        ToyScriptLexer lexer = new ToyScriptLexer(CharStreams.fromString(script));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 2. 语法分析
        parser = new ToyScriptParser(tokens);
        at.ast = parser.prog();

        // 3. 语义分析
        ParseTreeWalker walker = new ParseTreeWalker();

        //多步的语义解析。
        //优点：1.代码更清晰；2.允许使用在声明之前，这在支持面向对象、递归函数等特征时是必须的。
        dumpAST();
        //pass1：类型和Scope
        TypeAndScopeScanner pass1 = new TypeAndScopeScanner(at);
        walker.walk(pass1, at.ast);
        dumpAST();
    }


    /**
     * 打印AST，以lisp格式
     */
    public void dumpAST(){
        if (at!=null) {
            System.out.println(at.ast.toStringTree(parser));
        }
    }

}
