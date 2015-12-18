package hr.fer.zemris.ppj.identifier.table;

import java.util.List;

import hr.fer.zemris.ppj.types.Type;
import hr.fer.zemris.ppj.types.functions.FunctionType;

/**
 * <code>IdentifierTypeWrapper</code> wraps a identifer type for functions / variables.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class IdentifierTypeWrapper {

    private final String name;

    private final Type type;

    /**
     * Creates a wrapper for the variable with the specified name and type.
     *
     * @param name
     *            the name.
     * @param type
     *            the type.
     * @return created wrapper.
     * @since 1.0
     */
    public static IdentifierTypeWrapper forVariable(final String name, final Type type) {
        return new IdentifierTypeWrapper(name, type);
    }

    /**
     * Creates a wrapper for the function with the specified name, retur type and argument list.
     *
     * @param name
     *            the name.
     * @param returnType
     *            the return type.
     * @param argumentList
     *            the argument list.
     * @return created wrapper.
     * @since 1.0
     */
    public static IdentifierTypeWrapper forFunction(final String name, final Type returnType,
            final List<Type> argumentList) {
        return new IdentifierTypeWrapper(name, new FunctionType(returnType, argumentList));
    }

    private IdentifierTypeWrapper(final String name, final Type type) {
        this.name = name;
        this.type = type;
    }

    /**
     * @return <code>true</code> if the wrapper encapsulates a function, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean isFunction() {
        return type.isFunction();
    }

    /**
     * @return <code>true</code> if the wrapper encapsulates a variable, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean isVariable() {
        return !type.isFunction();
    }

    /**
     * @return name of the encapsulated identifier.
     * @since 1.0
     */
    public String name() {
        return name;
    }

    /**
     * @return type of the encapsulated identifier.
     * @since 1.0
     */
    public Type type() {
        return type;
    }

    @Override
    public String toString() {
        return name + ": " + type;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    };

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof IdentifierTypeWrapper) {
            final IdentifierTypeWrapper other = (IdentifierTypeWrapper) obj;

            return other.name.equals(name) && other.type.equals(type);
        }

        return false;
    }
}
