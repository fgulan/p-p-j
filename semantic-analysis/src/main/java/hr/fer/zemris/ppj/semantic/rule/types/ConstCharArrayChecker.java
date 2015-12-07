package hr.fer.zemris.ppj.semantic.rule.types;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>ConstCharArrayChecker</code>
 *
 * @author Jan Kelemen
 *
 * @version
 */
public class ConstCharArrayChecker implements Checker {

    // isto ko i za znakove, samo svaki znak posebno

    /**
     * Name of the node.
     */
    public static final String NAME = "<ConstCharArray>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "NIZ_ZNAKOVA";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 42.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        String value = (String) node.getAttribute(Attribute.VALUE);

        // KILL ME NOW
        return value.matches("\"(\\w|\\t|\\n|\\0|\\'|\\\"|\\\\)*\"");
    }
}