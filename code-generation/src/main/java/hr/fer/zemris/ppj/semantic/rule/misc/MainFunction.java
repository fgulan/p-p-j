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
 * @version 1.0
 */
public class MainFunction implements Checker {

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
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        return sprutJeProvokator();
    }

    /**
     * functions used to shut up compile warnings from SPRUT.
     *
     * @return <code>true</code> if the main function is correctly defined, <code>false</code> otherwise.
     * @since 1.0
     */
    public static boolean sprutJeProvokator() {
        final FunctionType functionType = IdentifierTable.GLOBAL_SCOPE.function("main");

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
    
    @Override
    public void generate(Node node) {
        // TODO Auto-generated method stub
        
    }
}
