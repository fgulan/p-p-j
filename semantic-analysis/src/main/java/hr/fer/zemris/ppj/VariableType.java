package hr.fer.zemris.ppj;

/**
 * <code>Type</code> types of variables.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public enum VariableType {

    /**
     * void
     */
    VOID,

    /**
     * int
     */
    INT,

    /**
     * const int
     */
    CONST_INT,

    /**
     * { 1, 2, 3 }
     */
    INT_ARRAY,

    /**
     * { 1, 2, 3 }
     */
    CONST_INT_ARRAY,

    /**
     * char
     */
    CHAR,

    /**
     * const char
     */
    CONST_CHAR,

    /**
     * { 'a', 'b' }
     */
    CHAR_ARRAY,

    /**
     * "meow"
     */
    CONST_CHAR_ARRAY;

    /**
     * @param arrayType
     *            array type.
     * @return type of a element in the array.
     * @since alpha
     */
    public static VariableType fromArrayType(VariableType arrayType) {
        switch (arrayType) {
            case INT_ARRAY:
                return INT;
            case CONST_INT_ARRAY:
                return CONST_INT;
            case CHAR_ARRAY:
                return CHAR;
            case CONST_CHAR_ARRAY:
                return CONST_CHAR;
            default:
                return null;
        }
    }

    /**
     * @param type
     *            raw type
     * @return array type of the raw type
     * @since alpha
     */
    public static VariableType toArrayType(VariableType type) {
        switch (type) {
            case INT:
                return INT_ARRAY;
            case CONST_INT:
                return CONST_INT_ARRAY;
            case CHAR:
                return CHAR_ARRAY;
            case CONST_CHAR:
                return CONST_CHAR_ARRAY;
            default:
                return null;
        }
    }

    /**
     * Checks if a type is const.
     *
     * @param type
     *            the type.
     * @return <code>true</code> if the type is const, <code>false</code> otherwise.
     * @since alpha
     */
    public static boolean isConst(VariableType type) {
        switch (type) {
            case CONST_INT:
                return true;
            case CONST_INT_ARRAY:
                return true;
            case CONST_CHAR:
                return true;
            case CONST_CHAR_ARRAY:
                return true;
            default:
                return false;
        }
    }

    /**
     * Checks if the type can be implicitly cast to another type.
     *
     * @param from
     *            original type.
     * @param to
     *            new type.
     * @return <code>true</code> if the type can be implicitly converted to another type, <code>false</code> otherwise.
     * @since alpha
     */
    public static boolean implicitConversion(VariableType from, VariableType to) {
        if (from == INT) {
            if ((to == CONST_INT) | (to == INT)) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (from == CHAR) {
            if ((to == CONST_CHAR) | (to == INT) | (to == CONST_INT) | (to == CHAR)) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (from == CONST_INT) {
            if ((to == INT) | (to == CONST_INT)) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (from == CONST_CHAR) {
            if ((to == CHAR) | (to == CONST_INT) | (to == INT) | (to == CONST_CHAR)) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (from == INT_ARRAY) {
            if ((to == CONST_INT_ARRAY) | (to == INT_ARRAY)) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (from == CONST_INT_ARRAY) {
            if (to == CONST_INT_ARRAY) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (from == CHAR_ARRAY) {
            if ((to == CONST_CHAR_ARRAY) | (to == CHAR_ARRAY)) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (from == CONST_CHAR_ARRAY) {
            if (to == CONST_CHAR_ARRAY) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (from == VOID) {
            if (to == VOID) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    /**
     * Checks if the type can be explicitly cast to another type.
     *
     * @param from
     *            original type.
     * @param to
     *            new type.
     * @return <code>true</code> if the type can be implicitly converted to another type, <code>false</code> otherwise.
     * @since alpha
     */
    public static boolean explicitConversion(VariableType from, VariableType to) {
        switch (from) {
            case INT:
            case CHAR:
            case CONST_INT:
            case CONST_CHAR:
                if (to.equals(INT) || to.equals(CHAR) || to.equals(CONST_INT) || to.equals(CONST_CHAR)) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    /**
     * Casts a type to const.
     *
     * @param original
     *            type to be casted.
     * @return const type.
     * @since alpha
     */
    public static VariableType toConst(VariableType original) {
        switch (original) {
            case INT:
                return CONST_INT;
            case CHAR:
                return CONST_CHAR;
            default:
                return null;
        }
    }

    public static boolean isArrayType(VariableType type) {
        switch (type) {
            case INT_ARRAY:
                return true;
            case CONST_INT_ARRAY:
                return true;
            case CHAR_ARRAY:
                return true;
            case CONST_CHAR_ARRAY:
                return true;
            default:
                return false;
        }
    }

    public static boolean isLExpression(VariableType type) {
        switch (type) {
            case INT:
            case CHAR:
                return true;
            default:
                return false;
        }
    }
}
