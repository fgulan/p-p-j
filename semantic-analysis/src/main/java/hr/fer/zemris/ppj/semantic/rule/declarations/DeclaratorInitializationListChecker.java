package hr.fer.zemris.ppj.semantic.rule.declarations;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>DeclaratorInitializationListChecker</code> is a checker for declarator initialization list.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class DeclaratorInitializationListChecker implements Checker {

    // <lista_init_deklaratora> ::= <init_deklarator>
    // <lista_init_deklaratora> ZAREZT <init_deklarator>

    /**
     * Name of the node.
     */
    public static final String NAME = "<DeclaratorInitializationList>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<lista_init_deklaratora>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 68.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
