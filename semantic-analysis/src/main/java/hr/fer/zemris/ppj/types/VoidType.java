package hr.fer.zemris.ppj.types;

/**
 * <code>VoidType</code> represents a void;
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class VoidType extends Type {

    public VoidType() {
        super(false, false, false);
    }

    @Override
    public boolean implicitConversion(Type to) {
        return false;
    }

    @Override
    public boolean isLExpression() {
        return false;
    }

    @Override
    public boolean explicitConversion(Type to) {
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
    public boolean equals(Object obj) {
        return obj instanceof VoidType;
    }

}
