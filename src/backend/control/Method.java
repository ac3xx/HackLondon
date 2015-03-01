package backend.control;

import backend.Block;
import backend.Scope;
import backend.StatementExecutor;
import backend.exceptions.ArgumentNumberException;
import backend.exceptions.StatementException;
import backend.var.Var;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Csongor Kiss
 */
public class Method extends Scope {
  private Block block;
    private Map<String, Var.Type> varMap;

  public Method(Scope parent, Map<String, Var.Type> args, Block body) {
    super(parent);
      this.block = body;
      varMap = args;
  }

public Method(Scope parent) {
        super(parent);
    }

    public String apply(String... args) throws StatementException {
        if (varMap.size() != args.length) throw new ArgumentNumberException();
        int i = 0;
    for (Map.Entry<String, Var.Type> entry : varMap.entrySet()) {
        Var var = Var.Type.varInstance(entry.getValue());
        var.setName(entry.getKey());
            var.setValue(args[i]);
            if (containsVariable(var.getName())) {
                reassignVariable(var.getName(), var.getStringValue());
            } else addVariable(var.getName(), var);
            i++;
        }

        for (String stmt : block.getStatements()) {
            execute(stmt);
        }
        System.out.println("method's scope: " + this);

        if (containsVariable("$return")) {
            return getVariableFromScope("$return").getStringValue();
        } else return "";
    }

    public void setBody(Block body) {
        this.block = body;
    }
}
