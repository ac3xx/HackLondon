package backend.var;

import backend.exceptions.StatementException;
import backend.exceptions.VariableTypeMismatchException;

/**
 * @author Csongor Kiss
 */
public class IntVar extends Var<Integer> {
  @Override
  public void setValue(String newValue) throws StatementException {
    try {
      value = Integer.parseInt(newValue);
      notifyValueSet();
    } catch (NumberFormatException e) {
      throw new VariableTypeMismatchException();
    }
  }

    public void setValue(int i) {
        this.value = i;
        notifyValueSet();
    }
}
