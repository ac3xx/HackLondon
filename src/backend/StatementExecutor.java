package backend;

import backend.exceptions.StatementException;
import backend.exceptions.VariableAlreadyInScopeException;
import backend.exceptions.VariableTypeMismatchException;
import backend.var.BoolVar;
import backend.var.FloatVar;
import backend.var.IntVar;
import backend.var.Var;

import java.util.*;

/**
 * @author Csongor Kiss
 */
public class StatementExecutor {
    private Scope scope;
    private static Map<String, Class> types = new HashMap<String, Class>();
    static {
        types.put("int", IntVar.class);
        types.put("boolean", BoolVar.class);
        types.put("float", FloatVar.class);
    }

    public StatementExecutor(Scope scope) {
      this.scope = scope;
    }

    public void execute(String stmt) throws StatementException {
      Scanner scanner = new Scanner(stmt);
      String first = scanner.next();
      if (types.containsKey(first)) {
          //new variable assignment
          Class c = types.get(first);
        try {
          Var var = (Var) c.newInstance();
          String name = scanner.next();
          var.setName(name);
          String equals = scanner.next();
          String value = scanner.next();
          var.setValue(value);
          scope.addVariable(name, var);
        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      } else if (scope.containsVariable(first)) {
          Var var = scope.getVariableFromScope(first);
          String equals = scanner.next();
          String value = scanner.next();
          var.setValue(value);
      }
    }
}
