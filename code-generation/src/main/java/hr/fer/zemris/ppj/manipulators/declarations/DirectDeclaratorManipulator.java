package hr.fer.zemris.ppj.manipulators.declarations;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.interfaces.Manipulator;
import hr.fer.zemris.ppj.manipulators.definitions.ParameterListManipulator;
import hr.fer.zemris.ppj.types.Type;
import hr.fer.zemris.ppj.types.VoidType;
import hr.fer.zemris.ppj.types.functions.FunctionType;

/**
 * <code>DirectDeclaratorChecker</code> is a checker for direct declarator.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
 */
public class DirectDeclaratorManipulator implements Manipulator {

    private static final int MIN_ARRAY_SIZE = 1;
    private static final int MAX_ARRAY_SIZE = 1024;

    // <izravni_deklarator> ::= IDN
    // <izravni_deklarator> ::= IDN L_UGL_ZAGRADA BROJ D_UGL_ZAGRADA
    // <izravni_deklarator> ::= IDN L_ZAGRADA KR_VOID D_ZAGRADA
    // <izravni_deklarator> ::= IDN L_ZAGRADA <lista_parametara> D_ZAGRADA

    /**
     * Name of the node.
     */
    public static final String NAME = "<DirectDeclarator>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<izravni_deklarator>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 69, 70.
     *
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean check(final Node node) {

        final Node idn = node.getChild(0);
        if (!idn.check()) {
            return Utils.badNode(node);
        }

        final Type type = (Type) node.getAttribute(Attribute.ITYPE);

        final String name = (String) idn.getAttribute(Attribute.VALUE);

        final int size = node.childrenCount();
        if (size == 1) {

            if (type.equals(new VoidType())) {
                return Utils.badNode(node);
            }

            if (!node.identifierTable().declare(name, type)) {
                return Utils.badNode(node);
            }

            node.addAttribute(Attribute.TYPE, type);
            return true;
        }

        for (int i = 1; i < size; i++) {
            final Node child = node.getChild(i);

            if (!child.check()) {
                return Utils.badNode(node);
            }

            if (child.name().equals("BROJ")) {

                if (type.equals(new VoidType())) {
                    return Utils.badNode(node);
                }

                final Type arrayType = type.toArray();
                if (!node.identifierTable().declareVariable(name, arrayType)) {
                    return Utils.badNode(node);
                }

                final int value = (int) child.getAttribute(Attribute.VALUE);
                if ((value < MIN_ARRAY_SIZE) || (value > MAX_ARRAY_SIZE)) {
                    return Utils.badNode(node);
                }

                node.addAttribute(Attribute.ELEMENT_COUNT, value);
                node.addAttribute(Attribute.TYPE, arrayType);

                return true;
            }

            if (child.name().equals("KR_VOID")) {

                final List<Type> args = new ArrayList<>();
                if (!Utils.handleFunction(node.identifierTable(), name, args, type)) {
                    return Utils.badNode(node);
                }

                node.addAttribute(Attribute.TYPE, new FunctionType(type, new ArrayList<Type>()));

                return true;
            }

            if (child.name().equals(ParameterListManipulator.HR_NAME)) {

                final List<Type> args = (List<Type>) child.getAttribute(Attribute.TYPES);

                if (!Utils.handleFunction(node.identifierTable(), name, args, type)) {
                    return Utils.badNode(node);
                }

                node.addAttribute(Attribute.TYPE,
                        new FunctionType(type, (List<Type>) child.getAttribute(Attribute.TYPES)));

                return true;
            }

        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
            case DIRECT_DECLARATOR_1: {
                break;
            }

            case DIRECT_DECLARATOR_2: {
                break;
            }

            case DIRECT_DECLARATOR_3: {
                break;
            }

            case DIRECT_DECLARATOR_4: {
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
