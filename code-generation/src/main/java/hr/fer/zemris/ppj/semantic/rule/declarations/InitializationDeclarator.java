package hr.fer.zemris.ppj.semantic.rule.declarations;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.semantic.rule.Generator;
import hr.fer.zemris.ppj.types.Type;
import hr.fer.zemris.ppj.types.arrays.ArrayType;

/**
 * <code>InitializationDeclaratorChecker</code> is a checker for initialization declarator.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
 */
public class InitializationDeclarator implements Checker, Generator {

    // <init_deklarator> ::= <izravni_deklarator>
    // <init_deklarator> ::= <izravni_deklarator> OP_PRIDRUZI <inicijalizator>

    /**
     * Name of the node.
     */
    public static final String NAME = "<InitializationDeclarator>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<init_deklarator>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 69.
     *
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean check(final Node node) {

        final int size = node.childrenCount();
        final Node child = node.getChild(0);
        child.addAttributeRecursive(Attribute.ITYPE, node.getAttribute(Attribute.ITYPE));
        if (!child.check()) {
            return Utils.badNode(node);
        }

        final Type type = (Type) child.getAttribute(Attribute.TYPE);
        Integer elemCount = (Integer) child.getAttribute(Attribute.ELEMENT_COUNT);
        if (elemCount == null) {
            elemCount = 1;
        }

        if (size == 1) {
            // 2. <izravni_deklarator> != {const(T), niz(const(T))}
            final boolean first = type.isConst();
            final boolean second = type.isArray() ? ((ArrayType) type).fromArray().isConst() : false;
            if (first || second) {
                return Utils.badNode(node);
            }

            return true;
        }

        for (int i = 1; i < size; i++) {
            final Node current = node.getChild(i);

            if (!current.check()) {
                return Utils.badNode(node);
            }

            if (current.name().equals(Initializator.HR_NAME)) {
                List<Type> initTypes;
                if (type.isArray()) {
                    initTypes = (List<Type>) current.getAttribute(Attribute.TYPES);
                }
                else {
                    initTypes = new ArrayList<>();
                    final Type initType = (Type) current.getAttribute(Attribute.TYPE);
                    if (initType == null) {
                        return Utils.badNode(node);
                    }
                    initTypes.add((Type) current.getAttribute(Attribute.TYPE));
                }

                if (handleInits(elemCount, type, initTypes)) {
                    return true;
                }
                else {
                    return Utils.badNode(node);
                }
            }
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

    private static boolean handleInits(final Integer elemCount, Type myType, final List<Type> initTypes) {
        if ((initTypes == null) || (myType == null) || (elemCount < initTypes.size())) {
            return false;
        }

        if (myType.isArray()) {
            myType = myType.fromArray();
        }

        for (final Type type : initTypes) {
            if (!type.implicitConversion(myType)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void generate(Node node) {
        // TODO Auto-generated method stub
        
    }
}
