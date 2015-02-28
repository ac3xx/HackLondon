package backend;

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
          if(var.setValue(value)) {
            if(!scope.addVariable(name, var)) {
                System.out.println(name + " is already in the scope");
            } else {
                System.out.println(var.getName() + " now has the value: " + value);
            }
          } else {
            System.out.println(name + " does not accept value: " + value);
          }
        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      } else if (scope.containsVariable(first)) {
          Var var = scope.getVariableFromScope(first);
          String equals = scanner.next();
          String value = scanner.next();
          if (!var.setValue(value)) {
            System.out.println(var.getName() + " does not accept value: " + value);
          } else {
            System.out.println(var.getName() + " now has the value: " + value);
          }
      } else {
          System.out.println(first + " is not in the scope");
      }
    }
}
