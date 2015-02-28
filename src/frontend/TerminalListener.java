package frontend;

import java.util.LinkedList;

/**
 * Created by Prince on 28/02/15.
 */
public interface TerminalListener {

  public boolean isValidLine(String line);

  public void setCodelines(LinkedList<String> codeLines);
}
