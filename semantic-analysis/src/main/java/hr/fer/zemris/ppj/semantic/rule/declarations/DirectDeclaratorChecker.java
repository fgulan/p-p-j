package hr.fer.zemris.ppj.semantic.rule.declarations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.semantic.exceptions.MysteriousBugException;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.semantic.rule.definitions.ParameterListChecker;
import hr.fer.zemris.ppj.types.Type;
import hr.fer.zemris.ppj.types.VoidType;
import hr.fer.zemris.ppj.types.functions.FunctionType;

/**
 * <code>DirectDeclaratorChecker</code> is a checker for direct declarator.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class DirectDeclaratorChecker implements Checker {

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
     * @since alpha
     */
    @Override
    public boolean check(Node node) {

        Node idn = node.getChild(0);
        if (!idn.check()) {
            return Utils.badNode(node);
        }

        Type type = (Type) node.getAttribute(Attribute.ITYPE);
        if (type.equals(new VoidType())) {
            return Utils.badNode(node);
        }

        String name = (String) idn.getAttribute(Attribute.VALUE);

        int size = node.childrenCount();
        if (size == 1) {
            if (!node.identifierTable().declare(name, type)) {
                return Utils.badNode(node);
            }

            node.addAttribute(Attribute.TYPE, type);
            return true;
        }

        for (int i = 1; i < size; i++) {
            Node child = node.getChild(i);

            if (!child.check()) {
                return Utils.badNode(node);
            }

            if (child.name().equals("BROJ")) {
                Type arrayType = type.toArray();
                if (!node.identifierTable().declareVariable(name, arrayType)) {
                    return Utils.badNode(node);
                }

                int value = (int) child.getAttribute(Attribute.VALUE);
                if ((value < MIN_ARRAY_SIZE) || (value > MAX_ARRAY_SIZE)) {
                    return Utils.badNode(node);
                }

                node.addAttribute(Attribute.ELEMENT_COUNT, value);
                node.addAttribute(Attribute.TYPE, arrayType);

                return true;
            }

            if (child.name().equals("KR_VOID")) {

                List<Type> args = new ArrayList<>();
                if (!Utils.handleFunction(node.identifierTable(), name, args, type)) {
                    return Utils.badNode(node);
                }

                node.addAttribute(Attribute.TYPE, new FunctionType(type, Arrays.asList((Type) new VoidType())));

                return true;
            }

            if (child.name().equals(ParameterListChecker.HR_NAME)) {
                @SuppressWarnings("unchecked")
                List<Type> args = (List<Type>) child.getAttribute(Attribute.TYPES);

                if (!Utils.handleFunction(node.identifierTable(), name, args, type)) {
                    return Utils.badNode(node);
                }

                node.addAttribute(Attribute.TYPE,
                        new FunctionType(type, (List<Type>) child.getAttribute(Attribute.TYPES)));

                return true;
            }

        }

        throw new MysteriousBugException(
                "If this line ever executes, Parser has failed or an if statement" + " is missing a return statement. "
                        + "Expected: 'BROJ', 'KR_VOID' or " + ParameterListChecker.HR_NAME + ".");
        // Uncomment before deployment
        // return true;
    }

}
