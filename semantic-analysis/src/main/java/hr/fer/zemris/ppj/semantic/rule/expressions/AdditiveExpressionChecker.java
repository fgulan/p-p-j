package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.types.IntType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>AdditiveExpressionChecker</code> is a checker for aditive expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class AdditiveExpressionChecker implements Checker {

    // <aditivni_izraz> ::= <multiplikativni_izraz>
    // <aditivni_izraz> ::= <aditivni_izraz> PLUS <multiplikativni_izraz>
    // <aditivni_izraz> ::= <aditivni_izraz> MINUS <multiplikativni_izraz>

    /**
     * Name of the node.
     */
    public static final String NAME = "<AdditiveExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<aditivni_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 57, 58.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        Node firstChild = node.getChild(0);
        String firstSymbol = firstChild.name();

        // <aditivni_izraz> ::= <multiplikativni_izraz>
        if ("<multiplikativni_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<multiplikativni_izraz>)
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

        // <aditivni_izraz> ::= <aditivni_izraz> PLUS <multiplikativni_izraz>
        if ("PLUS".equals(secondSymbol)) {

            // 1. provjeri(<aditivni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            // 2. <aditivni_izraz>.tip ~ int
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<multiplikativni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            Type type2 = (Type) thirdChild.getAttribute(Attribute.TYPE);
            // 4. <multiplikativni_izraz>.tip ~ int
            if (!type2.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new IntType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <aditivni_izraz> ::= <aditivni_izraz> MINUS <multiplikativni_izraz>
        if ("MINUS".equals(secondSymbol)) {

            // 1. provjeri(<aditivni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <aditivni_izraz>.tip ~ int
            Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<multiplikativni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <multiplikativni_izraz>.tip ~ int
            Type type2 = (Type) thirdChild.getAttribute(Attribute.TYPE);
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
