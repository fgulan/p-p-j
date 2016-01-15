package hr.fer.zemris.ppj.types;

import hr.fer.zemris.ppj.types.arrays.ConstCharArrayType;

/**
 * <code>ConstCharType</code> represents a const char.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class ConstCharType extends Type {

    private final CharType under = new CharType();

    /**
     * Class constructor, creates a const char type.
     *
     * @since 1.0
     */
    public ConstCharType() {
        super(true, false, false, 1);
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
        return new ConstCharArrayType();
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
    public String toString() {
        return "const char";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof ConstCharType;
    }
}
