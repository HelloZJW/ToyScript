package toy.compiler;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import toy.parser.ToyScriptLexer;
import toy.parser.ToyScriptParser;

public class ToyScriptCompiler {
    AnnotatedTree at;
    ToyScriptLexer lexer;
    ToyScriptParser parser;

    public AnnotatedTree compile(String script) {
        at = new AnnotatedTree();

        //词法分析
        lexer = new ToyScriptLexer(CharStreams.fromString(script));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        //语法分析
        parser = new ToyScriptParser(tokens);
        at.ast = parser.prog();

        //语义分析
        ParseTreeWalker walker = new ParseTreeWalker();

        //多步的语义解析。
        //优点：1.代码更清晰；2.允许使用在声明之前，这在支持面向对象、递归函数等特征时是必须的。
        //pass1：类型和Scope
        TypeAndScopeScanner pass1 = new TypeAndScopeScanner(at);
        walker.walk(pass1, at.ast);

        //pass2：把变量、类继承、函数声明的类型都解析出来。也就是所有声明时用到类型的地方。
        TypeResolver pass2 = new TypeResolver(at);
        walker.walk(pass2, at.ast);

        //pass3：消解有的变量应用、函数引用。另外还做了类型的推断。
//        RefResolver pass3 = new RefResolver(at);
//        walker.walk(pass3,at.ast);

        //pass4：类型检查
//        TypeChecker pass4 = new TypeChecker(at);
//        walker.walk(pass4,at.ast);

        //pass5：其他语义检查
//        SematicValidator pass5 = new SematicValidator(at);
//        walker.walk(pass5,at.ast);

        //pass6：做闭包的分析
//        ClosureAnalyzer closureAnalyzer = new ClosureAnalyzer(at);
//        closureAnalyzer.analyzeClosures();

        //打印语法树
        dumpAST();

        //解释执行
        ASTEvaluator visitor = new ASTEvaluator(at);
        Object result = visitor.visit(at.ast);
        System.out.println("\nValue of : " + script);
        System.out.println(result);

        return at;
    }


    /**
     * 打印符号表
     */
    public void dumpSymbols(){
        if (at != null){
            System.out.println(at.getScopeTreeString());
        }
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
