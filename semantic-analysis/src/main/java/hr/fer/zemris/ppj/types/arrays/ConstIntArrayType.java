package hr.fer.zemris.ppj.types.arrays;

import hr.fer.zemris.ppj.types.ConstIntType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>ConstIntArrayType</code> represents a const int array.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class ConstIntArrayType extends ArrayType {

    public ConstIntArrayType() {
        super(true);
    }

    @Override
    public boolean implicitConversion(final Type to) {
        if (to instanceof ConstIntArrayType) {
            return true;
        }

        return false;
    }

    @Override
    public Type fromArray() {
        return new ConstIntType();
    }

    @Override
    public ArrayType toConst() {
        return this;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof ConstIntArrayType;
    }

    @Override
    public String toString() {
        return "const int[]";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
