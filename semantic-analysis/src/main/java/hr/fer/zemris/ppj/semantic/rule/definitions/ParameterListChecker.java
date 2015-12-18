package hr.fer.zemris.ppj.semantic.rule.definitions;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>ParameterListChecker</code> is a checker for parameter list.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
 */
public class ParameterListChecker implements Checker {

    // <lista_parametara> ::= <deklaracija_parametre>
    // <lista_parametara> ::= <lista_parametara> ZAREZ <deklaracija_parametra>

    /**
     * Name of the node.
     */
    public static final String NAME = "<ParameterList>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<lista_parametara>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 67.
     *
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean check(final Node node) {

        final int size = node.childrenCount();
        List<Type> types = new ArrayList<>();
        List<String> names = new ArrayList<>();

        if (!node.getChild(0).check() || ((size > 1) && !node.getChild(2).check())) {
            return Utils.badNode(node);
        }

        Node decl;
        if (size == 1) {
            decl = node.getChild(0);
        }
        else {
            decl = node.getChild(2);
        }

        final String name = (String) decl.getAttribute(Attribute.VALUE);
        final Type type = (Type) decl.getAttribute(Attribute.TYPE);

        if (node.getChild(0).name().equals(ParameterListChecker.HR_NAME)) {
            final List<String> oldNames = (List<String>) node.getChild(0).getAttribute(Attribute.VALUES);
            if (oldNames.contains(name)) {
                return Utils.badNode(node);
            }

            names = oldNames;
            types = (List<Type>) node.getChild(0).getAttribute(Attribute.TYPES);
        }

        names.add(name);
        types.add(type);

        node.addAttribute(Attribute.VALUES, names);
        node.addAttribute(Attribute.TYPES, types);

        return true;
    }

}
