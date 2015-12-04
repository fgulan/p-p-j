package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>PostfixExpressionChecker</code> is a checker for postfix expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class PostfixExpressionChecker implements Checker {

    // <postfiks_izraz> ::= <primarni_izraz>
    // <postfiks_izraz> ::= <postfiks_izraz> L_UGL_ZAGRADA <izraz> D_UGL_ZAGRADA
    // <postfiks_izraz> ::= <postfiks_izraz> L_ZAGRADA D_ZAGRADA
    // <postfiks_izraz> ::= <postfiks_izraz> L_ZAGRADA <lista_argumenata> D_ZAGRADA
    // <postfiks_izraz> ::= <postfiks_izraz> OP_INC
    // <postfiks_izraz> ::= <postfiks_izraz> OP_DEC

    /**
     * Name of the node.
     */
    public static final String NAME = "<PostfixExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<postfiks_expression>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 52, 53, 54.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
