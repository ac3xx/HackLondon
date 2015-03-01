package backend;

import backend.control.If;
import backend.exceptions.StatementException;

import java.util.NoSuchElementException;
import java.util.Scanner;

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
        scanner = new Scanner(string).useDelimiter("(?<=[;}\n])");
        // everything starts with var, control or "//"
        while (scanner.hasNext()) {
            try {
                String stmt = scanner.next();
                char lastChar = stmt.substring(stmt.length() - 1).toCharArray()[0];
                System.out.println("lastchar: " + lastChar + " stmt: " + stmt);
                if (lastChar == '}') {
                    //it's control flow
                    Scanner stmtScan = new Scanner(stmt).useDelimiter("(?<=[)])");
                    String name = stmtScan.next();
                    switch (name) {
                        case "if":
                            String condition = stmtScan.next();
                            stripBrackets(condition);
                            ; // strip the brackets
                            String statements = stmtScan.next();
                            stripBrackets(statements); //strip curly braces
                            Scanner blockScanner = new Scanner(statements).useDelimiter("(?<=[;\n])");
                            Block trueBlock = new Block();
                            while (blockScanner.hasNext()) {
                                trueBlock.addStatement(blockScanner.next());
                            }
                            Block falseBlock = null;
                            if (scanner.hasNext("else") || scanner.hasNext(" else")) {
                                stmt = scanner.next(); //drop the else
                                stmtScan = new Scanner(stmt);
                                stmtScan.next(); //drop the else
                                statements = stmtScan.next();
                                stripBrackets(statements); //strip curly braces
                                blockScanner = new Scanner(statements).useDelimiter("(?<=[;\n])");
                                falseBlock = new Block();
                                while (blockScanner.hasNext()) {
                                    falseBlock.addStatement(blockScanner.next());
                                }
                            }
                            If ifcontrol = new If(scope, condition, trueBlock, falseBlock);
                            ifcontrol.execute();

                            break;
                        case "for":
                            //TODO: Make for class
                            break;
                        case "while":
                            //TODO: Make while class
                            break;
                        default:
                            break;
                    }
                } else if (lastChar == ';') {
                    //it's a var, execute the fucker
                    System.out.println("stmt execute: " + stmt);
//                    statementExecutor.execute(stmt);
                }
            } catch (NoSuchElementException e) {
                e.printStackTrace();
//            } catch (StatementException e) {
//                e.printStackTrace();
            } catch (StatementException e) {
                e.printStackTrace();
            }
        }
    }

    private void stripBrackets(String s) {
        s = s.substring(1, s.length()-1);
    }



}
