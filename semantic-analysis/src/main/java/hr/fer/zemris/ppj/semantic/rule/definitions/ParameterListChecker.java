package hr.fer.zemris.ppj.semantic.rule.definitions;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.VariableType;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>ParameterListChecker</code> is a checker for parameter list.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
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
     * @since alpha
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean check(Node node) {

        int size = node.childrenCount();
        List<VariableType> types = new ArrayList<>();
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

        String name = (String) decl.getAttribute(Attribute.VALUE);
        VariableType type = (VariableType) decl.getAttribute(Attribute.TYPE);

        if (node.getChild(0).name().equals(ParameterListChecker.HR_NAME)) {
            List<String> oldNames = (List<String>) node.getChild(0).getAttribute(Attribute.VALUES);
            if (oldNames.contains(name)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            names = oldNames;
            types = (List<VariableType>) node.getChild(0).getAttribute(Attribute.TYPES);
        }

        names.add(name);
        types.add(type);

        node.addAttribute(Attribute.VALUES, names);
        node.addAttribute(Attribute.TYPES, types);

        return true;
    }

}
