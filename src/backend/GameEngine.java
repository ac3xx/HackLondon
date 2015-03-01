package backend;

import backend.exceptions.StatementException;
import frontend.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameEngine implements TerminalListener, GamePanelListener {
  private HashMap<TileLocation, GameObject> tiles = new HashMap<TileLocation, GameObject>();
  private Terminal terminal;
  private GamePanel gamePanel;
  private GameObject currentGameObject;
  private GlobalObject world;

  public GameEngine() {
    world = new GlobalObject();
    currentGameObject = world;
    GameObject object = new GameObject(world);
    object.setTextureName("grass");
    tiles.put(new TileLocation(2, 2), object);
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

  @Override
  public void updatedLine(int lineNumber) {
    List<String> codeList = terminal.getCodeLines();
    String changedLine = codeList.get(lineNumber);
    try {
      world.execute(changedLine);
    } catch (StatementException e) {
//      e.printStackTrace();
    }
//    System.out.println("new line: " + changedLine);
  }

  public void setTerminal(Terminal terminal) {
    this.terminal = terminal;
    LinkedList<String> part1Code = new LinkedList<String>();
    part1Code.add("//Let there be light");
    part1Code.add("boolean light = false;");
    terminal.setCode(part1Code);
  }

  public void setGamePanel(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  public HashMap<TileLocation, GameObject> getTiles() {
    return tiles;
  }
}