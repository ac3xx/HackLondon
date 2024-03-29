package backend.var;

import backend.exceptions.StatementException;

import java.util.HashSet;
import java.util.Set;

public abstract class Var<T> {
    protected T value;
    private Set<VarListener> listeners = new HashSet<VarListener>();
    protected String name;

    public static enum Type {
        INTEGER, FLOAT, BOOL;

        public static Var varInstance(Type t) {
            switch (t) {
                case INTEGER:
                    return new IntVar();
                case FLOAT:
                    return new FloatVar();
                case BOOL:
                    return new BoolVar();
                default:
                    return null;
            }
        }
    }

  /**
   *
   * @param newValue
   */
    public abstract void setValue(String newValue) throws StatementException;

    public void addListener(VarListener listener) {
      listeners.add(listener);
      //TODO: think of a better way?
      listener.valueSet(this);
    }

    public void removeListener(VarListener listener) {
        listeners.remove(listener);
    }

    protected void notifyValueSet() {
        for (VarListener listener : listeners) {
            listener.valueSet(this);
        }
    }

    public T getValue() {
        return value;
    }

    public String getStringValue() {
      return value.toString();
    }

    public String getName() {
      return name;
    }

  public void setName(String name) {
    this.name = name;
  }

}