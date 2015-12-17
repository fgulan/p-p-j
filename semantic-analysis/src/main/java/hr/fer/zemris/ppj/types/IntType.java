package hr.fer.zemris.ppj.types;

import hr.fer.zemris.ppj.types.arrays.IntArrayType;

/**
 * <code>IntType</code> represents a int.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class IntType extends Type {

    public IntType() {
        super(false, false, false);
    }

    @Override
    public boolean implicitConversion(Type to) {
        if ((to instanceof IntType) || (to instanceof ConstIntType)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean explicitConversion(Type to) {
        if ((to instanceof IntType) || (to instanceof ConstIntType) || (to instanceof CharType)
                || (to instanceof ConstCharType)) {
            return true;
        }

        return false;
    }

    @Override
    public Type toArray() {
        return new IntArrayType();
    }

    @Override
    public Type fromArray() {
        return null;
    }

    @Override
    public Type toConst() {
        return new ConstIntType();
    }

    @Override
    public boolean isLExpression() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntType;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        return "int";
    }

}
