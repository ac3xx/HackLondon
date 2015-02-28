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

    public StatementExecutor(Scope scope, String stmt) {
      this.scope = scope;
    }

    public void execute(String stmt) {
      //int asd = 4;
      Scanner scanner = new Scanner(stmt);
      String next = scanner.next();
      if (types.containsKey(next)) {
          Class c = types.get(next);
        try {
          Var var = (Var) c.newInstance();
          String name = scanner.next();
          scope.addVariable(name, var);
          String equals = scanner.next();
          var.setValue(scanner.next());
        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
}
