package backend;

import java.util.HashSet;
import java.util.Set;

public abstract class Var<T> {
    protected T value;
    private Set<VarListener> listeners = new HashSet<VarListener>();
    protected String name;

  /**
   *
   * @param newValue
   * @return true if the assignment was successful
   *         false if newValue is malformed
   */
    public abstract boolean setValue(String newValue);

    public void setListener(VarListener listener) {
      listeners.add(listener);
    }

    public void removeListener(VarListener listener) {
        listeners.remove(listener);
    }

    protected void notifyValueChanged() {
        for (VarListener listener : listeners) {
            listener.valueChanged(this);
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