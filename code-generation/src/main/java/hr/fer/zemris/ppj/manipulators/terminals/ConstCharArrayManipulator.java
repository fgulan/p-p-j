package hr.fer.zemris.ppj.manipulators.terminals;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>ConstCharArrayManipulator</code> is a manipulator for strings.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
 */
public class ConstCharArrayManipulator implements Manipulator {

    // isto ko i za znakove, samo svaki znak posebno

    private static final CommandFactory ch = new CommandFactory();

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
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        String value = (String) node.getAttribute(Attribute.VALUE);
        if ((value.charAt(0) == '"') && ((value.charAt(value.length() - 1)) == '"')) {
            value = value.substring(1, value.length() - 1); // Trim quotes.
        }
        else {
            return false;
        }

        String rawValue = "";

        for (int i = 0; i < value.length(); i++) {
            final char currentChar = value.charAt(i);
            if (currentChar == '\\') {
                if ((i + 1) < value.length()) {
                    final char nextChar = value.charAt(i + 1);
                    switch (nextChar) {
                        case 't':
                            rawValue += '\t';
                            i++;
                            break;
                        case 'n':
                            rawValue += '\n';
                            i++;
                            break;
                        case '0':
                            rawValue += '\0';
                            i++;
                            break;
                        case '\'':
                            rawValue += '\'';
                            i++;
                            break;
                        case '\"':
                            rawValue += '"';
                            i++;
                            break;
                        case '\\':
                            rawValue += '\\';
                            i++;
                            break;
                        default:
                            return false;
                    }
                }
                else {
                    return false;
                }
            }
            else if (currentChar == '"') {
                return false;
            }
            else {
                rawValue += currentChar;
            }
        }

        node.addAttribute(Attribute.VALUE, rawValue);
        return true;
    }

    @Override
    public void generate(Node node) {
        return;
    }
}
