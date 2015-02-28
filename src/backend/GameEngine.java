package backend;

import frontend.GamePanel;
import frontend.GamePanelListener;
import frontend.Terminal;
import frontend.TerminalListener;

public class GameEngine implements TerminalListener, GamePanelListener {
  private Terminal terminal;
  private GamePanel gamePanel;

  public GameEngine(Terminal terminal, GamePanel gamePanel) {
    this.terminal = terminal;
    this.gamePanel = gamePanel;
    terminal.setListener(this);
    gamePanel.setListener(this);
  }

  public void clickAt( int x, int y) {
    
  }





}