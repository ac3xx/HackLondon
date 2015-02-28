package backend;

import backend.exceptions.StatementException;
import frontend.GamePanel;
import frontend.GamePanelListener;
import frontend.Terminal;
import frontend.TerminalListener;

import java.util.LinkedList;
import java.util.List;

public class GameEngine implements TerminalListener, GamePanelListener {
  private Terminal terminal;
  private GamePanel gamePanel;
  private GameObject currentGameObject;
  private GlobalObject world;

  public GameEngine(Terminal terminal, GamePanel gamePanel) {
    this.terminal = terminal;
    this.gamePanel = gamePanel;
    LinkedList<String> part1Code = new LinkedList<String>();
    part1Code.add("//Part1");
    part1Code.add("boolean light = false");
    terminal.setCode(part1Code);
    terminal.setListener(this);
    gamePanel.setListener(this);
    world = new GlobalObject();
    currentGameObject = world;
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

  @Override
  public void updatedLine(int lineNumber) {
    List<String> codeList = terminal.getCodeLines();
    String changedLine = codeList.get(lineNumber);
    try {
      world.execute(changedLine);
    } catch (StatementException e) {
//      e.printStackTrace();
      System.out.println("Invalid change");
    }
//    System.out.println("new line: " + changedLine);
  }
}