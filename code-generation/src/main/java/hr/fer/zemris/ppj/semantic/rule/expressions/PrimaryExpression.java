package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.semantic.rule.Generator;
import hr.fer.zemris.ppj.types.CharType;
import hr.fer.zemris.ppj.types.IntType;
import hr.fer.zemris.ppj.types.Type;
import hr.fer.zemris.ppj.types.arrays.ConstCharArrayType;

/**
 * <code>PrimaryExpressionChecker</code> is a checker for primary expression.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class PrimaryExpression implements Checker, Generator {

    // <primarni_izraz> ::= IDN
    // <primarni_izraz> ::= BROJ
    // <primarni_izraz> ::= ZNAK
    // <primarni_izraz> ::= NIZ_ZNAKOVA
    // <primarni_izraz> ::= L_ZAGRADA <izraz> D_ZAGRADA

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
        // TODO Auto-generated method stub
        
    }
}
