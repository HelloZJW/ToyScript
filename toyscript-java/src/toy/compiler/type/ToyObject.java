package toy.compiler.type;

import toy.compiler.Variable;

import java.util.HashMap;
import java.util.Map;

/**
 * ToyScript的对象
 */
public class ToyObject {
    //成员变量
    public Map<Variable, Object> fields = new HashMap<Variable, Object>();


    public Object getValue(Variable variable){
        Object rtn = fields.get(variable);
        //TODO 父类的属性如何返回？还是说都在这里了？

        //替换成自己的NullObject
//        if (rtn == null){
//            rtn = NullObject.instance();
//        }
        return rtn;
    }

    public void setValue(Variable variable, Object value){
        fields.put(variable, value);
    }
}