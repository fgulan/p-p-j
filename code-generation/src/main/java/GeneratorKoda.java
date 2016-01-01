import java.util.Scanner;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.TreeParser;
import hr.fer.zemris.ppj.manipulators.misc.DefinedFunctionsManipulator;
import hr.fer.zemris.ppj.manipulators.misc.MainFunctionManipulator;

/**
 * <code>GeneratorKoda</code> class is required by the evaluator, to contain a entry point for the code generator.
 * 
 * @author Jan Kelemen
 *
 * @version alpha.
 */
public class GeneratorKoda {

    /**
     * Entry point for the code generator.
     *
     * @param args
     *            command line arguments aren't used.
     * @since 1.0
     */
    public static void main(String[] args) {
        final Node node = TreeParser.parse(new Scanner(System.in));
        runSemanticAnalysis(node);
        runCodeGeneration(node);
    }

    private static void runSemanticAnalysis(Node node) {
        node.check();
        
        MainFunctionManipulator.sprutJeProvokator();
        DefinedFunctionsManipulator.sprutJeProvokator();
    }

    private static void runCodeGeneration(Node node) {
        node.generate();
    }

}
