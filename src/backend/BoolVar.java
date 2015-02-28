package backend;

public class BoolVar extends Var<Boolean> {

  @Override
  public boolean setValue(String newValue) {
    if (newValue.equals("true")) {
      value = true;
      notifyValueChanged();
      return true;
    }
    if (newValue.equals("false")) {
      value = false;
      notifyValueChanged();
      return true;
    }
    return false;
  }
}