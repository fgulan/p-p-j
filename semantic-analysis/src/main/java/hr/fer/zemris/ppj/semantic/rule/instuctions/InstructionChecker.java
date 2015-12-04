package hr.fer.zemris.ppj.semantic.rule.instuctions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>InstructionChecker</code> is a checker for instruction.
 *
 * @author Filip Gulan
 *
 * @version alpha
 */
public class InstructionChecker implements Checker {

    // <naredba> ::= <slozena_naredba>
    // <naredba> ::= <izraz_naredba>
    // <naredba> ::= <naredba_grananja>
    // <naredba> ::= <naredba_petlje>
    // <naredba> ::= <naredba_skoka>

    /**
     * Name of the node.
     */
    public static final String NAME = "<Instruction>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<naredba>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 63.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
