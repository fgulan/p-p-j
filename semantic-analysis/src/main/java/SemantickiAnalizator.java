import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.TreeParser;
import hr.fer.zemris.ppj.semantic.rule.misc.DefinedFunctionsChecker;
import hr.fer.zemris.ppj.semantic.rule.misc.MainFunctionChecker;

/**
 * <code>SemantickiAnalizator</code> class is required by the evaluator, to contain a entry point for the semantic
 * analyzer.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class SemantickiAnalizator {

    /**
     * Entry point for the semantic analyzer.
     *
     * @param args
     *            command line arguments aren't used.
     * @since alpha
     */
    public static void main(String[] args) {
        try {
            Node node = TreeParser.parse(new Scanner(new FileInputStream("test.in")));
            // System.out.print(node.print(0));
            node.check();
            new MainFunctionChecker().check(node);
            new DefinedFunctionsChecker().check(node);
            SemanticErrorReporter.finalReport();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
