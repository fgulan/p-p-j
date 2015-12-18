package hr.fer.zemris.ppj.types.functions;

import java.util.List;

import hr.fer.zemris.ppj.types.Type;

/**
 * <code>FunctionType</code> represents a function type.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class FunctionType extends Type {

    private final Type returnType;
    private final List<Type> argumentList;

    public FunctionType(final Type returnType, final List<Type> argumentList) {
        super(false, true, false);

        this.returnType = returnType;
        this.argumentList = argumentList;
    }

    public Type returnType() {
        return returnType;
    }

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
