package backend;

import backend.exceptions.StatementException;
import backend.var.Var;
import backend.var.VarListener;
import com.sun.deploy.util.StringUtils;
import frontend.GamePanel;
import frontend.GameWindow;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Csongor Kiss
 */
public class GameObject extends Scope {
    private String textureName = GamePanel.DEFAULT_TEXTURE;

    private LinkedList<String> codeLines = new LinkedList<String>();

    public GameObject(Scope scope) {
        super(scope);
        codeLines.add("//New object");
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

    @Override
    public void addVariable(String name, Var var) {
        if (containsVariable(name)) {
            try {
                reassignVariable(name, var.getStringValue());
            } catch (StatementException e) {
                e.printStackTrace();
            }
        }
        final GameObject instance = this;
        variables.put(name, var);
        if (name.equals("isLand")) {
            var.addListener(new VarListener() {
                @Override
                public void valueSet(Var variable) {
                    if (variable.getStringValue().equals("true")) {
                        instance.setTextureName("grass");
                    } else {
                        instance.setTextureName("water");
                    }
                    GameWindow.getInstance().getGamePanel().setInvalidated(true);
                }
            });
        }
    }

    public void reload() {
        Parser parser = new Parser(this);
        String parseString = StringUtils.join(codeLines, "");
        parser.parse(parseString);
    }
}
