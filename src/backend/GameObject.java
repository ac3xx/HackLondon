package backend;

import java.util.LinkedList;

/**
 * @author Csongor Kiss
 */
public class GameObject extends Scope {
  private LinkedList<String> codeLines = new LinkedList<String>();

  public GameObject(Scope scope) {
    super(scope);
  }

  public LinkedList<String> getCodeLines() {
    return codeLines;
  }

  public void setCodelines(LinkedList<String> codeLines) {
    this.codeLines = codeLines;
  }
}
