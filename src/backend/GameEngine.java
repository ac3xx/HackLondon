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
}