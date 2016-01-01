package hr.fer.zemris.ppj.manipulators.misc;

import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.identifier.table.IdentifierTable;
import hr.fer.zemris.ppj.identifier.table.IdentifierTypeWrapper;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>DefinedFunctionManipulator</code> is a manipulator for defined functions.
 *
 * @author Matea Sabolic
 *
 * @version 1.1
 */
public class DefinedFunctionsManipulator implements Manipulator {

    /**
     * Name of the node.
     */
    public static final String NAME = "<DefinedFunctions";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<funkcija>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 72.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        return true;
    }

    /**
     * @return <code>true</code> if all functions are defined, <code>false</code> otherwise.
     * @since 1.0
     */
    public static boolean sprutJeProvokator() {
        final Set<IdentifierTypeWrapper> declared = new HashSet<>(IdentifierTable.declaredFunctions);
        final Set<IdentifierTypeWrapper> defined = new HashSet<>(IdentifierTable.definedFunctions);

        if (!defined.containsAll(declared)) {
            SemanticErrorReporter.report("funkcija");
            return false;
        }

        return true;
    }

    @Override
    public void generate(Node node) {
        return;
    }
}
