package backend;

import backend.exceptions.StatementException;
import com.sun.deploy.util.StringUtils;
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
  private TileLocation currentTile;
  private GlobalObject world;

  public GameEngine() {
    world = new GlobalObject();
    currentGameObject = world;
    LinkedList<String> part1Code = new LinkedList<String>();
    part1Code.add("//Let there be light");
    part1Code.add("boolean light = false;");
    world.setCodelines(part1Code);
  }

  @Override
  public void tileSelected(TileLocation which) {
    currentTile = which;
    currentGameObject = tiles.get(which);
    if (currentGameObject == null) {
      currentGameObject = new GameObject(world);
      tiles.put(which, currentGameObject);
    }
    terminal.setCode(currentGameObject.getCodeLines());
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
      reloadClass();

//    List<String> codeList = terminal.getCodeLines();
//    String changedLine = codeList.get(lineNumber);
//    try {
//      currentGameObject.execute(changedLine);
//    } catch (StatementException e) {
////      e.printStackTrace();
//    }
////    System.out.println("new line: " + changedLine);
  }

    public void reloadClass() {
        currentGameObject.reload();
    }

  public void setTerminal(Terminal terminal) {
    this.terminal = terminal;
    terminal.setCode(currentGameObject.getCodeLines());
  }

  public void setGamePanel(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  public HashMap<TileLocation, GameObject> getTiles() {
    return tiles;
  }
}