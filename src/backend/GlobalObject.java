package backend;

import java.util.Map;

/**
 * @author Csongor Kiss
 */
public class GlobalObject extends GameObject {

  public GlobalObject() {
    super(null);
    StatementExecutor executor = new StatementExecutor(this);
    executor.execute("int asd = 4");
    executor.execute("asd = true");
    executor.execute("james = true");
  }

}
