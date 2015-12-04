package hr.fer.zemris.ppj.semantic.rule.instuctions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>ExpressionInstructionChecker</code> is a checker for expression instruction.
 *
 * @author Filip Gulan
 *
 * @version alpha
 */
public class ExpressionInstructionChecker implements Checker {

    // <izraz_naredba> ::= TOCKAZAREZ
    // <izraz_naredba> ::= <izraz> TOCKAZAREZ

    /**
     * Name of the node.
     */
    public static final String NAME = "<ExpressionInstruction>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<izraz_naredba>";

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
