package main.toy.compiler.types;

import java.util.LinkedList;
import java.util.List;

//作用域
public abstract class Scope extends Symbol{
    // 该Scope中的成员，包括变量、方法、类等。
    protected List<Symbol> symbols = new LinkedList<Symbol>();
}
