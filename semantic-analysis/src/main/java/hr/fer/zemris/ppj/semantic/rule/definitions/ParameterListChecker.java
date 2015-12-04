package hr.fer.zemris.ppj.semantic.rule.definitions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>ParameterListChecker</code> is a checker for parameter list.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class ParameterListChecker implements Checker {

    // <lista_parametara> ::= <deklaracija_parametre>
    // <lista_parametara> ::= <lista_parametara> ZAREZ <deklaracija_parametra>

    /**
     * Name of the node.
     */
    public static final String NAME = "<ParameterList>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<lista_parametara>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 67.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
