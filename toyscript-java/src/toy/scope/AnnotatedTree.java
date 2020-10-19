package toy.scope;

import java.util.*;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;


/**
 * 注释树。
 * 语义分析的结果都放在这里。跟AST的节点建立关联。包括：
 * 1.类型信息，包括基本类型和用户自定义类型；
 * 2.变量和函数调用的消解；
 * 3.作用域Scope。在Scope中包含了该作用域的所有符号。Variable、Function、Class等都是符号。
 */
public class AnnotatedTree {
    protected AnnotatedTree() {
    }

    ;
    // AST
    protected ParseTree ast = null;

    // 解析出来的所有类型，包括类和函数，以后还可以包括数组和枚举。类的方法也作为单独的要素放进去。
    protected List<Type> types = new LinkedList<Type>();

    // AST节点对应的Symbol
    protected Map<ParserRuleContext, Symbol> symbolOfNode = new HashMap<ParserRuleContext, Symbol>();

    // AST节点对应的Scope，如for、函数调用会启动新的Scope
    protected Map<ParserRuleContext, Scope> node2Scope = new HashMap<ParserRuleContext, Scope>();

    // 用于做类型推断，每个节点推断出来的类型
    protected Map<ParserRuleContext, Type> typeOfNode = new HashMap<ParserRuleContext, Type>();

    // 命名空间
    NameSpace nameSpace = null;  //全局命名空间

    /**
     * 输出本Scope中的内容，包括每个变量的名称、类型。
     *
     * @return 树状显示的字符串
     */
    public String getScopeTreeString() {
        StringBuffer sb = new StringBuffer();
        scopeToString(sb, nameSpace, "");
        return sb.toString();
    }

    /**
     * 查找某节点所在的Scope
     * 算法：逐级查找父节点，找到一个对应着Scope的上级节点
     *
     * @param node
     * @return
     */
    public Scope enclosingScopeOfNode(ParserRuleContext node) {
        Scope rtn = null;
        ParserRuleContext parent = node.getParent();
        if (parent != null) {
            rtn = node2Scope.get(parent);
            if (rtn == null) {
                rtn = enclosingScopeOfNode(parent);
            }

        }

        return rtn;
    }

    private void scopeToString(StringBuffer sb, Scope scope, String indent) {
        sb.append(indent).append(scope).append('\n');
        for (Symbol symbol : scope.symbols) {
            if (symbol instanceof Scope) {
                scopeToString(sb, (Scope) symbol, indent + "\t");
            } else {
                sb.append(indent).append("\t").append(symbol).append('\n');
            }
        }
    }
}