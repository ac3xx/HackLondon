package backend.var;

import backend.exceptions.StatementException;
import backend.exceptions.VariableAlreadyInScopeException;
import backend.exceptions.VariableTypeMismatchException;
import backend.var.Var;

/**
 * @author Csongor Kiss
 */
public class FloatVar extends Var<Float> {

  @Override
  public void setValue(String newValue) throws StatementException {
    try {
      value = Float.parseFloat(newValue);
      notifyValueSet();
    } catch (NumberFormatException e) {
      throw new VariableTypeMismatchException();
    }
  }
}
