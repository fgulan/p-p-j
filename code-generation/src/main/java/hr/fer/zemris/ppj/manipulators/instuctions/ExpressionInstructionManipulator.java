package hr.fer.zemris.ppj.manipulators.instuctions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>ExpressionInstructionChecker</code> is a checker for expression instruction.
 *
 * @author Filip Gulan
 *
 * @version 1.0
 */
public class ExpressionInstructionManipulator implements Manipulator {

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
     * @since 1.0
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

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
            case EXPRESSION_INSTRUCTION_1: {
                break;
            }

            case EXPRESSION_INSTRUCTION_2: {
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
