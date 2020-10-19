package toy.compiler;

import java.util.LinkedList;
import java.util.List;

public abstract class Scope extends Symbol {
    // 该Scope中的成员，包括变量、方法、类等。
    public List<Symbol> symbols = new LinkedList<Symbol>();

    /**
     * 是否包含某个Symbol
     * @param symbol
     * @return
     */
    public boolean containsSymbol(Symbol symbol){
        return symbols.contains(symbol);
    } /**
     * 向scope中添加符号，同时设置好该符号的enclosingScope
     * @param symbol
     */
    public void addSymbol(Symbol symbol){
        symbols.add(symbol);
        symbol.enclosingScope = this;
    }


    /**
     * 是否包含某个Variable
     * @param name
     * @return
     */
    public Variable getVariable(String name){
        return getVariable(this,name);
    }

    public static Variable getVariable(Scope scope, String name){
        for (Symbol s : scope.symbols) {
            if (s instanceof Variable && s.name.equals(name)){
                return (Variable) s;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return "Scope: " + name;
    }

}