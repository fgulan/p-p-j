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
public class ConstCharArray implements Checker {

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
        // TODO Auto-generated method stub
        
    }
}
