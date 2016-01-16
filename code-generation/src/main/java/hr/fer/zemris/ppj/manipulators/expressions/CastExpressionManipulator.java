package hr.fer.zemris.ppj.manipulators.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.interfaces.Manipulator;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>CastExpressionManipulator</code> is a manipulator for cast expression.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
 */
public class CastExpressionManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

    /**
     * Name of the node.
     */
    public static final String NAME = "<CastExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<cast_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 55, 56.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final Node firstChild = node.getChild(0);
        final String firstSymbol = firstChild.name();

        // <cast_izraz> ::= <unarni_izraz>
        if ("<unarni_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<unarni_izraz>
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, firstChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.L_EXPRESSION, firstChild.getAttribute(Attribute.L_EXPRESSION));
            return true;
        }

        // <cast_izraz> ::= L_ZAGRADA <ime_tipa> D_ZAGRADA <cast_izraz>
        if ("L_ZAGRADA".equals(firstSymbol)) {
            final Node secondChild = node.getChild(1);
            // 1. provjeri(<ime_tipa>)
            if (!secondChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            final Node fourthChild = node.getChild(3);
            // 2. provjeri(<cast_izraz>
            if (!fourthChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // <cast_izraz>.tip se moze pretvoriti u <ime_tipa>.tip
            final Type from = (Type) fourthChild.getAttribute(Attribute.TYPE);
            final Type to = (Type) secondChild.getAttribute(Attribute.TYPE);
            if (!from.explicitConversion(to)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, secondChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

    @Override
    public void generate(Node node) {
//        CAST_EXPRESSION_1("<cast_izraz> ::= <unarni_izraz>"),
//        CAST_EXPRESSION_2("<cast_izraz> ::= L_ZAGRADA <ime_tipa> D_ZAGRADA <cast_izraz>"),
        switch (Production.fromNode(node)) {
            case CAST_EXPRESSION_1: {
                node.getChild(0).generate();
                break;
            }

            case CAST_EXPRESSION_2: {
                node.getChild(1).generate();
                node.getChild(3).generate();
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
