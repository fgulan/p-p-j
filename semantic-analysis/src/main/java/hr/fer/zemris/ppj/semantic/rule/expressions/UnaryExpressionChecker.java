package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.VariableType;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>UnaryExpressionChecker</code> is a checker for unary expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class UnaryExpressionChecker implements Checker {

    // <unarni_izraz> ::= <postfiks_izraz>
    // <unarni_izraz> ::= OP_INC <unarni_izraz>
    // <unarni_izraz> ::= OP_DEC <unarni_izraz>
    // <unarni_izraz> ::= <unarni_operator> <cast_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<UnaryExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<unarni_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 54, 55.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        Node firstChild = node.getChild(0);
        String firstSymbol = firstChild.name();

        // <unarni_izraz> ::= <postfiks_izraz>
        if ("<postfiks_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<posfiks_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, firstChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.L_EXPRESSION, firstChild.getAttribute(Attribute.L_EXPRESSION));
            return true;
        }

        Node secondChild = node.getChild(1);
        String secondSymbol = secondChild.name();

        // <unarni_izraz> ::= OP_INC <unarni_izraz>
        if ("OP_INC".equals(firstSymbol)) {

            // 1. provjeri(<unarni_izraz>)
            if (!secondChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <unarni_izraz>.l-izraz = 1
            if (!secondChild.getAttribute(Attribute.L_EXPRESSION).equals(true)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <unarni_izraz.tip ~ int
            if (!VariableType.implicitConversion((VariableType) secondChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, VariableType.INT);
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }
        // <unarni_izraz> ::= OP_DEC <unarni_izraz>
        if ("OP_DEC".equals(firstSymbol)) {

            // 1. provjeri(<unarni_izraz>)
            if (!secondChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <unarni_izraz>.l-izraz = 1
            if (!secondChild.getAttribute(Attribute.L_EXPRESSION).equals(true)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <unarni_izraz.tip ~ int
            if (!VariableType.implicitConversion((VariableType) secondChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, VariableType.INT);
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <unarni_izraz> ::= <unarni_operator> <cast_izraz>
        if ("<unarni_operator>".equals(firstSymbol)) {

            // 1. provjeri(<cast_izraz>)
            if (!secondChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <cast_izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) secondChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, VariableType.INT);
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

}
