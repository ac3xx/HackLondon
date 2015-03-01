package backend.var;

import backend.var.Var;

/**
 * @author Csongor Kiss
 */
@FunctionalInterface
public interface VarListener {
  void valueSet(Var variable);
}
