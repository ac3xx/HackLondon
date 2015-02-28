package backend;

import java.util.Map;

/**
 * @author Csongor Kiss
 */
public class GlobalObject extends GameObject {

  public GlobalObject() {
    super(null);
    StatementExecutor executor = new StatementExecutor(this);
    for (Map.Entry<String, Var> entry : variables.entrySet())  {
      System.out.println(entry.getKey() + " = " + entry.getValue().getStringValue());
    }
  }

}
