package toy;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import toy.parser.ToyScriptLexer;
import toy.parser.ToyScriptParser;
import toy.scope.*;

public class ToyScript {
    public static void main(String args[]) {
        String script = "int a =2+6/2;";
//        String script = "2+6*3;";

        ToyScriptCompiler compiler = new ToyScriptCompiler();
        compiler.compile(script);
    }
}
