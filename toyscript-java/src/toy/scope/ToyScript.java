package toy.scope;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import toy.parser.ToyScriptLexer;
import toy.parser.ToyScriptParser;
import toy.scope.ASTEvaluator;
import toy.scope.AnnotatedTree;

public class ToyScript {
    public static void main(String args[]) {
        String script = "2+6/2;";
//        String script = "2+6*3;";

        //词法分析
        ToyScriptLexer lexer = new ToyScriptLexer(CharStreams.fromString(script));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        //语法分析
        ToyScriptParser parser = new ToyScriptParser(tokens);
        AnnotatedTree at = new AnnotatedTree();
        at.ast = parser.prog();

        //打印语法树
        System.out.println("The lisp style ast of : " + script);
        System.out.println(at.ast.toStringTree(parser));

        //解释执行
        ASTEvaluator visitor = new ASTEvaluator(at);
        Object result = visitor.visit(at.ast);
        System.out.println("\nValue of : " + script);
        System.out.println(result);
    }
}
