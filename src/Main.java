import backend.ArithmeticEvaluator;
import backend.Block;
import backend.ConditionEvaluator;
import backend.Scope;
import backend.control.If;
import backend.exceptions.StatementException;
import backend.var.BoolVar;
import backend.var.IntVar;
import frontend.GameWindow;
import javax.swing.SwingUtilities;

/**
 * Created by James on 28/02/15.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                GameWindow sk = GameWindow.init();
                sk.setVisible(true);
            }
        });

//        Scope scope = new Scope(null);
//        Block trueBlock = new Block();
//        trueBlock.addStatement("boolean asd = true;");
//        trueBlock.addStatement("int kasd = 5;");
//        Block falseBlock = new Block();
//        falseBlock.addStatement("boolean asd = false;");
//        If mIf = new If(scope, "false", trueBlock, falseBlock);
//        try {
//            mIf.execute();
//            System.out.println(mIf);
//
//        } catch (StatementException e) {
//            e.printStackTrace();
//        }
    }
}
