package hr.fer.zemris.ppj.semantic.rule.declarations;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>InitializationDeclaratorChecker</code> is a checker for initialization declarator.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class InitializationDeclaratorChecker implements Checker {

    // <init_deklarator> ::= <izravni_deklarator>
    // <init_deklarator> ::= <izravni_deklarator> OP_PRIDRUZI <inicijalizator>

    /**
     * Name of the node.
     */
    public static final String NAME = "<InitializationDeclarator>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<init_deklarator>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 69.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
