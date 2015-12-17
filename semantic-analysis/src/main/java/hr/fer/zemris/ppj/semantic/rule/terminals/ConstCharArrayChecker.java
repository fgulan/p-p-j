package hr.fer.zemris.ppj.semantic.rule.terminals;

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

        // known bug in lexical analysis
        if ("\"\\\"".equals(value)) {
            return true;
        }

        if (value.equals("\"\\\"")) {
            return false;
        }

        for (int i = 1; i < (value.length() - 1); i++) {
            char currentChar = value.charAt(i);
            if ((currentChar == '\'') || (currentChar == '\"')) {
                return false;
            }

            if ((value.charAt(i) == '\\') && ((i + 1) < (value.length() - 1))) {
                char nextChar = value.charAt(i + 1);
                switch (nextChar) {
                    case 't':
                    case 'n':
                    case '0':
                    case '\'':
                    case '"':
                    case '\\':
                        i++;
                        break;
                    default:
                        return false;
                }
            }
        }

        return true;
    }
}
