package hr.fer.zemris.ppj.semantic.rule.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>AssignExpressionListChecker</code> is a checker for assign expression list.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class AssignExpressionListChecker implements Checker {

    // <lista_izraza_pridruzivanja> ::= <izraz_pridruzivanja>
    // <lista_izraza_pridruzivanja> ::= <lista_izraza_pridruzivanja> ZAREZ <izraz_pridruzivanja>

    /**
     * Name of the node.
     */
    public static final String NAME = "<AssignExpressionList>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<lista_izraza_pridruzivanja>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 72.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        Node firstChild = node.getChild(0);
        String firstSymbol = firstChild.name();

        // <lista_izraza_pridruzivanja> ::= <izraz_pridruzivanja>
        if ("<izraz_pridruzivanja>".equals(firstSymbol)) {

            // 1. provjeri(<izraz_pridruzivanja>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            List<Type> types = new ArrayList<>(Arrays.asList((Type) firstChild.getAttribute(Attribute.TYPE)));
            node.addAttribute(Attribute.TYPES, types);
            node.addAttribute(Attribute.ELEMENT_COUNT, 1);
            return true;
        }

        Node thirdChild = node.getChild(2);
        // <lista_izraza_pridruzivanja> ::= <lista_izraza_pridruzivanja> ZAREZ <izraz_pridruzivanja>
        if ("<lista_izraza_pridruzivanja>".equals(firstSymbol)) {

            // 1. provjeri(<lista_izraza_pridruzivanja>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. provjeri(<izraz_pridruzivanja>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            @SuppressWarnings("unchecked")
            List<Type> types = new ArrayList<>((List<Type>) firstChild.getAttribute(Attribute.TYPES));
            types.add((Type) thirdChild.getAttribute(Attribute.TYPE));
            int elementCount = (Integer) firstChild.getAttribute(Attribute.ELEMENT_COUNT) + 1;
            node.addAttribute(Attribute.TYPES, types);
            node.addAttribute(Attribute.ELEMENT_COUNT, elementCount);
            return true;
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

}
