package backend;

import backend.var.IntVar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Csongor Kiss
 */
public class ArithmeticEvaluator {
    private Scope scope;

    public ArithmeticEvaluator(Scope scope) {
        this.scope = scope;
    }

    public int evaluate(String expression) {
        try {
           return Integer.parseInt(expression);
        } catch (NumberFormatException e) {
            if (scope.containsVariable(expression)) {
                return ((IntVar) scope.getVariableFromScope(expression)).getValue();
            }
            Pattern arithmethicPattern;
            Matcher matcher;
            arithmethicPattern = Pattern.compile("(.+) \\* (.+)");
            matcher = arithmethicPattern.matcher(expression);

            if (matcher.find()) {
                return evaluate(matcher.group(1)) * evaluate(matcher.group(2));
            }

            arithmethicPattern = Pattern.compile("(.+) \\+ (.+)");
            matcher = arithmethicPattern.matcher(expression);

            if (matcher.find()) {
                return evaluate(matcher.group(1)) + evaluate(matcher.group(2));
            }

            arithmethicPattern = Pattern.compile("(.+) \\- (.+)");
            matcher = arithmethicPattern.matcher(expression);

            if (matcher.find()) {
                return evaluate(matcher.group(1)) - evaluate(matcher.group(2));
            }
            return 0;
        }
    }
}
