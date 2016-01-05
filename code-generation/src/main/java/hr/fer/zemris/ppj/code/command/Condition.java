package hr.fer.zemris.ppj.code.command;

/**
 * <code>Condition</code> is a condition for conditional command execution on FRISC processor.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public enum Condition {

    /**
     * C = 1
     */
    CARRY("C"),
    /**
     * C = 0
     */
    NOT_CARRY("NC"),
    /**
     * V = 1
     */
    OVERFLOW("V"),
    /**
     * V = 0
     */
    NOT_OVERFLOW("NV"),
    /**
     * N = 1
     */
    NEGATVE("N"),
    /**
     * N = 0
     */
    NOT_NEGATIVE("NN"),
    /**
     * N = 1
     */
    MINUS("M"),
    /**
     * N = 0
     */
    PLUS("P"),
    /**
     * Z = 1
     */
    ZERO("Z"),
    /**
     * Z = 0
     */
    NOT_ZERO("NZ"),
    /**
     * Z = 1
     */
    EQUAL("EQ"),
    /**
     * Z = 0
     */
    NOT_EQUAL("NE"),
    /**
     * C = 1 || Z = 1
     */
    UNSIGNED_LESS_OR_EQUAL("ULE"),
    /**
     * C = 0 && Z = 0
     */
    UNSIGNED_GREATER_THAN("UGT"),
    /**
     * C = 1
     */
    UNSIGNED_LESS_THAN("ULT"),
    /**
     * C = 0
     */
    UNSIGNED_GREATER_OR_EQUAL("UGE"),
    /**
     * (N ^ V) = 1 || Z = 1
     */
    SIGNED_LESS_OR_EQUAL("ULE"),
    /**
     * (N ^ V) = 0 i Z = 0
     */
    SIGNED_GREATER_THAN("UGT"),
    /**
     * (N ^ V) = 1
     */
    SIGNED_LESS_THAN("ULT"),
    /**
     * (N ^ V) = 0
     */
    SIGNED_GREATER_OF_EQUAL("UGE");

    private String cond;

    Condition(final String cond) {
        this.cond = cond;
    }

    @Override
    public String toString() {
        return cond;
    }
}
