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
    private Map<Var.Type, String> varMap;

  public Method(Scope parent, Map<Var.Type, String> args, Block body) {
    super(parent);
      this.block = body;
      varMap = args;
  }

    public String apply(String... args) throws StatementException {
        if (varMap.size() != args.length) throw new ArgumentNumberException();
        int i = 0;
        for (Map.Entry<Var.Type, String> entry : varMap.entrySet()) {
            Var var = Var.Type.varInstance(entry.getKey());
            var.setName(entry.getValue());
            var.setValue(args[i]);
            addVariable(var.getName(), var);
            i++;
        }

        for (String stmt : block.getStatements()) {
            execute(stmt);
        }

        if (containsVariable("$return")) {
            return getVariableFromScope("$return").getStringValue();
        } else return "";
    }

}
