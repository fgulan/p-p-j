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
    public VariableType fromArrayType(VariableType arrayType) {
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
        	if (to== CONST_INT) return true;
        	else return false;
        }
        else if (from == CHAR) {
        	if (to == CONST_CHAR | to == INT | to == CONST_INT) return true;
        	else return false;
        }
        else if (from == CONST_INT) {
        	if (to== INT) return true;
        	else return false;
        }
        else if (from == CONST_CHAR) {
        	if (to == CHAR | to == CONST_INT | to == INT) return true;
        	else return false;
        }
        else if (from == INT_ARRAY) {
        	if (to == CONST_INT_ARRAY) return true;
        	else return false;
        }
        else if (from == CHAR_ARRAY) {
        	if (to == CONST_CHAR_ARRAY | to == CONST_INT_ARRAY | to == INT_ARRAY) return true;
        	else return false;
        }
        else if (from == CONST_CHAR_ARRAY) {
        	if (to == CONST_INT_ARRAY) return true;
        	else return false;
        }
        return false;
    }

}
