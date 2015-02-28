package backend;

import frontend.GamePanel;
import frontend.GamePanelListener;
import frontend.Terminal;
import frontend.TerminalListener;

import java.util.LinkedList;

public class GameEngine implements TerminalListener, GamePanelListener {
  private Terminal terminal;
  private GamePanel gamePanel;
  private GameObject currentGameObject;

  public GameEngine(Terminal terminal, GamePanel gamePanel) {
    this.terminal = terminal;
    this.gamePanel = gamePanel;
    terminal.setListener(this);
    gamePanel.setListener(this);
    currentGameObject = new GameObject();
  }

  @Override
  public void gameObjectSelected(GameObject object) {
    currentGameObject = object;
  }

  public void setCurrentGameObject(GameObject currentGameObject) {
    this.currentGameObject = currentGameObject;
  }

  public LinkedList<String> getCodeLines() {
    return currentGameObject.getCodeLines();
  }

  public void setCodelines(LinkedList<String> codeLines) {currentGameObject.setCodelines(codeLines);}

  public boolean isValidLine(String line) {

    // we need some game logic in here to check validity of the line

    return true;
  }
}