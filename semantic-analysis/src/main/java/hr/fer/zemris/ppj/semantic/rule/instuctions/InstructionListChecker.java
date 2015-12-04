package hr.fer.zemris.ppj.semantic.rule.instuctions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>InstructionListChecker</code> is a checker for instruction list.
 *
 * @author Filip Gulan
 *
 * @version alpha
 */
public class InstructionListChecker implements Checker {

    // <lista_naredbi> ::= <naredba>
    // <lista_naredbi> ::= <lista_naredbi> <naredba>

    /**
     * Name of the node.
     */
    public static final String NAME = "<InstructionList>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<lista_naredbi>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 62, 63.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
