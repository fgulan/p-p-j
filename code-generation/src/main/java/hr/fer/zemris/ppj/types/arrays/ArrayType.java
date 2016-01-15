package hr.fer.zemris.ppj.types.arrays;

import hr.fer.zemris.ppj.types.Type;

/**
 * <code>ArrayType</code> represents a abstract array type
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public abstract class ArrayType extends Type {

    /**
     * Class constructor, specifies if the array type consists of const elements.
     *
     * @param isConst
     *            const flag.
     * @since 1.0
     */
    public ArrayType(final boolean isConst, final int size) {
        super(isConst, false, true, size);
    }

    @Override
    public abstract boolean implicitConversion(Type to);

    @Override
    public boolean explicitConversion(final Type to) {
        return false;
    }

    @Override
    public Type toArray() {
        return null;
    }

    @Override
    public abstract Type fromArray();

    @Override
    public abstract ArrayType toConst();

    @Override
    public boolean isLExpression() {
        return false;
    }
}
