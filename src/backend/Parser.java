package backend;

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
        string = string.replace("\n", "");
        string = string.replace("\t", "");
        scanner = new Scanner(string).useDelimiter("[};](?=\\\\p{Alpha})");
        // everything starts with var, control or "//"
        try {
            String stmt = scanner.next();
            char lastChar = stmt.substring(stmt.length() - 1).toCharArray()[0];
            if (lastChar == '}') {
                //it's control flow
                Scanner stmtScan = new Scanner(stmt);
                String name = stmtScan.next();
                switch (name) {
                    case "if":
                        String condition = stmtScan.next();
                        condition = condition.substring(1, condition.length()); // strip the brackets
                        String statements = stmtScan.next();
                        statements = statements.substring((1, statements.length()-1)); //strip curly braces
                        Scanner blockScanner = new Scanner(statements).useDelimiter(";(?=\\\\p{Alpha})");
                        Block trueBlock = new Block();
                        while (blockScanner.hasNext()) {
                            trueBlock.addStatement(blockScanner.next());
                        }
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
                statementExecutor.execute(stmt);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } catch (StatementException e) {
            e.printStackTrace();
        }
    }



}
