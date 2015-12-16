package hr.fer.zemris.ppj.semantic.rule.terminals;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>IntRangeChecker</code> is a checker for int range.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class IntChecker implements Checker {

    // -2,147,483,648 to 2,147,483,647

    /**
     * Name of the node.
     */
    public static final String NAME = "<Int>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "INT";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 42.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        String value = (String) node.getAttribute((Attribute.VALUE));

        if (!value.matches("(-|\\+)?\\d+)")) {
            return false;
        }

        try {
            node.addAttribute(Attribute.VALUE, Integer.parseInt((String) node.getAttribute(Attribute.VALUE)));
        }
        catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

}
