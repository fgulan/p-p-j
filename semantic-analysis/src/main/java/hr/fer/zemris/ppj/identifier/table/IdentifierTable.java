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
 * @version 1.0
 */
public class IdentifierTable {

    private static int id = 0;

    /**
     * Global scope of the translation unit.
     */
    public static final IdentifierTable GLOBAL_SCOPE = new IdentifierTable();

    private final int this_id;

    private final IdentifierTable parent;

    private final List<IdentifierTable> children = new ArrayList<>();

    private final Map<String, IdentifierTypeWrapper> localIdentifiers = new HashMap<>();

    // Should be used only for checking if all declared functions are defined.
    /**
     * All declared functions, used only in final check.
     */
    public static final ArrayList<IdentifierTypeWrapper> declaredFunctions = new ArrayList<>();
    /**
     * All defined functions, used only in final check.
     */
    public static final ArrayList<IdentifierTypeWrapper> definedFunctions = new ArrayList<>();

    /**
     * Class constructor, creates a empty identifer table. (Used for the global scope)
     *
     * @since 1.0
     */
    public IdentifierTable() {
        this(null);
    }

    /**
     * Class constructor, creates a child identifier table.
     *
     * @param parent
     *            the parent table.
     * @since 1.0
     */
    public IdentifierTable(final IdentifierTable parent) {
        this.parent = parent;

        if (parent != null) {
            parent.addChild(this);
        }

        this_id = id++;
    }

    /**
     * Declares a identifier in the local scope.
     *
     * @param name
     *            name of the identifier.
     * @param type
     *            type of the identifier.
     * @return <code>true</code> if the identifier is successfuly declared.
     * @since 1.0
     */
    public boolean declare(final String name, final Type type) {
        if (type.isFunction()) {
            final FunctionType function = (FunctionType) type;
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
     * @since 1.0
     */
    public boolean declareVariable(final String name, final Type type) {
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
     * @since 1.0
     */
    public boolean declareFunction(final String name, final Type returnType, final List<Type> argumentList) {
        // moguce je vise uzastopnih deklaracija iste funkcije, provjeriti iskljucivo varijable
        if (isLocalVariableDeclared(name)) {
            return false;
        }

        final IdentifierTypeWrapper function = IdentifierTypeWrapper.forFunction(name, returnType, argumentList);
        IdentifierTable.declaredFunctions.add(function);

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
     * @since 1.0
     */
    public boolean defineFunction(final String name, final Type returnType, final List<Type> argumentList) {
        final IdentifierTypeWrapper function = IdentifierTypeWrapper.forFunction(name, returnType, argumentList);
        if (!declareFunction(name, returnType, argumentList)) {
            if (!identifierType(name).equals(function)) {
                return false;
            }
        }

        IdentifierTable.definedFunctions.add(function);
        return true;
    }

    /**
     * @param name
     *            variable identifer.
     * @return <code>true</code> if the variable is declared, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean isVariableDeclared(final String name) {
        return variable(name) != null;
    }

    /**
     * @param name
     *            function identifer.
     * @return <code>true</code> if the function is declared, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean isFunctionDeclared(final String name) {
        return function(name) != null;
    }

    /**
     * @param name
     *            function identifer.
     * @return <code>true</code> if the function is defined, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean isFunctionDefined(final String name) {
        if (IdentifierTable.GLOBAL_SCOPE.localIdentifiers.containsKey(name)) {
            final Type type = IdentifierTable.GLOBAL_SCOPE.identifierType(name);

            if (!type.isFunction()) {
                return true;
            }

            for (final IdentifierTypeWrapper wrapper : IdentifierTable.definedFunctions) {
                if (type.equals(wrapper.type()) && wrapper.name().equals(name)) {
                    return true;
                }
            }

        }

        return false;
    }

    /**
     * @return identifiers of declared variables.
     * @since 1.0
     */
    public Set<String> declaredVariables() {
        final Set<String> declared = new HashSet<>();
        if (parent == null) {
            for (final Entry<String, IdentifierTypeWrapper> entry : localIdentifiers.entrySet()) {
                if (entry.getValue().isVariable()) {
                    declared.add(entry.getKey());
                }
            }
        }

        for (final Entry<String, IdentifierTypeWrapper> entry : localIdentifiers.entrySet()) {
            if (entry.getValue().isVariable()) {
                declared.add(entry.getKey());
            }
        }

        declared.addAll(parent.declaredVariables());

        return declared;
    }

    /**
     * @return identifiers of defined functions.
     * @since 1.0
     */
    public Set<String> definedFunctions() {
        final Set<String> defined = new HashSet<>();
        for (final IdentifierTypeWrapper function : IdentifierTable.definedFunctions) {
            defined.add(function.name());
        }
        return defined;
    }

    /**
     * @return identifiers of declared functions.
     * @since 1.0
     */
    public Set<String> declaredFunctions() {
        final Set<String> declared = new HashSet<>();
        for (final IdentifierTypeWrapper function : IdentifierTable.declaredFunctions) {
            declared.add(function.name());
        }
        return declared;
    }

    /**
     * @param name
     *            identifier of the variable.
     * @return type of the variable.
     * @since 1.0
     */
    public Type variable(final String name) {
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
     * @since 1.0
     */
    public FunctionType function(final String name) {
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
     * @since 1.0
     */
    public void addChild(final IdentifierTable child) {
        children.add(child);
    }

    @Override
    public String toString() {
        return String.valueOf(this_id) + (parent != null ? parent.toString() : "");
    }

    /**
     * Checks if a identifier with the specified name is declared.
     *
     * @param name
     *            the name.
     * @return <code>true</code> if the identifier is declared, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean isDeclared(final String name) {
        if (parent == null) {
            return isLocalDeclared(name);
        }

        if (isLocalDeclared(name)) {
            return true;
        }

        return parent.isDeclared(name);
    }

    /**
     * Checks if a identifier is declared in a local scope.
     *
     * @param name
     *            name of the identifier.
     * @return <code>true</code> if the idenfitifer is declared, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean isLocalDeclared(final String name) {
        return localIdentifiers.containsKey(name);
    }

    /**
     * Checks if a function is declared in a local scope.
     *
     * @param name
     *            name of the function.
     * @return <code>true</code> if the function is declared, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean isLocalFunctionDeclared(final String name) {
        return localIdentifiers.containsKey(name) && localIdentifiers.get(name).isFunction();
    }

    /**
     * Checks if a variable is declared in a local scope.
     *
     * @param name
     *            name of the variable.
     * @return <code>true</code> if the variable is declared, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean isLocalVariableDeclared(final String name) {
        return localIdentifiers.containsKey(name) && localIdentifiers.get(name).isVariable();
    }

    /**
     * @param name
     *            name of the identifier.
     * @return type of the identifier with the specified name, <code>null</code> if the identifier doesn't exist.
     * @since 1.0
     */
    public Type identifierType(final String name) {
        if (parent == null) {
            return isLocalDeclared(name) ? localIdentifiers.get(name).type() : null;
        }

        if (isLocalDeclared(name)) {
            return localIdentifiers.get(name).type();
        }

        return parent.identifierType(name);
    }

    /**
     * @param name
     *            name of the function.
     * @return function type of the function with the specified name, <code>null</code> if the function doesn't exist.
     * @since 1.0
     */
    public FunctionType localFunction(final String name) {
        if (localIdentifiers.containsKey(name) && localIdentifiers.get(name).isFunction()) {
            return (FunctionType) localIdentifiers.get(name).type();
        }
        return null;
    }

    /**
     * @param name
     *            name of the variable.
     * @return type of the variable with the specified name, <code>null</code> if the variable doesn't exist.
     * @since 1.0
     */
    public Type localVariable(final String name) {
        if (localIdentifiers.containsKey(name) && localIdentifiers.get(name).isVariable()) {
            return localIdentifiers.get(name).type();
        }
        return null;
    }

}
