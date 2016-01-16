package hr.fer.zemris.ppj.manipulators.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.interfaces.Manipulator;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>AssignExpressionManipulator</code> is a manipulator for assign expression.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
 */
public class AssignExpressionManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

    /**
     * Name of the node.
     */
    public static final String NAME = "<AssignExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<izraz_pridruzivanja>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 61.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final Node firstChild = node.getChild(0);
        final String firstSymbol = firstChild.name();

        // <izraz_pridruzivanja> ::= <log_ili_izraz>
        if ("<log_ili_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<log_ili_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, firstChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.L_EXPRESSION, firstChild.getAttribute(Attribute.L_EXPRESSION));
            return true;
        }

        final Node thirdChild = node.getChild(2);
        // <izraz_pridruzivanja> ::= <postfiks_izraz> OP_PRIDRUZI <izraz_pridruzivanja>
        if ("<postfiks_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<postfiks_izraz>
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <postfiks_izraz>.l-izraz = 1
            if (!firstChild.getAttribute(Attribute.L_EXPRESSION).equals(true)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<izraz_pridruzivanja)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <izraz_pridruzivanja>.tip ~ <postfiks_izraz>.tip
            final Type from = (Type) thirdChild.getAttribute(Attribute.TYPE);
            final Type to = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!from.implicitConversion(to)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, firstChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
        case ASSIGN_EXPRESSION_1: {
            // ASSIGN_EXPRESSION_1("<izraz_pridruzivanja> ::= <log_ili_izraz>"),
            node.getChild(0).generate();
            break;
        }

        case ASSIGN_EXPRESSION_2: {
            // ASSIGN_EXPRESSION_2("<izraz_pridruzivanja> ::= <postfiks_izraz> OP_PRIDRUZI <izraz_pridruzivanja>"),
            node.getChild(0).generate();
            node.getChild(2).generate();
            break;
        }

        default:
            System.err.println("Generation reached undefined production!");
            break;
        }
    }
}
