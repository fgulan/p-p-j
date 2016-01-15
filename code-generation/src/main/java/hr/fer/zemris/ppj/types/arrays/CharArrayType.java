package hr.fer.zemris.ppj.types.arrays;

import hr.fer.zemris.ppj.types.CharType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>CharArrayType</code> is a char array.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class CharArrayType extends ArrayType {

    /**
     * Class constructor, creates a char array type.
     *
     * @since 1.0
     */
    public CharArrayType() {
        super(false, 1);
    }

    @Override
    public boolean implicitConversion(final Type to) {
        if ((to instanceof CharArrayType) || (to instanceof ConstCharArrayType)) {
            return true;
        }

        return false;
    }

    @Override
    public Type fromArray() {
        return new CharType();
    }

    @Override
    public ArrayType toConst() {
        return new ConstCharArrayType();
    }

    @Override
    public String toString() {
        return "char[]";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof CharArrayType;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
