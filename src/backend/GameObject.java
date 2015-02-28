package backend;

import java.util.LinkedList;

/**
 * @author Csongor Kiss
 */
public class GameObject {
  private LinkedList<String> codeLines = new LinkedList<String>();

  public LinkedList<String> getCodeLines() {
    return codeLines;
  }

  public void setCodelines(LinkedList<String> codeLines) { this.codeLines = codeLines;}
}
