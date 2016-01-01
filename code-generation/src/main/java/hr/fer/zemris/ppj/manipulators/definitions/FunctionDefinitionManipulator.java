package hr.fer.zemris.ppj.manipulators.definitions;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.identifier.table.IdentifierTable;
import hr.fer.zemris.ppj.interfaces.Manipulator;
import hr.fer.zemris.ppj.types.Type;
import hr.fer.zemris.ppj.types.functions.FunctionType;

/**
 * <code>FunctionDefinitionChecker</code> is a checker for function definition.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
 */
public class FunctionDefinitionManipulator implements Manipulator {

    // <definicija_funkcije> ::= <ime_tipa> IDN L_ZAGRADA KR_VOID D_ZAGRADA <slozena_naredba>
    // <definicija_funkcije> ::= <ime_tipa> IDN L_ZAGRADA <lista_parametara> D_ZAGRADA> <slozena_naredba>

    /**
     * Name of the node.
     */
    public static final String NAME = "<FunctionDefinition>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<definicija_funkcije>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 66.
     *
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean check(final Node node) {
        Type type;
        String name;
        List<Type> types;
        List<String> names = new ArrayList<>();

        if (!node.getChild(0).check()) {
            return Utils.badNode(node);
        }

        type = (Type) node.getChild(0).getAttribute(Attribute.TYPE);
        if (type.isConst()) {
            return Utils.badNode(node);
        }

        if (!node.getChild(1).check()) {
            return Utils.badNode(node);
        }

        name = (String) node.getChild(1).getAttribute(Attribute.VALUE);

        if (IdentifierTable.GLOBAL_SCOPE.isFunctionDefined(name)) {
            return Utils.badNode(node);
        }

        if (!node.getChild(3).check()) {
            return Utils.badNode(node);
        }

        if (node.getChild(3).name().equals("KR_VOID")) {
            types = new ArrayList<>();
        }
        else {
            types = (List<Type>) node.getChild(3).getAttribute(Attribute.TYPES);
            names = (List<String>) node.getChild(3).getAttribute(Attribute.VALUES);

            node.addAttribute(Attribute.TYPES, types);
            node.addAttribute(Attribute.VALUES, names);
        }

        final FunctionType function = new FunctionType(type, types);
        final Type declared = IdentifierTable.GLOBAL_SCOPE.identifierType(name);
        if ((declared != null) && !declared.equals(function)) {
            return Utils.badNode(node);
        }

        IdentifierTable.GLOBAL_SCOPE.defineFunction(name, type, types);

        final int paramCount = names.size();
        for (int i = 0; i < paramCount; i++) {
            node.identifierTable().declareVariable(names.get(i), types.get(i));
        }

        node.addAttributeRecursive(Attribute.FUNCTION_NAME, name);

        if (!node.getChild(5).check()) {
            return Utils.badNode(node);
        }
        return true;
    }

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
            case FUNCTION_DEFINITION_1: {
                break;
            }

            case FUNCTION_DEFINITION_2: {
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
