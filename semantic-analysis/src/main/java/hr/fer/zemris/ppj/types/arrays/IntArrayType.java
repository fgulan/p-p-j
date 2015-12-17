package hr.fer.zemris.ppj.types.arrays;

import hr.fer.zemris.ppj.types.IntType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>IntArrayType</code> represents a integer array.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class IntArrayType extends ArrayType {

    public IntArrayType() {
        super(false);
    }

    @Override
    public boolean implicitConversion(Type to) {
        if ((to instanceof ConstIntArrayType) || (to instanceof IntArrayType)) {
            return true;
        }

        return false;
    }

    @Override
    public Type fromArray() {
        return new IntType();
    }

    @Override
    public ArrayType toConst() {
        return new ConstIntArrayType();
    }

    @Override
    public String toString() {
        return "int[]";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntArrayType;
    }

}
