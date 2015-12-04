package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>ArgumentListChecker</code> is a checker for argument list.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class ArgumentListChecker implements Checker {

    // <lista_argumenata> ::= <izraz_pridruzivanja>
    // <lista_argumenata> ::= <lista_argumenata> ZAREZ <izraz_pridruzivanja>

    /**
     * Name of the node.
     */
    public static final String NAME = "<ArgumentList>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<lista_argumenata>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 54.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
