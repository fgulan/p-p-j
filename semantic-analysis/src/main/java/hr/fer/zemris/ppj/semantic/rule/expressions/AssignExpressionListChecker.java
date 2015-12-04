package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>AssignExpressionListChecker</code> is a checker for assign expression list.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class AssignExpressionListChecker implements Checker {

    // <lista_izraza_pridruzivanja> ::= <izraz_pridruzivanja>
    // <lista_izraza_pridruzivanja> ::= <lista_izraza_pridruzivanja> ZAREZ <izraz_pridruzivanja>

    /**
     * Name of the node.
     */
    public static final String NAME = "<AssignExpressionList>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<lista_izraza_pridruzivanja>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 72.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
