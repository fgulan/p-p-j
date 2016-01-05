package hr.fer.zemris.ppj.manipulators.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.interfaces.Manipulator;
import hr.fer.zemris.ppj.types.IntType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>EqualityExpressionManipulator</code> is a manipulator for equality expression.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
 */
public class EqualityExpressionManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

    /**
     * Name of the node.
     */
    public static final String NAME = "<EqualityExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<jednakosni_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 58, 59.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final Node firstChild = node.getChild(0);
        final String firstSymbol = firstChild.name();

        // <jedankosni_izraz> ::= <odnosni_izraz>
        if ("<odnosni_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<odnosni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, firstChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.L_EXPRESSION, firstChild.getAttribute(Attribute.L_EXPRESSION));
            return true;
        }

        final Node secondChild = node.getChild(1);
        final String secondSymbol = secondChild.name();
        final Node thirdChild = node.getChild(2);

        // <jednakosni_izraz> ::= <jednakosni_izraz> OP_EQ <odnosni_izraz>
        if ("OP_EQ".equals(secondSymbol)) {

            // 1. provjeri(<jednakosni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <jednakosni_izraz>.tip ~ int
            final Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<odnosni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <jednakosni_izraz>.tip ~ int
            final Type type2 = (Type) thirdChild.getAttribute(Attribute.TYPE);
            if (!type2.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new IntType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <jednakosni_izraz> ::= <jednakosni_izraz> OP_NEQ <odnosni_izraz>
        if ("OP_NEQ".equals(secondSymbol)) {

            // 1. provjeri(<jednakosni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <jednakosni_izraz>.tip ~ int
            final Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<odnosni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <jednakosni_izraz>.tip ~ int
            final Type type2 = (Type) thirdChild.getAttribute(Attribute.TYPE);
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

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
            case EQUALITY_EXPRESSION_1: {
                break;
            }

            case EQUALITY_EXPRESSION_2: {
                break;
            }

            case EQUALITY_EXPRESSION_3: {
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
