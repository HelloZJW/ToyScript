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

    public static class Function extends Scope implements FunctionType {
        // 参数
        protected List<Variable> parameters = new LinkedList<Variable>();

        //返回值
        protected Type returnType = null;

        private List<Type> paramTypes = null;

        public Function(String name, Scope enclosingScope, ParserRuleContext ctx) {
            this.name = name;
            this.enclosingScope = enclosingScope;
            this.ctx = ctx;
        }

        @Override
        public Type getReturnType() {
            return returnType;
        }

        @Override
        public List<Type> getParamTypes() {
            if (paramTypes == null) {
                paramTypes = new LinkedList<Type>();
            }

            for (Variable param : parameters) {
                paramTypes.add(param.type);
            }

            return paramTypes;
        }

        @Override
        public boolean matchParameterTypes(List<Type> paramTypes) {
            return false;
        }

        @Override
        public String toString(){
            return "Function " + name;
        }

        @Override
        public boolean isType(Type type) {
            return false;
        }
    }
}