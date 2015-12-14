import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.TreeParser;

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
            System.out.print(node.print(0));
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
