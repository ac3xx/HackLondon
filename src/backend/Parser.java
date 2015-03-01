package backend;

import backend.control.Executable;
import backend.control.For;
import backend.control.If;
import backend.exceptions.StatementException;

import java.util.ArrayList;
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

//    static String[] splitAndKeep(String input, String regex, int offset) {
//        ArrayList<String> res = new ArrayList<String>();
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(input);
//        int pos = 0;
//        while (m.find()) {
//            res.add(input.substring(pos, m.end() - offset));
//            pos = m.end() - offset;
//        }
//        if(pos < input.length()) res.add(input.substring(pos));
//        return res.toArray(new String[res.size()]);
//    }
//
//    static String[] splitAndKeep(String input, String regex) {
//        return splitAndKeep(input, regex, 0);
//    }

    public void parse(String string) {
        Executable accumulatingExecutable = null;
        boolean declarationMode = false;
        Block accumulatingBlock = null;
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
                    System.out.println(nextLine);
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
                                System.out.println(matcher.group(1));
                                System.out.println(matcher.group(2));
                                System.out.println(matcher.group(3) + ";");
                                declarationMode = true;
                            }
                            break;
                        }
                        case "if": {
                            Pattern pattern = Pattern.compile("\\((.+)\\) \\{");
                            Matcher matcher = pattern.matcher(nextLine);
                            if (matcher.matches()) {
                                System.out.println("matches!");
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
                            break;
                        case "boolean":
                            break;
                        case "float":
                            break;
                        default:
                            break;
                    }
                    declarationMode = true;
                } else if (nextLine.endsWith("}")) {
                    if (accumulatingExecutable != null)
                        accumulatingExecutable.execute();
                    declarationMode = false;
                } else {
                    System.out.println(nextLine);
                }
//                String instr = scanner.next();
//                switch (instr) {
//                    case "boolean":
//                    case "int":
//                    case "float":
//                        String name = scanner.next();
//                        String equals = scanner.next();
//                        String value = scanner.next();
//                        statementExecutor.execute(instr + name + value);
//                        break;
//                    case "if":
//                        String condition = scanner.next();
//                        String trueString = scanner.next();
//                        while (!scanner.hasNext(".+}")) {
//                            String toAdd = scanner.next();
//                            System.out.println("trueString: " + trueString+ " toAdd: "+ toAdd);
//                            trueString = trueString + " " +toAdd;
//                        }
//                        stripBrackets(trueString);
//                        String falseString = null;
//                        if (scanner.hasNext("else")) {
//                            falseString = scanner.next();
//                            while (!scanner.hasNext(".+}")) {
//                                falseString = falseString + scanner.next();
//                            }
//                        }
//                        stripBrackets(falseString);
//                        Block trueBlock = new Block();
//                        Block falseBlock = new Block();
//                        for (String s: trueString.split(";")) {
//                            trueBlock.addStatement(s);
//                        }
//                        for (String s: falseString.split(";")) {
//                            trueBlock.addStatement(s);
//                        }
//                        If ifcontrol = new If(scope, condition, trueBlock, falseBlock);
//                        ifcontrol.execute();
//                        break;
//                    case "for":
//                    default:
//                        break;
//
//                }
            } catch (StatementException e) {
                e.printStackTrace();
            }
        }

//        String[] stringArray = splitAndKeep(string, "[;}]");
//        for (String s: stringArray) {
//            //System.out.println("StringArray: " + s);
//        }
//        // everything starts with var, control or "//"
//        for (int i = 0; i < stringArray.length; i++) {
//            try {
//                if (stringArray[i].startsWith("//")) {
//                    continue;
//                }
//                String stmt = stringArray[i];
//                //System.out.println("Stmt: " + stmt);
//                char lastChar = stmt.substring(stmt.length() - 1).toCharArray()[0];
//                //System.out.println("lastchar: " + lastChar + " stmt: " + stmt);
//                if (lastChar == '}') {
//                    //it's control flow
//
////                    String[] stringArray = splitAndKeep(stmt, " )}");
////                    for (String s: stringArray) {
////                        System.out.println("StringArray: " + s);
////                    }
//
//                    String[] args = stmt.split(" ");
//                    for (String s: args) {
//                        System.out.println("Args: " + s);
//                    }
//                    String name = args[0];
//                    //System.out.println("name: " + name);
//                    switch (name) {
//                        case "if":
//                            String condition = args[1];
//                            stripBrackets(condition);
//                            String statements = args[2];
//                            stripBrackets(statements); //strip curly braces
//                            Scanner blockScanner = new Scanner(statements).useDelimiter("(?<=[;\n])");
//                            Block trueString = new Block();
//                            while (blockScanner.hasNext()) {
//                                trueString.addStatement(blockScanner.next());
//                            }
//                            Block falseString = null;
//                            if (scanner.hasNext("else") || scanner.hasNext(" else")) {
//                                stmt = scanner.next(); //drop the else
//                                Scanner stmtScan2 = new Scanner(stmt);
//                                stmtScan2.next(); //drop the else
//                                statements = stmtScan2.next();
//                                stripBrackets(statements); //strip curly braces
//                                Scanner blockScanner2 = new Scanner(statements).useDelimiter("(?<=[;\n])");
//                                falseString = new Block();
//                                while (blockScanner2.hasNext()) {
//                                    falseString.addStatement(blockScanner2.next());
//                                }
//                            }
////                            If ifcontrol = new If(scope, condition, trueString, falseString);
////                            ifcontrol.execute();
//
//                            break;
//                        case "for":
//                            //TODO: Make for class
//                            break;
//                        case "while":
//                            //TODO: Make while class
//                            break;
//                        default:
//                            break;
//                    }
//                } else if (lastChar == ';') {
//                    //it's a var, execute the fucker
//                    //System.out.println("stmt execute: " + stmt);
//                    //statementExecutor.execute(stmt);
//                }
//            } catch (NoSuchElementException e) {
//                e.printStackTrace();
////            } catch (StatementException e) {
////                e.printStackTrace();
//            } catch (StatementException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private void stripBrackets(String s) {
        if (s.startsWith("(") || s.startsWith("{")) {
            s = s.substring(1);
        }
        if (s.endsWith(")") || s.endsWith("}")) {
            s = s.substring(0, s.length()-1);
        }
    }



}
