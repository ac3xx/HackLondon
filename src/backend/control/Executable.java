package backend.control;

import backend.Scope;
import backend.exceptions.StatementException;

/**
 * @author Csongor Kiss
 */
public abstract class Executable extends Scope {
    public Executable(Scope scope) {
       super(scope);
    }

    public abstract void execute() throws StatementException;
}
