package hr.fer.zemris.ppj.code.command;

/**
 * <code>Size</code> is a specifer for memory commands.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public enum Size {

    /**
     * 8 bits.
     */
    BYTE("B"),
    /**
     * 16 bits.
     */
    HALF_WORD("H"),

    /**
     * 32 bits.
     */
    WORD("");

    private String symbol;

    Size(final String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
