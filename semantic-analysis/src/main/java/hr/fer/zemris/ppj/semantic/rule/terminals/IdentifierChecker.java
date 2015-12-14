package hr.fer.zemris.ppj.semantic.rule.terminals;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>NumberChecker</code>
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class IdentifierChecker implements Checker {

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
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        String value = (String) node.getAttribute(Attribute.VALUE);

        // KILL ME NOW
        return value.matches("\\w(\\w|\\d)*");
    }
}
