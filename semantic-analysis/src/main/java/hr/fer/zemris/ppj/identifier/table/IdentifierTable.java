package hr.fer.zemris.ppj.identifier.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import hr.fer.zemris.ppj.types.Type;
import hr.fer.zemris.ppj.types.functions.FunctionType;

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

    private Map<String, IdentifierTypeWrapper> localIdentifiers = new HashMap<>();

    // Should be used only for checking if all declared functions are defined.
    // This is null in all identifier tables except GLOBAL_SCOPE
    public static final Set<IdentifierTypeWrapper> declaredFunctions = new HashSet<>();
    public static final Set<IdentifierTypeWrapper> definedFunctions = new HashSet<>();

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
        this.parent = parent;

        if (parent != null) {
            parent.addChild(this);
        }

        id = this_id++;
    }

    public boolean declare(String name, Type type) {
        if (type.isFunction()) {
            FunctionType function = (FunctionType) type;
            return declareFunction(name, function.returnType(), function.argumentList());
        }

        return declareVariable(name, type);
    }

    /**
     * @param name
     *            identifier of the variable to be declared.
     * @param type
     *            type of the variable to be declared.
     * @return <code>true</code> if the variable is succesfully declared, <code>false</code> otherwise.
     * @since alpha
     */
    public boolean declareVariable(String name, Type type) {
        if (isLocalDeclared(name)) {
            return false;
        }

        localIdentifiers.put(name, IdentifierTypeWrapper.forVariable(name, type));
        return true;
    }

    /**
     * @param name
     *            identifier of the function to be defined.
     * @param returnType
     *            return type of the function.
     * @param argumentList
     *            argument list of the function.
     * @return <code>true</code> if the function is succesfully declared, <code>false</code> otherwise.
     * @since alpha
     */
    public boolean declareFunction(String name, Type returnType, List<Type> argumentList) {
        // moguce je vise uzastopnih deklaracija iste funkcije, provjeriti iskljucivo varijable
        if (isLocalVariableDeclared(name)) {
            return false;
        }

        IdentifierTypeWrapper function = IdentifierTypeWrapper.forFunction(name, returnType, argumentList);
        GLOBAL_SCOPE.declaredFunctions.add(function);

        localIdentifiers.put(name, function);
        return true;
    }

    /**
     * @param name
     *            identifier of the function to be defined.
     * @param returnType
     *            return type of the function.
     * @param argumentList
     *            argument list of the function.
     * @return <code>true</code> if the function is succesfully defined, <code>false</code> otherwise.
     * @since alpha
     */
    public boolean defineFunction(String name, Type returnType, List<Type> argumentList) {
        IdentifierTypeWrapper function = IdentifierTypeWrapper.forFunction(name, returnType, argumentList);
        if (!declareFunction(name, returnType, argumentList)) {
            if (!identifierType(name).equals(function)) {
                return false;
            }
        }

        GLOBAL_SCOPE.definedFunctions.add(function);
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
        if (GLOBAL_SCOPE.localIdentifiers.containsKey(name)) {
            Type type = GLOBAL_SCOPE.identifierType(name);

            if (!type.isFunction()) {
                return true;
            }

            for (IdentifierTypeWrapper wrapper : GLOBAL_SCOPE.definedFunctions) {
                if (type.equals(wrapper.type())) {
                    return true;
                }
            }

        }

        return false;
    }

    /**
     * @return identifiers of declared variables.
     * @since alpha
     */
    public Set<String> declaredVariables() {
        Set<String> declared = new HashSet<>();
        if (parent == null) {
            for (Entry<String, IdentifierTypeWrapper> entry : localIdentifiers.entrySet()) {
                if (entry.getValue().isVariable()) {
                    declared.add(entry.getKey());
                }
            }
        }

        for (Entry<String, IdentifierTypeWrapper> entry : localIdentifiers.entrySet()) {
            if (entry.getValue().isVariable()) {
                declared.add(entry.getKey());
            }
        }

        declared.addAll(parent.declaredVariables());

        return declared;
    }

    /**
     * @return identifiers of defined functions.
     * @since alpha
     */
    public Set<String> definedFunctions() {
        Set<String> defined = new HashSet<>();
        for (IdentifierTypeWrapper function : GLOBAL_SCOPE.definedFunctions) {
            defined.add(function.name());
        }
        return defined;
    }

    /**
     * @return identifiers of declared functions.
     * @since alpha
     */
    public Set<String> declaredFunctions() {
        Set<String> declared = new HashSet<>();
        for (IdentifierTypeWrapper function : GLOBAL_SCOPE.declaredFunctions) {
            declared.add(function.name());
        }
        return declared;
    }

    /**
     * @param name
     *            identifier of the variable.
     * @return type of the variable.
     * @since alpha
     */
    public Type variable(String name) {
        if (parent == null) {
            if (localIdentifiers.containsKey(name) && localIdentifiers.get(name).isVariable()) {
                return localIdentifiers.get(name).type();
            }
            return null;
        }

        if (localIdentifiers.containsKey(name) && localIdentifiers.get(name).isVariable()) {
            return localIdentifiers.get(name).type();
        }

        return parent.variable(name);
    }

    /**
     * @param name
     *            identifier of the function.
     * @return info for the function
     * @since alpha
     */
    public FunctionType function(String name) {
        if (parent == null) {
            if (localIdentifiers.containsKey(name) && localIdentifiers.get(name).isFunction()) {
                return (FunctionType) localIdentifiers.get(name).type();
            }
            return null;
        }

        if (localIdentifiers.containsKey(name) && localIdentifiers.get(name).isFunction()) {
            return (FunctionType) localIdentifiers.get(name).type();
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

    public boolean isDeclared(String name) {
        if (parent == null) {
            return isLocalDeclared(name);
        }

        if (isLocalDeclared(name)) {
            return true;
        }

        return parent.isLocalDeclared(name);
    }

    public boolean isLocalDeclared(String name) {
        return localIdentifiers.containsKey(name);
    }

    public boolean isLocalFunctionDeclared(String name) {
        return localIdentifiers.containsKey(name) && localIdentifiers.get(name).isFunction();
    }

    public boolean isLocalVariableDeclared(String name) {
        return localIdentifiers.containsKey(name) && localIdentifiers.get(name).isVariable();
    }

    public Type identifierType(String name) {
        if (parent == null) {
            return isLocalDeclared(name) ? localIdentifiers.get(name).type() : null;
        }

        if (isLocalDeclared(name)) {
            return localIdentifiers.get(name).type();
        }

        return parent.identifierType(name);
    }

    public FunctionType localFunction(String name) {
        if (localIdentifiers.containsKey(name) && localIdentifiers.get(name).isFunction()) {
            return (FunctionType) localIdentifiers.get(name).type();
        }
        return null;
    }

    public Type localVariable(String name) {
        if (localIdentifiers.containsKey(name) && localIdentifiers.get(name).isVariable()) {
            return localIdentifiers.get(name).type();
        }
        return null;
    }

}
