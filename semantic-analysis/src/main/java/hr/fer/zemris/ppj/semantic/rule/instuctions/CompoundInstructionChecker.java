package hr.fer.zemris.ppj.semantic.rule.instuctions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>CompoundInstructionChecker</code> is a checker for compound instruction.
 *
 * @author Filip Gulan
 *
 * @version alpha
 */
public class CompoundInstructionChecker implements Checker {

    // <slozena_naredba> ::= L_VIT_ZAGRADA <lista_naredbi> D_VIT_ZAGRADA
    // <slozena_naredba> ::= L_VIT_ZAGRADA <lista_deklaracija> <lista_naredbi> D_VIT_ZAGRADA

    /**
     * Name of the node.
     */
    public static final String NAME = "<CompoundInstruction>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<slozena_naredba>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 62.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
