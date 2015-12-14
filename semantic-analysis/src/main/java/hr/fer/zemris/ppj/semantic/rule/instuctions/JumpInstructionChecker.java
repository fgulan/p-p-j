package hr.fer.zemris.ppj.semantic.rule.instuctions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
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
    @Deprecated
    public boolean check(Node node) {
        // <naredba_skoka> ::= KR_CONTINUE TOCKAZAREZ
        // <naredba_skoka> ::= KR_BREAK TOCKAZAREZ
        if ("KR_CONTINUE".equals(node.getChild(0).name()) || "KR_BREAK".equals(node.getChild(0).name())) {
            boolean insideLoop = (Boolean) node.getAttribute(Attribute.INSIDE_LOOP);
            if (!insideLoop) {
                SemanticErrorReporter.report(node);
                return false;
            }
            return true;
        }

        // <naredba_skoka> ::= KR_RETURN TOCKAZAREZ
        // TODO for now
        // <naredba_skoka> ::= KR_RETURN <izraz> TOCKAZAREZ
        // TODO for now
        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

}
