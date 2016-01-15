package hr.fer.zemris.ppj.types.arrays;

import hr.fer.zemris.ppj.types.ConstIntType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>ConstIntArrayType</code> represents a const int array.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class ConstIntArrayType extends ArrayType {

    /**
     * Class constructur, creates a const int array type.
     *
     * @since 1.0
     */
    public ConstIntArrayType() {
        super(true, 4);
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
