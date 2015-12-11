package hr.fer.zemris.ppj;

/**
 * <code>Attribute</code> attributes for grammar symbols.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public enum Attribute {
    /**
     * tip
     */
    TYPE, // VariableType

    /**
     * tipovi
     */
    TYPES, // List<VariableType>

    /**
     * l-izraz
     */
    L_EXPRESSION, // Boolean

    /**
     * br-elem
     */
    ELEMENT_COUNT, // Integer

    /**
     * uniformnog simbola
     */
    UNIFORM_SYMBOL, // String

    /**
     * vrijednosti intova, charova & stuff
     */
    VALUE, // String

    /**
     * redak leksicke jedinke
     */
    LINE_NUMBER, // Integer

    /**
     * pov
     */
    RETURN_VALUE, // VariableType

    // IDK ovo treba popuniti po potrebi

}
