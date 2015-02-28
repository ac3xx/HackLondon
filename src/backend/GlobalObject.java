package backend;

import backend.exceptions.StatementException;
import backend.var.Var;
import backend.var.VarListener;
import frontend.GameWindow;

/**
 * @author Csongor Kiss
 */
public class GlobalObject extends GameObject {

  public GlobalObject() {
    super(null);
  }

  @Override
  public void addVariable(String name, Var var) {
    if (containsVariable(name)) {
      try {
        reassignVariable(name, var.getStringValue());
      } catch (StatementException e) {
        e.printStackTrace();
      }
    }
    variables.put(name, var);
    if (name.equals("light")) {
        var.addListener(new VarListener() {
          @Override
          public void valueSet(Var variable) {
            if (variable.getStringValue().equals("true")) {
              System.out.println("Congrats");
              GameWindow.getInstance().getGamePanel().turnOnTheLight();
            } else {
              System.out.println("No congrats");
              GameWindow.getInstance().getGamePanel().turnOffTheLight();
            }
          }
        });
    }
  }

}
