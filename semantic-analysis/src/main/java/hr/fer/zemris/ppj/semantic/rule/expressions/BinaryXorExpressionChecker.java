package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.VariableType;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>BinaryXorExpressionChecker</code> is a checker for binary xor expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class BinaryXorExpressionChecker implements Checker {

    // <bin_xili_izraz> ::= <bin_i_izraz>
    // <bin_xili_izraz> ::= <bin_xili_izraz> OP_BIN_XILI <bin_i_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<BinaryXorExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<bin_xili_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 59.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        Node firstChild = node.getChild(0);
        String firstSymbol = firstChild.name();

        // <bin_xili_izraz> ::= <bin_i_izraz>
        if ("<bin_i_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<bin_i_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, firstChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.L_EXPRESSION, firstChild.getAttribute(Attribute.L_EXPRESSION));
            return true;
        }

        Node thirdChild = node.getChild(2);
        // <bin_xili_izraz> ::= <bin_xili_izraz> OP_BIN_XILI <bin_i_izraz>
        if ("<bin_xili_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<bin_xili_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <bin_xili_izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) firstChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<bin_i_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <bin_i_izraz>.tip ~ int
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
