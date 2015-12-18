package hr.fer.zemris.ppj.types;

/**
 * <code>Type</code> is a variable
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public abstract class Type {

    private final boolean isConst;
    private final boolean isFunction;
    private final boolean isArray;

    public Type(boolean isConst, boolean isFunction, boolean isArray) {
        this.isConst = isConst;
        this.isArray = isArray;
        this.isFunction = isFunction;
    }

    public abstract boolean implicitConversion(Type to);

    public abstract boolean explicitConversion(Type to);

    public abstract Type toArray();

    public abstract Type fromArray();

    public abstract Type toConst();

    public abstract boolean isLExpression();

    public boolean isConst() {
        return isConst;
    }

    public boolean isFunction() {
        return isFunction;
    }

    public boolean isArray() {
        return isArray;
    }
}
