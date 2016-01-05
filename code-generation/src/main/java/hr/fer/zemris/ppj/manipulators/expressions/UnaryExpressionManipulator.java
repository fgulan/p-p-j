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
 * <code>UnaryExpressionManipulator</code> is a manipulator for unary expression.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
 */
public class UnaryExpressionManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

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
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final Node firstChild = node.getChild(0);
        final String firstSymbol = firstChild.name();

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

        final Node secondChild = node.getChild(1);

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
            final Type type = (Type) secondChild.getAttribute(Attribute.TYPE);
            if (!type.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new IntType());
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
            final Type type = (Type) secondChild.getAttribute(Attribute.TYPE);
            if (!type.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new IntType());
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
            final Type type = (Type) secondChild.getAttribute(Attribute.TYPE);
            if (!type.implicitConversion(new IntType())) {
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
            case UNARY_EXPRESSION_1: {
                break;
            }

            case UNARY_EXPRESSION_2: {
                break;
            }

            case UNARY_EXPRESSION_3: {
                break;
            }

            case UNARY_EXPRESSION_4: {
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
