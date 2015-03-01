package backend.control;

import backend.Block;
import backend.ConditionEvaluator;
import backend.Scope;
import backend.exceptions.StatementException;

import java.beans.Statement;

/**
 * @author Csongor Kiss
 */
public class If extends Scope {
    private Block trueBlock, falseBlock;
    private String condition;

    //TODO: else if
    public If(Scope parent, String condition, Block trueBlock, Block falseBlock) {
        super(parent);
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
        this.condition = condition;
    }

    public void execute() throws StatementException {
        ConditionEvaluator evaluator = new ConditionEvaluator(parent);
        boolean condition = evaluator.evaluate(this.condition);
        if (condition) {
            for (String stmt : trueBlock.getStatements()) {
                execute(stmt);
            }
        } else {
            for (String stmt : falseBlock.getStatements()) {
                execute(stmt);
            }
        }
    }
}
