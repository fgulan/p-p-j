package hr.fer.zemris.ppj.semantic.rule.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.VariableType;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>ArgumentListChecker</code> is a checker for argument list.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class ArgumentListChecker implements Checker {

    // <lista_argumenata> ::= <izraz_pridruzivanja>
    // <lista_argumenata> ::= <lista_argumenata> ZAREZ <izraz_pridruzivanja>

    /**
     * Name of the node.
     */
    public static final String NAME = "<ArgumentList>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<lista_argumenata>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 54.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        Node firstChild = node.getChild(0);
        String firstSymbol = node.name();

        // <lista_argumenata> ::= <izraz_pridruzivanja>
        if ("<izraz_pridruzivanja>".equals(firstSymbol)) {

            // 1. provjeri(<izraz_pridruzivanja)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPES, new ArrayList<>(Arrays.asList(firstChild.getAttribute(Attribute.TYPE))));
            return true;
        }

        Node thirdChild = node.getChild(0);
        String thirdSymbol = thirdChild.name();
        // <lista_argumenata> ::= <lista_argumenata> ZAREZ <izraz_pridruzivanja>
        if ("<lista_argumenata>".equals(firstSymbol)) {

            // 1. provjeri(<lista_argumenata>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. provjeri(<izraz_pridruzivanja>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            List<VariableType> types = new ArrayList<>((List<VariableType>) firstChild.getAttribute(Attribute.TYPES));
            types.add((VariableType) thirdChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.TYPES, types);
            return true;
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

}
