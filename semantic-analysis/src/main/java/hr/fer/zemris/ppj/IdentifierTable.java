package hr.fer.zemris.ppj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <code>IdentifierTable</code> is a hierarhical identifier table.
 *
 * @author Jan Kelemen, Domagoj Polancec
 *
 * @version alpha.
 */
public class IdentifierTable {

    private static int id = 0;

    /**
     * Global scope of the translation unit.
     */
    public static final IdentifierTable GLOBAL_SCOPE = new IdentifierTable();

    private int this_id;

    private IdentifierTable parent;

    private List<IdentifierTable> children = new ArrayList<>();

    private Map<String, VariableType> declaredVariables;

    private Map<String, FunctionWrapper> declaredFunctions;

    private Map<String, FunctionWrapper> definedFunctions;

    /**
     * Class constructor, creates a empty identifer table. (Used for the global scope)
     *
     * @since alpha
     */
    public IdentifierTable() {
        this(null);
    }

    /**
     * Class constructor, creates a child identifier table.
     *
     * @param parent
     *            the parent table.
     * @since alpha
     */
    public IdentifierTable(IdentifierTable parent) {
        this(parent, new HashMap<>(), new HashMap<>(), new HashMap<>());

        if (parent != null) {
            parent.addChild(this);
        }
    }

    /**
     * Class constructor, specifies everything.
     *
     * @param parent
     *            the parent.
     * @param declaredVariables
     *            declared variables in the scope.
     * @param declaredFunctions
     *            declared functions in the scope.
     * @param definedFunctions
     *            defined functions in the scope.
     * @since alpha
     */
    public IdentifierTable(IdentifierTable parent, Map<String, VariableType> declaredVariables,
            Map<String, FunctionWrapper> declaredFunctions, Map<String, FunctionWrapper> definedFunctions) {
        this.parent = parent;
        this.declaredVariables = declaredVariables;
        this.declaredFunctions = declaredFunctions;
        this.definedFunctions = definedFunctions;
        this_id = id++;
    }

    /**
     * @param name
     *            identifier of the variable to be declared.
     * @param type
     *            type of the variable to be declared.
     * @return <code>true</code> if the variable is succesfully declared, <code>false</code> otherwise.
     * @since alpha
     */
    public boolean declareVariable(String name, VariableType type) {
        if (isLocalDeclared(name)) {
            return false;
        }

        declaredVariables.put(name, type);
        return true;
    }

    /**
     * @param name
     *            identifier of the function to be defined.
     * @param function
     *            the function.
     * @return <code>true</code> if the function is succesfully declared, <code>false</code> otherwise.
     * @since alpha
     */
    public boolean declareFunction(String name, FunctionWrapper function) {
        if (isLocalVariableDeclared(name)) {
            return false;
        }

        declaredFunctions.put(name, function);
        return true;
    }

    /**
     * @param name
     *            identifier of the function to be defined.
     * @param function
     *            the function.
     * @return <code>true</code> if the function is succesfully defined, <code>false</code> otherwise.
     * @since alpha
     */
    public boolean defineFunction(String name, FunctionWrapper function) {
        if (!declareFunction(name, function)) {
            return false;
        }

        definedFunctions.put(name, function);
        // GLOBAL_SCOPE.definedFunctions.put(name, function);
        return true;
    }

    /**
     * @param name
     *            variable identifer.
     * @return <code>true</code> if the variable is declared, <code>false</code> otherwise.
     * @since alpha
     */
    public boolean isVariableDeclared(String name) {
        return variable(name) != null;
    }

    /**
     * @param name
     *            function identifer.
     * @return <code>true</code> if the function is declared, <code>false</code> otherwise.
     * @since alpha
     */
    public boolean isFunctionDeclared(String name) {
        return function(name) != null;
    }

    /**
     * @param name
     *            function identifer.
     * @return <code>true</code> if the function is defined, <code>false</code> otherwise.
     * @since alpha
     */
    public boolean isFunctionDefined(String name) {
        return GLOBAL_SCOPE.definedFunctions.containsKey(name);
    }

    /**
     * @return identifiers of declared variables.
     * @since alpha
     */
    public Set<String> declaredVariables() {
        if (parent == null) {
            return new HashSet<>(declaredVariables.keySet());
        }

        Set<String> variables = new HashSet<>(declaredVariables.keySet());
        variables.addAll(parent.declaredVariables());

        return variables;
    }

    /**
     * @return identifiers of defined functions.
     * @since alpha
     */
    public Set<String> definedFunctions() {
        return GLOBAL_SCOPE.definedFunctions.keySet();
    }

    /**
     * @return identifiers of declared functions.
     * @since alpha
     */
    public Set<String> declaredFunctions() {
        if (parent == null) {
            return new HashSet<>(declaredFunctions.keySet());
        }

        Set<String> functions = new HashSet<>(declaredFunctions.keySet());
        functions.addAll(parent.declaredFunctions());

        return functions;
    }

    /**
     * @param name
     *            identifier of the variable.
     * @return type of the variable.
     * @since alpha
     */
    public VariableType variable(String name) {
        if (parent == null) {
            return declaredVariables.get(name);
        }

        if (declaredVariables.containsKey(name)) {
            return declaredVariables.get(name);
        }

        return parent.variable(name);
    }

    /**
     * @param name
     *            identifier of the function.
     * @return info for the function
     * @since alpha
     */
    public FunctionWrapper function(String name) {
        if (parent == null) {
            return declaredFunctions.get(name);
        }

        if (declaredFunctions.containsKey(name)) {
            return declaredFunctions.get(name);
        }

        return parent.function(name);
    }

    /**
     * Adds a child scope to the table.
     *
     * @param child
     *            the child to be added.
     * @since alpha
     */
    public void addChild(IdentifierTable child) {
        children.add(child);
    }

    @Override
    public String toString() {
        return String.valueOf(this_id) + (parent != null ? parent.toString() : "");
    }

    public boolean isLocalDeclared(String name) {
        return isLocalFunctionDeclared(name) || isLocalVariableDeclared(name);
    }

    public boolean isLocalFunctionDeclared(String name) {
        return declaredFunctions.containsKey(name);
    }

    public boolean isLocalVariableDeclared(String name) {
        return declaredVariables.containsKey(name);
    }

    public FunctionWrapper localFunction(String name) {
        return declaredFunctions.get(name);
    }

    public VariableType localVariable(String name) {
        return declaredVariables.get(name);
    }
}
