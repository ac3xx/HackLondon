package backend;

import java.util.HashMap;

/**
 * @author Csongor Kiss
 */
public class Scope {
    private Scope parent;
    protected HashMap<String, Var> variables = new HashMap<String, Var>();

    public Scope(Scope parent) {
      //TODO: it can be null
      this.parent = parent;
    }

  /**
   *
   * @param name
   * @param var
   * @return true, if the variable is added
   *         false, if the variable is already in the scope
   */
  public boolean addVariable(String name, Var var) {
      if (containsVariable(name)) {
        return false;
      }
      variables.put(name, var);
      return true;
  }

  public boolean reassignVariable(String name, String value) {
    if (variables.containsKey(name)) {
      return variables.get(name).setValue(value);
    }
    if (parent != null) {
      return parent.reassignVariable(name, value);
    } else {
      //TODO: throw exception
      return false;
    }
  }

  public Var getVariableFromScope(String name) {
      if (variables.containsKey(name)) return variables.get(name);
      if (parent != null) {
        return parent.getVariableFromScope(name);
      } else {
          //TODO: throw exception
          return null;
      }
  }

  public boolean containsVariable(String name) {
    return variables.containsKey(name) || parent != null && parent.containsVariable(name);
  }
}
