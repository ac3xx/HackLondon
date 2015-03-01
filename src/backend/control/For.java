package backend.control;

import backend.Block;
import backend.ConditionEvaluator;
import backend.Scope;
import backend.exceptions.StatementException;

/**
 * @author Csongor Kiss
 */
public class For extends Scope {
    private final String condition;
    private final ConditionEvaluator evaluator;
    private final Block block;
    private final String reassignment;

    //for (int i = 0; i < 100; i++) {
    // block of code
    //}
    public For(Scope scope, String initial, String condition, String reassignment, Block block) throws StatementException {
        super(scope);
        this.block = block;
        this.evaluator = new ConditionEvaluator(this);
        this.condition = condition;
        this.reassignment = reassignment;
        execute(initial);
    }

    public void execute() throws StatementException {
        System.out.println(this);
        for (String stmt : block.getStatements()) {
            execute(stmt);
        }
        if(evaluator.evaluate(condition)) {
            execute(reassignment);
            execute();
        }
    }
}
