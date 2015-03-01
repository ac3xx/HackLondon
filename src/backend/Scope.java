package backend;

import backend.exceptions.StatementException;
import backend.exceptions.VariableAlreadyInScopeException;
import backend.exceptions.VariableNotInScopeException;
import backend.var.Var;

import java.util.HashMap;

/**
 * @author Csongor Kiss
 */
public class Scope {
    private Scope parent;
    protected HashMap<String, Var> variables = new HashMap<String, Var>();
    protected HashMap<String, Method> methods = new HashMap<String, Method>();

    public Scope(Scope parent) {
      //TODO: it can be null
      this.parent = parent;
    }

  /**
   *
   * @param name
   * @param var
   */
  public void addVariable(String name, Var var) throws StatementException{
      if (containsVariable(name)) {
          throw new VariableAlreadyInScopeException();
      }
      variables.put(name, var);
  }

  public void reassignVariable(String name, String value) throws StatementException{
    if (variables.containsKey(name)) {
      variables.get(name).setValue(value);
    } else if (parent != null) {
      parent.reassignVariable(name, value);
    } else {
      throw new VariableNotInScopeException();
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

  public void execute(String changedLine) throws StatementException {
      //TODO: this probably shouldn't even exist
      StatementExecutor executor = new StatementExecutor(this);
      executor.execute(changedLine);
  }
}
