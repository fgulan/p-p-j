package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;


/**
 * <code>UnaryOperatorChecker</code> is a checker for unary operator.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class UnaryOperator implements Checker {

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
        // TODO Auto-generated method stub
        
    }
}
