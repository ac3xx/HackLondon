package backend.var;

import backend.exceptions.StatementException;
import backend.exceptions.VariableTypeMismatchException;

public class BoolVar extends Var<Boolean> {

  @Override
  public void setValue(String newValue) throws StatementException {
    if (newValue.equals("true")) {
      value = true;
      notifyValueSet();
    } else if (newValue.equals("false")) {
      value = false;
      notifyValueSet();
    } else {
      throw new VariableTypeMismatchException();
    }
  }
}