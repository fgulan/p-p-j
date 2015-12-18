package hr.fer.zemris.ppj.types.arrays;

import hr.fer.zemris.ppj.types.Type;

/**
 * <code>ArrayType</code> represents a abstract array type
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public abstract class ArrayType extends Type {

    public ArrayType(final boolean isConst) {
        super(isConst, false, true);
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
