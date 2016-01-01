package hr.fer.zemris.ppj.manipulators.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.interfaces.Manipulator;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>ArgumentListManipulator</code> is a manipulator for argument list.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
 */
public class ArgumentListManipulator implements Manipulator {

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
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final Node firstChild = node.getChild(0);
        final String firstSymbol = firstChild.name();

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

        final Node thirdChild = node.getChild(2);
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

            @SuppressWarnings("unchecked")
            final List<Type> types = new ArrayList<Type>((List<Type>) firstChild.getAttribute(Attribute.TYPES));
            types.add((Type) thirdChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.TYPES, types);
            return true;
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
            case ARGUMENT_LIST_1: {
                break;
            }

            case ARGUMENT_LIST_2: {
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
