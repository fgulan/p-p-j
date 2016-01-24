package hr.fer.zemris.ppj.manipulators.definitions;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.code.Reg;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.code.generator.CallStack;
import hr.fer.zemris.ppj.code.generator.FRISCGenerator;
import hr.fer.zemris.ppj.interfaces.Manipulator;
import hr.fer.zemris.ppj.types.IntType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>ParameterListManipulator</code> is a manipulator for parameter list.
 *
 * @author Domagoj Polancec
 *
 * @version 1.1
 */
public class ParameterListManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

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

        if (node.getChild(0).name().equals(ParameterListManipulator.HR_NAME)) {
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

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
            case PARAMETER_LIST_1:
            case PARAMETER_LIST_2: {
                // PARAMETER_LIST_1("<lista_parametara> ::= <deklaracija_parametra>"),
                Node temp = node;
                Stack<Node> parameterStack = new Stack<>();
                while (Production.fromNode(temp) == Production.PARAMETER_DECLARATION_2) {
                    parameterStack.push(temp.getChild(2));
                    temp = temp.getChild(0);
                }
                parameterStack.push(temp.getChild(0));

                CallStack.setScopeStart();
                while (!parameterStack.isEmpty()) {
                    Node parameterNode = parameterStack.pop();
                    String name = (String) parameterNode.getChild(1).getAttribute(Attribute.VALUE);
                    CallStack.push(name, new IntType());
                    FRISCGenerator.generateCommand(ch.push(Reg.R0));
                }
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
