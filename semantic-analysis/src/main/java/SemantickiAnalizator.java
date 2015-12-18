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
 * @version 1.0
 */
public class SemantickiAnalizator {

    /**
     * Entry point for the semantic analyzer.
     *
     * @param args
     *            command line arguments aren't used.
     * @since 1.0
     */
    public static void main(String[] args) {
        final Node node = TreeParser.parse(new Scanner(System.in));
        node.check();
        MainFunctionChecker.sprutJeProvokator();
        DefinedFunctionsChecker.sprutJeProvokator();
        SemanticErrorReporter.finalReport();
    }

}
