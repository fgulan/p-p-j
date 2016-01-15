package hr.fer.zemris.ppj.types.arrays;

import hr.fer.zemris.ppj.types.ConstCharType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>ConstCarArrayType</code> represents a const char array.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class ConstCharArrayType extends ArrayType {

    /**
     * Class constructor, creates a const char array type.
     *
     * @since 1.0
     */
    public ConstCharArrayType() {
        super(true, 1);
    }

    @Override
    public boolean implicitConversion(final Type to) {
        if (to instanceof ConstCharArrayType) {
            return true;
        }

        return false;
    }

    @Override
    public Type fromArray() {
        return new ConstCharType();
    }

    @Override
    public ArrayType toConst() {
        return this;
    }

    @Override
    public String toString() {
        return "const char[]";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof ConstCharArrayType;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
