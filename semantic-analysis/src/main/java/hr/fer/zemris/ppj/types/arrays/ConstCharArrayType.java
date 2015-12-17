package hr.fer.zemris.ppj.types.arrays;

import hr.fer.zemris.ppj.types.ConstCharType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>ConstCarArrayType</code> represents a const char array.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class ConstCharArrayType extends ArrayType {

    public static int length(String value) {
        int length = 0;
        for (int i = 1; i < (value.length() - 1); i++) {
            if (value.charAt(i) == '\\') {
                i++;
            }
            length++;
        }
        return length + 1;
    }

    public ConstCharArrayType() {
        super(true);
    }

    @Override
    public boolean implicitConversion(Type to) {
        if (to instanceof ConstCharArrayType) {
            return true;
        }

        return false;
    }

    @Override
    public Type fromArray() {
        return new ConstCharType();
    }

    @Override
    public ArrayType toConst() {
        return this;
    }

    @Override
    public String toString() {
        return "const char[]";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ConstCharArrayType;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
