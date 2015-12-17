package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>AssignExpressionChecker</code> is a checker for assign expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class AssignExpressionChecker implements Checker {

    // <izraz_pridruzivanja> ::= <log_ili_izraz>
    // <izraz_pridruzivanja> ::= <postfiks_izraz> OP_PRIDRUZI <izraz_pridruzivanja>

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
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        Node firstChild = node.getChild(0);
        String firstSymbol = firstChild.name();

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

        Node thirdChild = node.getChild(2);
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
            Type from = (Type) thirdChild.getAttribute(Attribute.TYPE);
            Type to = (Type) firstChild.getAttribute(Attribute.TYPE);
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

}
