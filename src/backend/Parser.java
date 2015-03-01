package backend;

import backend.control.Executable;
import backend.control.For;
import backend.control.If;
import backend.control.Method;
import backend.exceptions.MethodAlreadyInScopeException;
import backend.exceptions.StatementException;
import backend.var.Var;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Prince on 01/03/15.
 */
public class Parser {

    private Scanner scanner;
    private Scope scope;
    private StatementExecutor statementExecutor;

    public Parser(Scope scope) {
        this.scope = scope;
        statementExecutor = new StatementExecutor(scope);
    }

    public void parse(String string) {
        Executable accumulatingExecutable = null;
        Method accumulatingMethod = null;
        Block accumulatingBlock = null;
        boolean declarationMode = false;
        scanner = new Scanner(string);
        while (scanner.hasNextLine()) {
            try {
                String nextLine = scanner.nextLine().trim();
                if (nextLine.startsWith("//") || nextLine.startsWith("/*") || nextLine.startsWith("*")) continue;
                if (nextLine.endsWith(";")) {
                    if (!declarationMode) {
                        statementExecutor.execute(nextLine);
                    } else {
//                        System.out.println(nextLine);
                        accumulatingBlock.addStatement(nextLine);
                    }
                } else if (nextLine.endsWith("{")) {
                    String firstWord = nextLine.substring(0, nextLine.indexOf(" "));
                    nextLine = nextLine.substring(nextLine.indexOf(" ") + 1, nextLine.length());
                    declarationMode = true;
                    switch (firstWord.trim()) {
                        case "for": {
                            Pattern pattern = Pattern.compile("\\((int .+ = [0-9]*;) (i < [0-9]+); (.+)\\) \\{");
                            Matcher matcher = pattern.matcher(nextLine);
                            //for (int i = 0; i < 10; i++) {...}
                            if (matcher.matches()) {
                                Block block = new Block();
                                accumulatingBlock = block;
                                For mFor = new For(scope, matcher.group(1), matcher.group(2), matcher.group(3) + ";", block);
                                accumulatingExecutable = mFor;
                                declarationMode = true;
                            }
                            break;
                        }
                        case "if": {
                            Pattern pattern = Pattern.compile("\\((.+)\\) \\{");
                            Matcher matcher = pattern.matcher(nextLine);
                            if (matcher.matches()) {
                                Block block = new Block();
                                accumulatingBlock = block;
                                If mIf = new If(scope, matcher.group(1), block, null);
                                accumulatingExecutable = mIf;
                                declarationMode = true;
                            }
                            break;
                        }
                        case "while":
                            break;
                        case "int":
                        case "boolean":
                        case "float":
//                            String type = firstWord.trim();
                            String methodName = nextLine.substring(0, nextLine.indexOf("("));
                            String signature = nextLine.substring(nextLine.indexOf("(") + 1, nextLine.indexOf(")"));
                            if (signature.length() > 0) {
                                Scanner signatureScanner = new Scanner(signature);
                                HashMap<Var.Type, String> args = new HashMap<>();
                                while(signatureScanner.hasNext()) {
                                    String typeName = signatureScanner.next();
                                    Var.Type type;
                                    switch (typeName) {
                                        case "int":
                                            type = Var.Type.INTEGER;
                                            break;
                                        case "boolean":
                                            type = Var.Type.BOOL;
                                            break;
                                        case "float":
                                            type = Var.Type.FLOAT;
                                            break;
                                        default:
                                            throw new IllegalArgumentException();
                                    }
                                    String varName = signatureScanner.next();
                                    if (varName.contains(",")) {
                                        varName = varName.substring(0, varName.indexOf(","));
                                    }
                                    args.put(type, varName);
                            }
                                Block body = new Block();
                                accumulatingBlock = body;
                                Method method = new Method(scope, args, body);
                                try {
                                    scope.addMethod(methodName, method);
                                } catch (MethodAlreadyInScopeException e) {
                                    method = scope.getMethod(methodName);
                                    method.setBody(body);
                                }
                                declarationMode = true;
                            }
                            break;
                        default:
                            break;
                    }
                } else if (nextLine.endsWith("}")) {
                    if (accumulatingExecutable != null) {
                        accumulatingExecutable.execute();
                        accumulatingExecutable = null;
                    }
                    if (accumulatingMethod != null) {
                        accumulatingMethod = null;
                    }
                    accumulatingBlock = null;
                    declarationMode = false;
                } else {
//                    System.out.println(nextLine);
                }
            } catch (StatementException e) {
                e.printStackTrace();
            }
        }
    }

}
