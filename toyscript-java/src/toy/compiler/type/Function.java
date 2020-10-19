package toy.compiler.type;

import org.antlr.v4.runtime.ParserRuleContext;
import toy.compiler.Scope;
import toy.compiler.Variable;

import java.util.LinkedList;
import java.util.List;

public class Function extends Scope implements FunctionType {
    // 参数
    public List<Variable> parameters = new LinkedList<Variable>();

    //返回值
    public Type returnType = null;

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

    /**
     * 检查改函数是否匹配所需的参数。
     * @param paramTypes
     * @return
     */
    @Override
    public boolean matchParameterTypes(List<Type> paramTypes){
        // 比较每个参数
        if (parameters.size() != paramTypes.size()) {
            return false;
        }

        boolean match = true;
        for (int i = 0; i < paramTypes.size(); i++) {
            Variable var = parameters.get(i);
            Type type = paramTypes.get(i);
            if (!var.type.isType(type)) {
                match = false;
                break;
            }
        }

        return match;
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