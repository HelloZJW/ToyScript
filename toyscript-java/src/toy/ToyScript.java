package toy;

import toy.compiler.*;

public class ToyScript {
    public static void main(String args[]) {
//        String script = "45+10*2;";
//        String script = "int a =45+10*2;";
        //String script = "int age = 44; { int i = 10; age+i;}";
        //String script = "int age = 44; for(int i = 0;i<10;i++) { age = age + 2;} int i = 8;";
//        String script = "int myfunc() {return 2+3;} myfunc();";
        String script = "int b= 10; int myfunc(int a) {return a+b+3;} myfunc(2);";

        ToyScriptCompiler compiler = new ToyScriptCompiler();
        compiler.compile(script);
    }
}
