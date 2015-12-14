package hr.fer.zemris.ppj.semantic.rule.terminals;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>CharValueChecker</code>
 *
 * @author Jan Kelemen
 *
 * @version
 */
public class CharChecker implements Checker {

    // svi ascii + '\t', '\n', '\0', '\'', '\"', '\\'

    /**
     * Name of the node.
     */
    public static final String NAME = "<Char>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "ZNAK";

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
        return value.matches("'(\\w|\\t|\\n|\\0|\\'|\\\"|\\\\)'");
    }
}
