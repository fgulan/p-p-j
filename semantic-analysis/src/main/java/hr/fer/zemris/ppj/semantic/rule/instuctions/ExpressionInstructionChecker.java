package hr.fer.zemris.ppj.semantic.rule.instuctions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
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
    public boolean check(final Node node) {
        final Node expression = node.getChild(0);

        // <izraz_naredba> ::= <izraz> TOCKAZAREZ
        if ("<izraz>".equals(expression.name())) {

            if (!expression.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            node.addAttribute(Attribute.TYPE, expression.getAttribute(Attribute.TYPE));
            return true;
        }

        // <izraz_naredba> ::= TOCKAZAREZ
        if ("TOCKAZAREZ".equals(expression.name())) {
            node.addAttribute(Attribute.TYPE, expression.getAttribute(Attribute.TYPE));
            return true;
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

}
