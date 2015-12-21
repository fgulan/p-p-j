package hr.fer.zemris.ppj.types;

/**
 * <code>Type</code> is a abstract type of the identifier.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public abstract class Type {

    private final boolean isConst;
    private final boolean isFunction;
    private final boolean isArray;

    /**
     * Class constructor specifies the characteristics of the type.
     *
     * @param isConst
     *            <code>true</code> if the type is a const type, <code>false</code> otherwise.
     * @param isFunction
     *            <code>true</code> if the type is a function type, <code>false</code> otherwise.
     * @param isArray
     *            <code>true</code> if the type is a array type, <code>false</code> otherwise.
     * @since 1.0
     */
    public Type(final boolean isConst, final boolean isFunction, final boolean isArray) {
        this.isConst = isConst;
        this.isArray = isArray;
        this.isFunction = isFunction;
    }

    /**
     * Checks if a type can be implicitly converted to another type.
     *
     * @param to
     *            type to which a conversion is checked.
     * @return <code>true</code> if the type can be implicitly converted to <code>to</code>, <code>false</code>
     *         otherwise.
     * @since 1.0
     */
    public abstract boolean implicitConversion(Type to);

    /**
     * Checks if a type can be explicitly converted to another type.
     *
     * @param to
     *            type to which a conversion is checked.
     * @return <code>true</code> if the type can be explicitly converted to <code>to</code>, <code>false</code>
     *         otherwise.
     * @since 1.0
     */
    public abstract boolean explicitConversion(Type to);

    /**
     * @return array type of the type, <code>null</code> if the array can't be created for the type.
     * @since 1.0
     */
    public abstract Type toArray();

    /**
     * @return type of which a array consists, <code>null</code> if the type isn't a array type.
     * @since 1.0
     */
    public abstract Type fromArray();

    /**
     * @return const type of the type, <code>null</code> if the type can't be const.
     * @since 1.0
     */
    public abstract Type toConst();

    /**
     * @return <code>true</code> if the type is a l-value, <code>false</code> otherwise.
     * @since 1.0
     */
    public abstract boolean isLExpression();

    /**
     * @return <code>true</code> if the type is const, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean isConst() {
        return isConst;
    }

    /**
     * @return <code>true</code> if the type is a function, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean isFunction() {
        return isFunction;
    }

    /**
     * @return <code>true</code> if the type is a array, <code>false</code> otherwise.
     * @since 1.0
     */
    public boolean isArray() {
        return isArray;
    }
}
