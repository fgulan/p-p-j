package hr.fer.zemris.ppj.types;

/**
 * <code>VoidType</code> represents a void;
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class VoidType extends Type {

    /**
     * Class constructor, specifies a void type.
     *
     * @since 1.0
     */
    public VoidType() {
        super(false, false, false);
    }

    @Override
    public boolean implicitConversion(final Type to) {
        return false;
    }

    @Override
    public boolean isLExpression() {
        return false;
    }

    @Override
    public boolean explicitConversion(final Type to) {
        return false;
    }

    @Override
    public Type toArray() {
        return null;
    }

    @Override
    public Type fromArray() {
        return null;
    }

    @Override
    public Type toConst() {
        return null;
    }

    @Override
    public String toString() {
        return "void";
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof VoidType;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
