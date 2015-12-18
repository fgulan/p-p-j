package hr.fer.zemris.ppj.types;

import hr.fer.zemris.ppj.types.arrays.ConstIntArrayType;

/**
 * <code>ConstIntType</code> represents a const int.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class ConstIntType extends Type {

    private final IntType under = new IntType();

    public ConstIntType() {
        super(true, false, false);
    }

    @Override
    public boolean implicitConversion(final Type to) {
        return under.implicitConversion(to);
    }

    @Override
    public boolean explicitConversion(final Type to) {
        return under.explicitConversion(to);
    }

    @Override
    public Type toArray() {
        return new ConstIntArrayType();
    }

    @Override
    public Type fromArray() {
        return null;
    }

    @Override
    public Type toConst() {
        return this;
    }

    @Override
    public boolean isLExpression() {
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof ConstIntType;
    }

    @Override
    public String toString() {
        return "const int";
    }

}
