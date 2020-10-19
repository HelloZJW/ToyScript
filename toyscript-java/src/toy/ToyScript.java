package toy;

import toy.compiler.*;

public class ToyScript {
    public static void main(String args[]) {
//        String script = "int a =2+6/2; println(a);";
//        String script = "2+6*3;";
        String script = "int b= 10; int myfunc(int a) {return a+b+3;} myfunc(2);";

        ToyScriptCompiler compiler = new ToyScriptCompiler();
        compiler.compile(script);
    }
}
