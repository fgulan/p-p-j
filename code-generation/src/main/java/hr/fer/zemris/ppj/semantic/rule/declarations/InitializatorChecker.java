package hr.fer.zemris.ppj.semantic.rule.declarations;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.semantic.rule.expressions.AssignExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.AssignExpressionListChecker;
import hr.fer.zemris.ppj.types.CharType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>InitializatorChecker</code> is a checker for initializator.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
 */
public class InitializatorChecker implements Checker {

    // <inicijalizator> ::= <izraz_pridruzivanja>
    // <inicijalizator> ::= L_VIT_ZAGRADA <lista_izraza_pridruzivanja> D_VIT_ZAGRADA

    /**
     * Name of the node.
     */
    public static final String NAME = "<Initializator>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<inicijalizator>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 71, 72.
     *
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean check(final Node node) {
        final Node child = node.getChild(0);

        int elemCount;
        List<Type> types = new ArrayList<>();
        Type type;

        if (child.name().equals(AssignExpressionChecker.HR_NAME)) {
            final Node assignExpr = child;
            if (!assignExpr.check()) {
                return Utils.badNode(node);
            }

            Node current = assignExpr;
            while (current.childrenCount() != 0) {
                current = current.getChild(0);
            }

            if ("NIZ_ZNAKOVA".equals(current.name())) {
                elemCount = ((String) current.getAttribute(Attribute.VALUE)).length() + 1;

                for (int i = 0; i < elemCount; i++) {
                    types.add(new CharType());
                }

                node.addAttribute(Attribute.ELEMENT_COUNT, elemCount);
                node.addAttribute(Attribute.TYPES, types);
                return true;
            }

            else {
                type = (Type) assignExpr.getAttribute(Attribute.TYPE);
                node.addAttribute(Attribute.TYPE, type);
            }

            return true;
        }

        final int size = node.childrenCount();
        for (int i = 0; i < size; i++) {
            final Node current = node.getChild(i);

            if (!current.check()) {
                return Utils.badNode(node);
            }

            if (current.name().equals(AssignExpressionListChecker.HR_NAME)) {
                types = (List<Type>) current.getAttribute(Attribute.TYPES);
                elemCount = types.size();

                node.addAttribute(Attribute.ELEMENT_COUNT, elemCount);
                node.addAttribute(Attribute.TYPES, types);

                return true;
            }

        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

}
