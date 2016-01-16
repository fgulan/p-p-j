package hr.fer.zemris.ppj.manipulators.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>UnaryOperatorManipulator</code> is a manipulator for unary operator.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
 */
public class UnaryOperatorManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

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
            // UNARY_OPERATOR_1("<unarni_operator> ::= PLUS"),
            break;
        }

        case UNARY_OPERATOR_2: {
            // UNARY_OPERATOR_2("<unarni_operator> ::= MINUS"),
            break;
        }

        case UNARY_OPERATOR_3: {
            // UNARY_OPERATOR_3("<unarni_operator> ::= OP_TILDA"),
            break;
        }

        case UNARY_OPERATOR_4: {
            // UNARY_OPERATOR_4("<unarni_operator> ::= OP_NEG"),
            break;
        }

        default:
            System.err.println("Generation reached undefined production!");
            break;
        }
    }
}
