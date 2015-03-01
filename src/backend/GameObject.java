package backend;

import java.util.LinkedList;

/**
 * @author Csongor Kiss
 */
public class GameObject extends Scope {
  private String textureName;

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

  public String getTextureName() {
    return textureName;
  }

  public void setTextureName(String textureName) {
    this.textureName = textureName;
  }
}
