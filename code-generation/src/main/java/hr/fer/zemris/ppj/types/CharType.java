package hr.fer.zemris.ppj.types;

import hr.fer.zemris.ppj.types.arrays.CharArrayType;

/**
 * <code>CharType</code> represents a char.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class CharType extends Type {

    /**
     * Class constructor, creates a char type.
     *
     * @since 1.0
     */
    public CharType() {
        super(false, false, false, 1);
    }

    @Override
    public boolean implicitConversion(final Type to) {
        if ((to instanceof IntType) || (to instanceof ConstIntType) || (to instanceof CharType)
                || (to instanceof ConstCharType)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean explicitConversion(final Type to) {
        if ((to instanceof IntType) || (to instanceof ConstIntType) || (to instanceof CharType)
                || (to instanceof ConstCharType)) {
            return true;
        }

        return false;
    }

    @Override
    public Type toArray() {
        return new CharArrayType();
    }

    @Override
    public Type fromArray() {
        return null;
    }

    @Override
    public Type toConst() {
        return new ConstCharType();
    }

    @Override
    public boolean isLExpression() {
        return true;
    }

    @Override
    public String toString() {
        return "char";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof CharType;
    }

}
