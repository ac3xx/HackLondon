package backend;

import backend.control.If;
import backend.control.Method;
import backend.exceptions.IllegalStatementException;
import backend.exceptions.StatementException;
import backend.var.BoolVar;
import backend.var.FloatVar;
import backend.var.IntVar;
import backend.var.Var;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

/**
 * @author Csongor Kiss
 */
public class StatementExecutor {
    private Scope scope;
    private static Map<String, Class> types = new HashMap<>();
    private static Set<String> control = new HashSet<>();
    static {
        types.put("int", IntVar.class);
        types.put("boolean", BoolVar.class);
        types.put("float", FloatVar.class);

        control.add("if");
        control.add("for");
        control.add("while");

    }

    public StatementExecutor(Scope scope) {
        this.scope = scope;
    }

    public void execute(String stmt) throws StatementException {
        if (!stmt.contains(";") && !stmt.contains("}")) throw new IllegalStatementException();
        stmt = stmt.substring(0, stmt.indexOf(';'));
        System.out.println(stmt);

        Scanner scanner = new Scanner(stmt);
        String first = scanner.next();
        if (types.containsKey(first)) {
            //new variable assignment
            Class c = types.get(first);
            try {
                Var var = (Var) c.newInstance();
                String name = scanner.next();
                var.setName(name);
                String equals = scanner.next();
                if (var instanceof IntVar) {
                    ArithmeticEvaluator evaluator = new ArithmeticEvaluator(scope);
                    ((IntVar)var).setValue(evaluator.evaluate(scanner.nextLine()));
                } else {
                    String value = scanner.nextLine();
                    var.setValue(value);
                }
                if (scope.containsVariable(name)) {
                    Var variable = scope.getVariableFromScope(name);
                    variable.setValue(var.getStringValue());
                } else {
                    scope.addVariable(name, var);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchElementException e) {
                //TODO: maybe do something about this?
            }
        } else if (scope.containsVariable(first)) {
            Var var = scope.getVariableFromScope(first);
            String equals = scanner.next();
            String value = scanner.nextLine();
            if (var instanceof IntVar) {
                ArithmeticEvaluator evaluator = new ArithmeticEvaluator(scope);
                ((IntVar)var).setValue(evaluator.evaluate(value));
            } else {
                var.setValue(value);
            }
        } else if (control.contains(first)) {
            try {
                String condition = scanner.next();
                switch(first) {
                    case "if":

                        break;
                    case "for":
                        break;
                    case "while":
                        break;
                    default:
                        break;

                }
            } catch (NoSuchElementException e) {
                //TODO: maybe do something about this?
            }
        } else if (scope.containsVariable(first.substring(0, (first.length() - 2)))) {
            if ((first.substring(first.length() - 2, first.length())).equals("++")) {
                IntVar value = (IntVar) scope.getVariableFromScope(first.substring(0, (first.length() - 2)));
                value.setValue(value.getValue() + 1);
            }
        } else if (first.equals("return")) {
            String next = scanner.next();

            if (scope.containsVariable(next)) {
                Var returnVar = scope.getVariableFromScope(next);
                System.out.println(returnVar.getStringValue());
                if (scope.containsVariable("$return")) {
                    Var var = scope.getVariableFromScope("$return");
                    var.setValue(returnVar.getStringValue());
                } else {
                    scope.addVariable("$return", returnVar);
                }
            }
        } else if (first.contains("(") && first.contains(")")) {
            System.out.println("found");
            //method
            String methodName = first.substring(0, first.indexOf("("));
            if (scope.containsMethod(methodName)) {
                System.out.println(methodName + "found");
                String signature = first.substring(first.indexOf("(") + 1, first.indexOf(")"));
                String[] args = signature.split(", ");
                Method method = scope.getMethod(methodName);
                System.out.println("returns: " + method.apply(args));
                System.out.println(methodName + " called with args: " + args.length);
            }
        }
    }
}
