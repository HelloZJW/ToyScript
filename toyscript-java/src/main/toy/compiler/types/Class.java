package main.toy.compiler.types;

//类作用域
public class Class extends Scope implements Type{

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
