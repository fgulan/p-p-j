package hr.fer.zemris.ppj.semantic.rule.declarations;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>DeclarationChecker</code> is a checker for declaration.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class DeclarationChecker implements Checker {

    // <deklaracija> ::= <ime_tipa> <lista_init_deklaratora> TOCKAZAREZ

    /**
     * Name of the node.
     */
    public static final String NAME = "<Declaration>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<deklaracija>";

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
