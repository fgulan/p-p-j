package hr.fer.zemris.ppj.semantic.rule.instuctions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>BranchInstructionChecker</code> is a checker for branch instruction.
 *
 * @author Filip Gulan
 *
 * @version alpha
 */
public class BranchInstructionChecker implements Checker {

    // <naredba_grananja> ::= KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba>
    // <naredba_grananja> ::= KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba> KR_ELSE <naredba>

    /**
     * Name of the node.
     */
    public static final String NAME = "<BranchInstruction>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<naredba_grananja>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 63, 64.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
