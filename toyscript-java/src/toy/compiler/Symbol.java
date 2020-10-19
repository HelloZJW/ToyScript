package toy.compiler;

import org.antlr.v4.runtime.ParserRuleContext;
import toy.compiler.type.FunctionType;
import toy.compiler.type.Type;

import java.util.LinkedList;
import java.util.List;

//编译过程中产生的变量、函数、类、块，都被称作符号
public abstract class Symbol {
    //符号的名称
    protected String name = null;

    //所属作用域
    protected Scope enclosingScope = null;

    //可见性，比如public还是private
    protected int visibility = 0;

    //Symbol关联的AST节点
    protected ParserRuleContext ctx = null;

    public String getName(){
        return name;
    }

    public Scope getEnclosingScope(){
        return enclosingScope;
    }
}