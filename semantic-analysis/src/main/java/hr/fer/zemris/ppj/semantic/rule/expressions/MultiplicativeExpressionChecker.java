package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.VariableType;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>MultiplicativeExpressionChecker</code> is a checker for multiplicative expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class MultiplicativeExpressionChecker implements Checker {

    // <multiplikativni_izraz> ::= <cast_izraz>
    // <multiplikativni_izraz> ::= <multiplikativni_izraz> OP_PUTA <cast_izraz>
    // <multiplikativni_izraz> ::= <multiplikativni_izraz> OP_DIJELI <cast_izraz>
    // <multiplikativni_izraz> ::= <multiplikativni_izraz> OP_MOD <cast_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<MultiplicativeExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<multiplikativni_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 57.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        Node firstChild = node.getChild(0);
        String firstSymbol = firstChild.name();

        // <multiplikativni_izraz> ::= <cast_izraz>
        if ("<cast_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<cast_izraz>)
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
        Node thirdChild = node.getChild(2);
        // <multiplikativni_izraz> ::= <multiplikativni_izraz> OP_PUTA <cast_izraz>
        if ("OP_PUTA".equals(secondSymbol)) {

            // 1. provjeri(<multiplikativni_izraz>
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <multiplikativni_izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) firstChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<cast_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <cast_izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) thirdChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, VariableType.INT);
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <multiplikativni_izraz> ::= <multiplikativni_izraz> OP_DIJELI <cast_izraz>
        if ("OP_DIJELI".equals(secondSymbol)) {

            // 1. provjeri(<multiplikativni_izraz>
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <multiplikativni_izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) firstChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<cast_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <cast_izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) thirdChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, VariableType.INT);
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <multiplikativni_izraz> ::= <multiplikativni_izraz> OP_MOD <cast_izraz>
        if ("OP_MOD".equals(secondSymbol)) {

            // 1. provjeri(<multiplikativni_izraz>
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <multiplikativni_izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) firstChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<cast_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <cast_izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) thirdChild.getAttribute(Attribute.TYPE),
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
