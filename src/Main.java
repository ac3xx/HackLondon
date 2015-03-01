import backend.ArithmeticEvaluator;
import backend.Block;
import backend.ConditionEvaluator;
import backend.Scope;
import backend.control.For;
import backend.control.If;
import backend.control.Method;
import backend.exceptions.StatementException;
import backend.var.BoolVar;
import backend.var.IntVar;
import backend.var.Var;
import frontend.GameWindow;
import javax.swing.SwingUtilities;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by James on 28/02/15.
 */
public class Main {
    public static void main(String[] args) throws StatementException {
//        SwingUtilities.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//
//                GameWindow sk = GameWindow.init();
//                sk.setVisible(true);
//            }
//        });

        Scope scope = new Scope(null);
        Block block = new Block();
        Map<Var.Type, String> arguments = new HashMap<>();
        arguments.put(Var.Type.INTEGER, "i");
        block.addStatement("i = i + 6;");
        block.addStatement("return i;");
        Method method = new Method(scope, arguments, block);
        method.apply("9");
    }
}
