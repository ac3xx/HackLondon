package backend;

import java.lang.reflect.Type;
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

    public void execute(String stmt) {
      Scanner scanner = new Scanner(stmt);
      String next = scanner.next();
      if (types.containsKey(next)) {
          //new variable assignment
          Class c = types.get(next);
        try {
          Var var = (Var) c.newInstance();
          String name = scanner.next();
          String equals = scanner.next();
          String value = scanner.next();
          if(var.setValue(value)) {
            if(!scope.addVariable(name, var)) {
                System.out.println(name + " is already in the scope");
            }
          } else {
            System.out.println(name + " does not accept value: " + value);
          }
        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
}
