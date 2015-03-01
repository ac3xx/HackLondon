package backend;

import backend.exceptions.StatementException;
import backend.exceptions.VariableNotInScopeException;
import backend.var.BoolVar;
import backend.var.Var;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Csongor Kiss
 */
public class ConditionEvaluator {
    private Scope scope;

    public ConditionEvaluator(Scope scope) {
        this.scope = scope;
    }

    public boolean evaluate(String condition) throws StatementException {
        if (scope.containsVariable(condition)) {
            return ((BoolVar) scope.getVariableFromScope(condition)).getValue();
        }
        if (condition.equals("true")) return true;
        if (condition.equals("false")) return false;
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("(.+) < (.+)");
        matcher = pattern.matcher(condition);
        if (matcher.find()) {
            ArithmeticEvaluator arithmeticEvaluator = new ArithmeticEvaluator(scope);
            return arithmeticEvaluator.evaluate(matcher.group(1))
                    < arithmeticEvaluator.evaluate(matcher.group(2));
        }

        pattern = Pattern.compile("(.+) && (.+)");
        matcher = pattern.matcher(condition);
        if (matcher.find()) {
            return evaluate(matcher.group(1)) && evaluate(matcher.group(2));
        }
        pattern = Pattern.compile("(.+) \\|\\| (.+)");
        matcher = pattern.matcher(condition);
        if (matcher.find()) {
            return evaluate(matcher.group(1)) || evaluate(matcher.group(2));
        }
        return false;
    }
}
