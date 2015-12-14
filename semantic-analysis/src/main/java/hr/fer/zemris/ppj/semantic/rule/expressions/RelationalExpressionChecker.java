package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.VariableType;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>RelationalExpressionChecker</code> is a checker for relational expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class RelationalExpressionChecker implements Checker {

    // <odnosni_izraz> ::= <aditivni_izraz>
    // <odnosni_izraz> ::= <odnosni_izraz> OP_LT <aditivni_izraz>
    // <odnosni_izraz> ::= <odnosni_izraz> OP_GT <aditivni_izraz>
    // <odnosni_izraz> ::= <odnosni_izraz> OP_LTE <aditivni_izraz>
    // <odnosni_izraz> ::= <odnosni_izraz> OP_GTE <aditivni_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<RelationalExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<odnosni_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 58.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        Node firstChild = node.getChild(0);
        String firstSymbol = firstChild.name();

        // <odnosni_izraz> ::= <aditivni_izraz>
        if ("<aditivni_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<aditivni_izraz>)
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

        // <odnosni_izraz> ::= <odnosni_izraz> OP_LT <aditivni_izraz>
        if ("OP_LT".equals(secondSymbol)) {

            // 1. provjeri(<odnosni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <odnosni_izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) firstChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<aditivni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <aditivni_izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) thirdChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, VariableType.INT);
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <odnosni_izraz> ::= <odnosni_izraz> OP_GT <aditivni_izraz>
        if ("OP_GT".equals(secondSymbol)) {

            // 1. provjeri(<odnosni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <odnosni_izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) firstChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<aditivni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <aditivni_izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) thirdChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, VariableType.INT);
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <odnosni_izraz> ::= <odnosni_izraz> OP_LTE <aditivni_izraz>
        if ("OP_LTE".equals(secondSymbol)) {

            // 1. provjeri(<odnosni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <odnosni_izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) firstChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<aditivni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <aditivni_izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) thirdChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, VariableType.INT);
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <odnosni_izraz> ::= <odnosni_izraz> OP_GTE <aditivni_izraz>
        if ("OP_GTE".equals(secondSymbol)) {

            // 1. provjeri(<odnosni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <odnosni_izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) firstChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<aditivni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <aditivni_izraz>.tip ~ int
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
