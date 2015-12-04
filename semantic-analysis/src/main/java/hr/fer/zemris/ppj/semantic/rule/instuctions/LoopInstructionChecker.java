package hr.fer.zemris.ppj.semantic.rule.instuctions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>LoopInstructionChecker</code> is a checker for loop instruction.
 *
 * @author Filip Gulan
 *
 * @version alpha
 */
public class LoopInstructionChecker implements Checker {

    // <naredba_petlje> ::= KR_WHILE L_ZAGRADA <izraz> D_ZAGRADA <naredba>
    // <naredba_petlje> ::= KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredbs> D_ZAGRADA <naredba>
    // <naredba_petlje> ::= KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredba> <izraz> D_ZAGRADA <naredba>

    /**
     * Name of the node.
     */
    public static final String NAME = "<LoopInstruction>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<naredba_petlje>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 64.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
