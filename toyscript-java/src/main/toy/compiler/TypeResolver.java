package main.toy.compiler;

import main.toy.ToyScriptBaseListener;

public class TypeResolver  extends ToyScriptBaseListener {
    private AnnotatedTree at = null;

    //是否把本地变量加入符号表
    private boolean enterLocalVariable = false;

    public TypeResolver(AnnotatedTree at) {
        this.at = at;
    }
}
