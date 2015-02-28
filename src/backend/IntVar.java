package backend;

/**
 * @author Csongor Kiss
 */
public class IntVar extends Var<Integer> {
  @Override
  public boolean setValue(String newValue) {
    try {
      value = Integer.parseInt(newValue);
      notifyValueChanged();
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
