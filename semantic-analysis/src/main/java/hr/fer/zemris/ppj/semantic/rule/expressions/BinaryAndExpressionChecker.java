package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.types.IntType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>BinaryAndExpressionChecker</code> is a checker for binary and expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class BinaryAndExpressionChecker implements Checker {

    // <bin_i_izraz> ::= <jednakosni_izraz>
    // <bin_i_izraz> ::= <bin_i_izraz> OP_BIN_I <jednakosni_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<BinaryAndExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<bin_i_izraz>";

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

        // <bin_i_izraz> ::= <jednakosni_izraz>
        if ("<jednakosni_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<jednakosni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, firstChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.L_EXPRESSION, firstChild.getAttribute(Attribute.L_EXPRESSION));
            return true;
        }

        Node thirdChild = node.getChild(2);
        // <bin_i_izraz> ::= <bin_i_izraz> OP_BIN_I <jednakosni_izraz>
        if ("<bin_i_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<bin_i_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <bin_i_izraz>.tip ~ int
            Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<jednakosni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <jednakosni_izraz>.tip ~ int
            Type type2 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type2.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new IntType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

}
