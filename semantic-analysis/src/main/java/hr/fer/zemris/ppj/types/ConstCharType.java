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

    public ConstCharType() {
        super(true, false, false);
    }

    @Override
    public boolean implicitConversion(Type to) {
        return under.implicitConversion(to);
    }

    @Override
    public boolean explicitConversion(Type to) {
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
    public boolean equals(Object obj) {
        return obj instanceof ConstCharType;
    }
}
