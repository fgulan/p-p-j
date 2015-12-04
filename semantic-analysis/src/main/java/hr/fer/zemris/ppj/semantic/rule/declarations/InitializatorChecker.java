package hr.fer.zemris.ppj.semantic.rule.declarations;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>InitializatorChecker</code> is a checker for initializator.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class InitializatorChecker implements Checker {

    // <inicijalizator> ::= <izraz_pridruzivanja>
    // <inicijalizator> ::= L_VIT_ZAGRADA <lista_izraza_pridruzivanja> D_VIT_ZAGRADA

    /**
     * Name of the node.
     */
    public static final String NAME = "<Initializator>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<inicijalizator>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 71, 72.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
