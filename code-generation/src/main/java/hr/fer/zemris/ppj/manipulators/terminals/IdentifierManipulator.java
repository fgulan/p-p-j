package hr.fer.zemris.ppj.manipulators.terminals;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>NumberChecker</code>
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class IdentifierManipulator implements Manipulator {

    /**
     * Name of the node.
     */
    public static final String NAME = "<Char>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "IDN";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: ?.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final String value = (String) node.getAttribute(Attribute.VALUE);

        // KILL ME NOW
        return value.matches("\\w(\\w|\\d)*");
    }

    @Override
    public void generate(Node node) {
        return;
    }
}
