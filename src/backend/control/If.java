package backend.control;

import backend.Block;
import backend.Scope;

/**
 * @author Csongor Kiss
 */
public class If extends Scope {
  private boolean condition;

  //TODO: else if
  public If(Scope parent, boolean condition, Block trueBlock, Block falseBlock) {
    super(parent);

  }
}
