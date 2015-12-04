package hr.fer.zemris.ppj.semantic.rule.instuctions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>JumpInstructionChecker</code> is a checker for jump instruction.
 *
 * @author Filip Gulan
 *
 * @version alpha
 */
public class JumpInstructionChecker implements Checker {

    // <naredba_skoka> ::= KR_CONTINUE TOCKAZAREZ
    // <naredba_skoka> ::= KR_BREAK TOCKAZAREZ
    // <naredba_skoka> ::= KR_RETURN TOCKAZAREZ
    // <naredba_skoka> ::= KR_RETURN <izraz> TOCKAZAREZ

    /**
     * Name of the node.
     */
    public static final String NAME = "<JumpInstruction>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<naredba_skoka>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 64, 65.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
