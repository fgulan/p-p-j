package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.VariableType;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>PrimaryExpressionChecker</code> is a checker for primary expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class PrimaryExpressionChecker implements Checker {

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
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        Node firstChild = node.getChild(0);
        String firstSymbol = firstChild.name();

        // <primarni_izraz> ::= IDN
        if ("IDN".equals(firstSymbol)) {

            // 1. IDN.ime je deklarirano
            if (!firstChild.check()) {
                System.out.println(HR_NAME + " ::= " + firstChild.toString());
                return false;
            }

            node.addAttribute(Attribute.TYPE, firstChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.L_EXPRESSION, firstChild.getAttribute(Attribute.L_EXPRESSION));
            return true;
        }

        // <primarni_izraz> ::= BROJ
        if ("BROJ".equals(firstSymbol)) {

            // 1. vrijednost je u rasponu tipa int
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, VariableType.INT);
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

            node.addAttribute(Attribute.TYPE, VariableType.CHAR);
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

            node.addAttribute(Attribute.CELEM_COUNT, firstChild.getAttribute(Attribute.CELEM_COUNT));
            node.addAttribute(Attribute.TYPE, VariableType.CONST_CHAR_ARRAY);
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <primarni_izraz> ::= L_ZAGRADA <izraz> D_ZAGRADA
        if ("L_ZAGRADA".equals(firstSymbol)) {
            Node expression = node.getChild(1);

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

}
