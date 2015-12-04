package hr.fer.zemris.ppj.semantic.rule.definitions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>FunctionDefinitionChecker</code> is a checker for function definition.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class FunctionDefinitionChecker implements Checker {

    // <definicija_funkcije> ::= <ime_tipa> IDN L_ZAGRADA KR_VOID D_ZAGRADA <slozena_naredba>
    // <definicija_funkcije> ::= <ime_tipa> IDN L_ZAGRADA <lista_parametara> D_ZAGRADA> <slozena_naredba>

    /**
     * Name of the node.
     */
    public static final String NAME = "<FunctionDefinition>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<definicija_funkcije>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 66.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
