package backend;

/**
 * @author Csongor Kiss
 */
public class FloatVar extends Var<Float> {

  @Override
  public boolean setValue(String newValue) {
    try {
      value = Float.parseFloat(newValue);
      notifyValueChanged();
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
