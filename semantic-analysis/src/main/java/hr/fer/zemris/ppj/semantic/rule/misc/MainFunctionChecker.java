package hr.fer.zemris.ppj.semantic.rule.misc;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.identifier.table.IdentifierTable;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.types.IntType;
import hr.fer.zemris.ppj.types.functions.FunctionType;

/**
 * <code>MainFunctionChecker</code> is a checker for main function.
 *
 * @author Matea Sabolic
 *
 * @version alpha
 */
public class MainFunctionChecker implements Checker {

    // main (void -> int)

    /**
     * Name of the node.
     */
    public static final String NAME = "<MainFunction>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<main>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 72.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        return true;
    }

    public static boolean sprutJeProvokator() {
        FunctionType functionType = IdentifierTable.GLOBAL_SCOPE.function("main");

        if (functionType == null) {
            SemanticErrorReporter.report("main");
            return false;
        }

        if (!functionType.returnType().equals(new IntType()) || !functionType.argumentList().isEmpty()) {
            SemanticErrorReporter.report("main");
            return false;
        }

        return true;
    }

}
