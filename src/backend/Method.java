package backend;

import backend.exceptions.StatementException;

/**
 * @author Csongor Kiss
 */
public class Method extends Scope {
  private Block block;

  public Method(Scope parent) {
    super(parent);
  }

  public void execute() throws StatementException {
    StatementExecutor executor = new StatementExecutor(this);
    for (String stmt : block.getStatements()) {
      executor.execute(stmt);
    }
  }

  public void setBlock(Block block) {
    this.block = block;
  }
}
