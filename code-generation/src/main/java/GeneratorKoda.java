import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.TreeParser;
import hr.fer.zemris.ppj.code.generator.FRISCGenerator;
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
    public static void main(String[] args) throws FileNotFoundException {
        final Node node = TreeParser.parse(new Scanner(new FileInputStream(new File("test.in"))));
        runSemanticAnalysis(node);
        runCodeGeneration(node, System.out);
    }

    private static void runSemanticAnalysis(Node node) {
        node.check();

        MainFunctionManipulator.sprutJeProvokator();
        DefinedFunctionsManipulator.sprutJeProvokator();
    }

    private static void runCodeGeneration(Node node, OutputStream outputStream) {
        FRISCGenerator.generatePreamble();
        node.generate();
        FRISCGenerator.generateEpilogue();
        try {
            FRISCGenerator.generateTo(outputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
