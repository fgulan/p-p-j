package hr.fer.zemris.ppj.identifier.table;

import java.util.List;

import hr.fer.zemris.ppj.types.Type;
import hr.fer.zemris.ppj.types.functions.FunctionType;

/**
 * <code>IdentifierTypeWrapper</code> wraps a identifer type for functions / variables.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class IdentifierTypeWrapper {

    private final String name;

    private final Type type;

    public static IdentifierTypeWrapper forVariable(String name, Type type) {
        return new IdentifierTypeWrapper(name, type);
    }

    public static IdentifierTypeWrapper forFunction(String name, Type returnType, List<Type> argumentList) {
        return new IdentifierTypeWrapper(name, new FunctionType(returnType, argumentList));
    }

    IdentifierTypeWrapper(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public boolean isFunction() {
        return type.isFunction();
    }

    public boolean isVariable() {
        return !type.isFunction();
    }

    public String name() {
        return name;
    }

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
    public boolean equals(Object obj) {
        if (obj instanceof IdentifierTypeWrapper) {
            IdentifierTypeWrapper other = (IdentifierTypeWrapper) obj;

            return other.name.equals(name) && other.type.equals(type);
        }

        return false;
    }
}
