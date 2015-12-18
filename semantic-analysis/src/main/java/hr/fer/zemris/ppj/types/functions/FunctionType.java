package hr.fer.zemris.ppj.types.functions;

import java.util.List;

import hr.fer.zemris.ppj.types.Type;

/**
 * <code>FunctionType</code> represents a function type.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class FunctionType extends Type {

    private final Type returnType;
    private final List<Type> argumentList;

    /**
     * Class constructor, specifies the function type.
     *
     * @param returnType
     *            the return type of the function.
     * @param argumentList
     *            the argument list of the function.
     * @since 1.0
     */
    public FunctionType(final Type returnType, final List<Type> argumentList) {
        super(false, true, false);

        this.returnType = returnType;
        this.argumentList = argumentList;
    }

    /**
     * @return return type of the function.
     * @since 1.0
     */
    public Type returnType() {
        return returnType;
    }

    /**
     * @return argument list of the function.
     * @since 1.0
     */
    public List<Type> argumentList() {
        return argumentList;
    }

    @Override
    public boolean implicitConversion(final Type to) {
        return false;
    }

    @Override
    public boolean explicitConversion(final Type to) {
        return false;
    }

    @Override
    public Type toArray() {
        return null;
    }

    @Override
    public Type fromArray() {
        return null;
    }

    @Override
    public Type toConst() {
        return null;
    }

    @Override
    public boolean isLExpression() {
        return false;
    }

    @Override
    public String toString() {
        return "funkcija(" + returnType + ", " + argumentList.toString() + ")";
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof FunctionType) {
            final FunctionType other = (FunctionType) obj;

            return other.returnType.equals(returnType) && other.argumentList.equals(argumentList);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
