package toy.compiler.type;

import toy.compiler.Scope;

public class ClassType extends Scope implements Type {
    //父类
    private ClassType parentClassType = null; //= rootClass;


    @Override
    public boolean isType(Type type) {
        return false;
    }
}