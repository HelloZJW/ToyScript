package toy.scope;

/**
 * 对栈中的值的引用
 */
public interface LValue {

    public Object getValue();

    public void setValue(Object value);

    public Variable getVariable();

    public ToyObject getValueContainer();
    //public StackFrame getFrame();
}