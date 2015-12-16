package hr.fer.zemris.ppj.semantic.rule.definitions;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.IdentifierTable;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.VariableType;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>FunctionDefinitionChecker</code> is a checker for function definition.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class FunctionDefinitionChecker implements Checker {

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
     * @since alpha
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean check(Node node) {
        VariableType type;
        String name;
        List<VariableType> types;
        List<String> names = new ArrayList<>();

        if (!node.getChild(0).check()) {
            return Utils.badNode(node);
        }

        type = (VariableType) node.getChild(0).getAttribute(Attribute.TYPE);
        if (VariableType.isConst(type)) {
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
            types = (List<VariableType>) node.getChild(3).getAttribute(Attribute.TYPES);
            names = (List<String>) node.getChild(3).getAttribute(Attribute.VALUES);

            node.addAttribute(Attribute.TYPES, types);
            node.addAttribute(Attribute.VALUES, names);
        }

        if (!Utils.handleFunction(IdentifierTable.GLOBAL_SCOPE, name, types, type)) {
            return Utils.badNode(node);
        }

        int paramCount = names.size();
        for (int i = 0; i < paramCount; i++) {
            node.identifierTable().declareVariable(names.get(i), types.get(i));
        }

        node.addAttributeRecursive(Attribute.FUNCTION_NAME, name);

        if (!node.getChild(5).check()) {
            return Utils.badNode(node);
        }
        return true;
    }

}
