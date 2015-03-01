package backend;

import backend.exceptions.StatementException;
import backend.var.Var;
import backend.var.VarListener;
import frontend.GameWindow;

import java.util.Scanner;

/**
 * @author Csongor Kiss
 */
public class GlobalObject extends GameObject {
    private static boolean firstPartCompleted = false;
    private VarListener toggleLightListener = variable -> {
        if (variable.getValue().equals(true)) {
            if (!firstPartCompleted) {
                GameWindow.getInstance().getTerminal().addCodeLine("Good job");
            }
            firstPartCompleted = true;
        }
    };

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
        var.addListener(variable -> {
          if (variable.getValue().equals(true)) {
            GameWindow.getInstance().getGamePanel().turnOnTheLight();
          } else {
            GameWindow.getInstance().getGamePanel().turnOffTheLight();
          }
        });
        var.addListener(toggleLightListener);
    }
  }

}
