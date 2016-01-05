package hr.fer.zemris.ppj.manipulators.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.interfaces.Manipulator;
import hr.fer.zemris.ppj.types.CharType;
import hr.fer.zemris.ppj.types.IntType;
import hr.fer.zemris.ppj.types.Type;
import hr.fer.zemris.ppj.types.arrays.ConstCharArrayType;

/**
 * <code>PrimaryExpressionManipulator</code> is a manipulator for primary expression.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
 */
public class PrimaryExpressionManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

    /**
     * Name of the node.
     */
    public static final String NAME = "<PrimaryExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<primarni_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 51, 52.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final Node firstChild = node.getChild(0);
        final String firstSymbol = firstChild.name();

        // <primarni_izraz> ::= IDN
        if ("IDN".equals(firstSymbol)) {

            // 1. IDN.ime je deklarirano
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            final String name = (String) firstChild.getAttribute(Attribute.VALUE);
            if (!node.identifierTable().isDeclared(name)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            final Type type = node.identifierTable().identifierType(name);

            node.addAttribute(Attribute.TYPE, type);
            node.addAttribute(Attribute.L_EXPRESSION, type.isLExpression());
            return true;
        }

        // <primarni_izraz> ::= BROJ
        if ("BROJ".equals(firstSymbol)) {

            // 1. vrijednost je u rasponu tipa int
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new IntType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <primarni_izraz> ::= ZNAK
        if ("ZNAK".equals(firstSymbol)) {

            // 1. znak je ispravan po 4.3.2
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new CharType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <primarni_izraz> ::= NIZ_ZNAKOVA
        if ("NIZ_ZNAKOVA".equals(firstSymbol)) {

            // 1. konstantni niz znakova je ispravan po 4.3.2
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new ConstCharArrayType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <primarni_izraz> ::= L_ZAGRADA <izraz> D_ZAGRADA
        if ("L_ZAGRADA".equals(firstSymbol)) {
            final Node expression = node.getChild(1);

            // 1. provjeri(<izraz>)
            if (!expression.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, expression.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.L_EXPRESSION, expression.getAttribute(Attribute.L_EXPRESSION));
            return true;
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
            case PRIMARY_EXPRESSION_1: {
                break;
            }

            case PRIMARY_EXPRESSION_2: {
                break;
            }

            case PRIMARY_EXPRESSION_3: {
                break;
            }

            case PRIMARY_EXPRESSION_4: {
                break;
            }

            case PRIMARY_EXPRESSION_5: {
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
