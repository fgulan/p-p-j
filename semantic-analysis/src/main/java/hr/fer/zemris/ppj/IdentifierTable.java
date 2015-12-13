package hr.fer.zemris.ppj;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <code>IdentifierTable</code> is a hierarhical identifier table.
 *
 * @author Jan Kelemen
 *
 * @version alpha.
 */
public class IdentifierTable {

    /**
     * Global scope of the translation unit.
     */
    public static final IdentifierTable GLOBAL_SCOPE = new IdentifierTable();

    private IdentifierTable parent;

    private Map<String, VariableType> declaredVariables;

    private Map<String, FunctionWrapper> declaredFunctions;

    private Map<String, FunctionWrapper> definedFunctions;

    /**
     * Class constructor, creates a empty identifer table. (Used for the global scope)
     *
     * @since alpha
     */
    public IdentifierTable() {
        this(null, new HashMap<>(), new HashMap<>(), new HashMap<>());
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
        if (declaredVariables.containsKey(name)) {
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
        if (declaredFunctions.containsKey(name)) {
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
        return true;
    }

    /**
     * @param name
     *            variable identifer.
     * @return <code>true</code> if the variable is declared, <code>false</code> otherwise.
     * @since alpha
     */
    public boolean isVariableDeclared(String name) {
        // TODO: IMPLEMENT THIS
        return true;
    }

    /**
     * @param name
     *            function identifer.
     * @return <code>true</code> if the function is declared, <code>false</code> otherwise.
     * @since alpha
     */
    public boolean isFunctionDeclared(String name) {
        // TODO: IMPLEMENT THIS
        return true;
    }

    /**
     * @param name
     *            function identifer.
     * @return <code>true</code> if the function is defined, <code>false</code> otherwise.
     * @since alpha
     */
    public boolean isFunctionDefined(String name) {
        // TODO: IMPLEMENT THIS
        return true;
    }

    /**
     * @return identifiers of declared variables.
     * @since alpha
     */
    public Set<String> declaredVariables() {
        // TODO: IMPLEMENT THIS
        return null;
    }

    /**
     * @return identifiers of defined functions.
     * @since alpha
     */
    public Set<String> definedFunctions() {
        // TODO: IMPLEMENT THIS
        return null;
    }

    /**
     * @return identifiers of declared functions.
     * @since alpha
     */
    public Set<String> declaredFunctions() {
        // TODO: IMPLEMENT THIS
        return null;
    }
}
