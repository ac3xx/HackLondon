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
      if (variables.containsKey(name)) {
          return false;
      }
      variables.put(name, var);
      return true;
  }
}
