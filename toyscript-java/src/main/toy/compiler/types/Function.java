package main.toy.compiler.types;

import java.util.List;

//函数作用域
public class Function extends Scope implements FunctionType{

    @Override
    public Type getReturnType() {
        return null;
    }

    @Override
    public List<Type> getParamTypes() {
        return null;
    }

    @Override
    public boolean matchParameterTypes(List<Type> paramTypes) {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Scope getEnclosingScope() {
        return null;
    }

    @Override
    public boolean isType(Type type) {
        return false;
    }
}
