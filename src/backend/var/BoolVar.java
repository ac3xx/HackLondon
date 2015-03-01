package backend.var;

import backend.exceptions.StatementException;
import backend.exceptions.VariableTypeMismatchException;

public class BoolVar extends Var<Boolean> {

  @Override
  public void setValue(String newValue) throws StatementException {
      newValue = newValue.trim();
    if (newValue.equals("true")) {
      value = true;
      notifyValueSet();
    } else if (newValue.equals("false")) {
      value = false;
      notifyValueSet();
    } else {
        System.out.println("invalid for bool:" + newValue);
      throw new VariableTypeMismatchException();
    }
  }
}