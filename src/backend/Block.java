package backend;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Csongor Kiss
 */
public class Block {
  private List<String> statements = new ArrayList<String>();

  public void addStatement(String newStatement) {
      statements.add(newStatement);
  }

  public List<String> getStatements() {
    return statements;
  }
}
