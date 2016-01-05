package hr.fer.zemris.ppj.code;

/**
 * <code>Reg</code> is a set of registers of FRISC processor.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public enum Reg {

    R0("R0"),
    R1("R1"),
    R2("R2"),
    R3("R3"),
    R4("R4"),
    R5("R5"),
    R6("R6"),
    SP("SP"),
    PC("PC"),

    /**
     * Warning! Only 4 bits are used! (ZVCN)
     */
    SR("SR");

    private String name;

    Reg(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
