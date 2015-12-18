package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>ExpressionChecker</code> is a checker for expression.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class ExpressionChecker implements Checker {

    // <izraz> ::= <izraz_pridruzivanja>
    // <izraz> ::= <izraz> ZAREZ <izraz_pridruzivanja>

    /**
     * Name of the node.
     */
    public static final String NAME = "<Expression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 61, 62.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final Node firstChild = node.getChild(0);
        final String firstSymbol = firstChild.name();

        // <izraz> ::= <izraz_pridruzivanja>
        if ("<izraz_pridruzivanja>".equals(firstSymbol)) {

            // 1. provjeri(<izraz_pridruzivanja>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, firstChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.L_EXPRESSION, firstChild.getAttribute(Attribute.L_EXPRESSION));
            return true;
        }

        final Node thirdChild = node.getChild(2);
        // <izraz> ::= <izraz> ZAREZ <izraz_pridruzivanja>
        if ("<izraz>".equals(firstSymbol)) {

            // 1. provjeri(<izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. provjeri(<izraz_pridruzivanja>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, thirdChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

}
