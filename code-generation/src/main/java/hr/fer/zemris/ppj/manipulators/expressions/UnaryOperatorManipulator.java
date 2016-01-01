package hr.fer.zemris.ppj.manipulators.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>UnaryOperatorManipulator</code> is a manipulator for unary operator.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
 */
public class UnaryOperatorManipulator implements Manipulator {

    // <unarni_operator> ::= PLUS
    // <unarni_operator> ::= MINUS
    // <unarni_operator> ::= OP_TILDA
    // <unarni_operator> ::= OP_NEG

    /**
     * Name of the node.
     */
    public static final String NAME = "<UnaryOperator>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<unarni_operator>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 55.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        return true;
    }

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
            case UNARY_OPERATOR_1: {
                break;
            }

            case UNARY_OPERATOR_2: {
                break;
            }

            case UNARY_OPERATOR_3: {
                break;
            }

            case UNARY_OPERATOR_4: {
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
