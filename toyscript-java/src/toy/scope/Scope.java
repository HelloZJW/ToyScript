package toy.scope;

import java.util.LinkedList;
import java.util.List;

public abstract class Scope extends Symbol{
    // 该Scope中的成员，包括变量、方法、类等。
    protected List<Symbol> symbols = new LinkedList<Symbol>();

    @Override
    public String toString(){
        return "Scope: " + name;
    }

}

//
////函数作用域
//public class Function extends Scope implements FunctionType{
//
//}
//
////类作用域
//public class Class extends Scope implements Type{
//
//}