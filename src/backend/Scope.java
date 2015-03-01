package backend;

import backend.control.Method;
import backend.exceptions.*;
import backend.var.Var;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Csongor Kiss
 */
public class Scope {
    protected Scope parent;
    protected HashMap<String, Var> variables = new HashMap<>();
    protected HashMap<String, Method> methods = new HashMap<>();

    public Scope(Scope parent) {
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

    public void addMethod(String name, Method method) throws MethodAlreadyInScopeException {
        if (containsMethod(name)) {
            throw new MethodAlreadyInScopeException();
        }
        methods.put(name, method);
    }

    public boolean containsMethod(String name) {
        return methods.containsKey(name) || parent != null && parent.containsMethod(name);
    }

    public Method getMethod(String name) throws MethodNotInScopeException {
        if (methods.containsKey(name)) return methods.get(name);
        if ( parent != null) {
            return parent.getMethod(name);
        } else {
            throw new MethodNotInScopeException();
        }
    }

  public Var getVariableFromScope(String name) throws VariableNotInScopeException {
      if (variables.containsKey(name)) return variables.get(name);
      if (parent != null) {
        return parent.getVariableFromScope(name);
      } else {
          throw new VariableNotInScopeException();
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

    @Override
    public String toString() {
        String ret = "";
        for (Map.Entry<String, Var> entry : variables.entrySet()) {
            ret += entry.getKey() + " = " + entry.getValue().getStringValue() + "\n";
        }
        return ret;
    }

    public void removeMethod(String methodName) {
        methods.remove(methodName);
    }
}
