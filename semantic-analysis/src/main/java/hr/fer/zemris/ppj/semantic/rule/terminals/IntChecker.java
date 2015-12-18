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
    public static final String HR_NAME = "BROJ";

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

        int intVal = 0;
        try {
            if (value.length() == 1) {
                intVal = Integer.parseInt(value);
            }
            else {
                boolean negative = value.startsWith("-");
                if (negative) {
                    value = value.substring(1);
                }

                if (value.startsWith("0x") || value.startsWith("0X")) {
                    value = value.substring(2);
                    intVal = Integer.parseInt(value, 16);
                }
                else if (value.startsWith("0")) {
                    value = value.substring(1);
                    intVal = Integer.parseInt(value, 8);
                }
                else {
                    intVal = Integer.parseInt(value);
                }

                if (negative) {
                    intVal *= -1;
                }
            }
        }
        catch (NumberFormatException e) {
            return false;
        }

        node.addAttribute(Attribute.VALUE, intVal);
        return true;
    }

}
