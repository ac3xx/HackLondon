package backend;

import backend.control.If;
import backend.exceptions.IllegalStatementException;
import backend.exceptions.StatementException;
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
    private static Map<String, Class> types = new HashMap<>();
    private static Set<String> control = new HashSet<>();
    static {
        types.put("int", IntVar.class);
        types.put("boolean", BoolVar.class);
        types.put("float", FloatVar.class);
        
        control.add("if");
        control.add("for");
        control.add("while");
        
    }

    public StatementExecutor(Scope scope) {
      this.scope = scope;
    }

    public void execute(String stmt) throws StatementException {
      if (!stmt.contains(";") && !stmt.contains("}")) throw new IllegalStatementException();

      stmt = stmt.substring(0, stmt.indexOf(';'));

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
        } catch (NoSuchElementException e) {
          //TODO: maybe do something about this?
        }
      } else if (scope.containsVariable(first)) {
          Var var = scope.getVariableFromScope(first);
          String equals = scanner.next();
          String value = scanner.next();
          var.setValue(value);
      } else if (control.contains(first)) {
          try {
              String condition = scanner.next();
              switch(first) {
                  case "if":

                      break;
                  case "for":
                      break;
                  case "while":
                      break;
                  default:
                      break;

              }
          } catch (NoSuchElementException e) {
              //TODO: maybe do something about this?
          }
      }
    }
}
