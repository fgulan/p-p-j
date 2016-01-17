package hr.fer.zemris.ppj.code.command;

import hr.fer.zemris.ppj.code.Reg;

/**
 * <code>CommandFactory</code> is a factory for FRISC commands.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
@SuppressWarnings("javadoc")
public class CommandFactory {

    public String add(Reg src1, Reg src2, Reg dest) {
        return "\tADD " + src1 + ", " + src2 + ", " + dest;
    }

    public String add(Reg src1, int src2, Reg dest) {
        return "\tADD " + src1 + ", %D " + src2 + ", " + dest;
    }

    public String add(Reg src1, String label, Reg dest) {
        return "\tADD " + src1 + ", " + label + ", " + dest;
    }

    public String adc(Reg src1, Reg src2, Reg dest) {
        return "\tADC " + src1 + ", " + src2 + ", " + dest;
    }

    public String adc(Reg src1, int src2, Reg dest) {
        return "\tADC " + src1 + ", %D " + src2 + ", " + dest;
    }

    public String adc(Reg src1, String label, Reg dest) {
        return "\tADC " + src1 + ", " + label + ", " + dest;
    }

    public String sub(Reg src1, Reg src2, Reg dest) {
        return "\tSUB " + src1 + ", " + src2 + ", " + dest;
    }

    public String sub(Reg src1, int src2, Reg dest) {
        return "\tSUB " + src1 + ", %D " + src2 + ", " + dest;
    }

    public String sub(Reg src1, String label, Reg dest) {
        return "\tSUB " + src1 + ", " + label + ", " + dest;
    }

    public String sbc(Reg src1, Reg src2, Reg dest) {
        return "\tSBC " + src1 + ", " + src2 + ", " + dest;
    }

    public String sbc(Reg src1, int src2, Reg dest) {
        return "\tSBC " + src1 + ", %D " + src2 + ", " + dest;
    }

    public String sbc(Reg src1, String label, Reg dest) {
        return "\tSBC " + src1 + ", " + label + ", " + dest;
    }

    public String cmp(Reg src1, Reg src2) {
        return "\tCMP " + src1 + ", " + src2;
    }

    public String cmp(Reg src1, int src2) {
        return "\tCMP " + src1 + ", %D " + src2;
    }

    public String cmp(Reg src1, String label) {
        return "\tCMP " + src1 + ", " + label;
    }

    public String and(Reg src1, Reg src2, Reg dest) {
        return "\tAND " + src1 + ", " + src2 + ", " + dest;
    }

    public String and(Reg src1, int src2, Reg dest) {
        return "\tAND " + src1 + ", %D " + src2 + ", " + dest;
    }

    public String and(Reg src1, String label, Reg dest) {
        return "\tAND " + src1 + ", " + label + ", " + dest;
    }

    public String or(Reg src1, Reg src2, Reg dest) {
        return "\tOR " + src1 + ", " + src2 + ", " + dest;
    }

    public String or(Reg src1, int src2, Reg dest) {
        return "\tOR " + src1 + ", %D " + src2 + ", " + dest;
    }

    public String or(Reg src1, String label, Reg dest) {
        return "\tOR " + src1 + ", " + label + ", " + dest;
    }

    public String xor(Reg src1, Reg src2, Reg dest) {
        return "\tXOR " + src1 + ", " + src2 + ", " + dest;
    }

    public String xor(Reg src1, int src2, Reg dest) {
        return "\tXOR " + src1 + ", %D " + src2 + ", " + dest;
    }

    public String xor(Reg src1, String label, Reg dest) {
        return "\tXOR " + src1 + ", " + label + ", " + dest;
    }

    public String shl(Reg src1, Reg src2, Reg dest) {
        return "\tSHL " + src1 + ", " + src2 + ", " + dest;
    }

    public String shl(Reg src1, int src2, Reg dest) {
        return "\tSHL " + src1 + ", %D " + src2 + ", " + dest;
    }

    public String shl(Reg src1, String label, Reg dest) {
        return "\tSHL " + src1 + ", " + label + ", " + dest;
    }

    public String shr(Reg src1, Reg src2, Reg dest) {
        return "\tSHR " + src1 + ", " + src2 + ", " + dest;
    }

    public String shr(Reg src1, int src2, Reg dest) {
        return "\tSHR " + src1 + ", %D " + src2 + ", " + dest;
    }

    public String shr(Reg src1, String label, Reg dest) {
        return "\tSHR " + src1 + ", " + label + ", " + dest;
    }

    public String ashr(Reg src1, Reg src2, Reg dest) {
        return "\tASHR " + src1 + ", " + src2 + ", " + dest;
    }

    public String ashr(Reg src1, int src2, Reg dest) {
        return "\tASHR " + src1 + ", %D " + src2 + ", " + dest;
    }

    public String ashr(Reg src1, String label, Reg dest) {
        return "\tASHR " + src1 + ", " + label + ", " + dest;
    }

    public String rotl(Reg src1, Reg src2, Reg dest) {
        return "\tROTL " + src1 + ", " + src2 + ", " + dest;
    }

    public String rotl(Reg src1, int src2, Reg dest) {
        return "\tROTL " + src1 + ", %D " + src2 + ", " + dest;
    }

    public String rotl(Reg src1, String label, Reg dest) {
        return "\tROTL " + src1 + ", " + label + ", " + dest;
    }

    public String rotr(Reg src1, Reg src2, Reg dest) {
        return "\tROTR " + src1 + ", " + src2 + ", " + dest;
    }

    public String rotr(Reg src1, int src2, Reg dest) {
        return "\tROTR " + src1 + ", %D " + src2 + ", " + dest;
    }

    public String rotr(Reg src1, String label, Reg dest) {
        return "\tROTR " + src1 + ", " + label + ", " + dest;
    }

    public String move(Reg src1, Reg dest) {
        return "\tMOVE " + src1 + ", " + dest;
    }

    public String move(int src1, Reg dest) {
        return "\tMOVE " + src1 + ", " + dest;
    }

    public String moveH(String src1, Reg dest) {
        return "\tMOVE %H " + src1 + ", " + dest;
    }

    public String move(String label, Reg dest) {
        return "\tMOVE " + label + ", " + dest;
    }

    public String load(Reg dest, int adr20) {
        return load(dest, adr20, Size.WORD);
    }

    public String load(Reg dest, int adr20, Size size) {
        return load(dest, String.valueOf(adr20), size);
    }

    public String load(Reg dest, String label) {
        return load(dest, label, Size.WORD);
    }
    
    public String loadDW(Reg dest, String label) {
        return "\tLOAD " + dest + ", " + label;
    }

    public String load(Reg dest, String label, Size size) {
        return "\tLOAD" + size + " " + dest + ", (" + label + ")";
    }

    public String load(Reg dest, Reg adrreg) {
        return load(dest, adrreg, "0", Size.WORD);
    }

    public String load(Reg dest, Reg adrreg, Size size) {
        return load(dest, adrreg, "0", size);
    }

    public String load(Reg dest, Reg adrreg, String hexOffset) {
        return load(dest, adrreg, hexOffset, Size.WORD);
    }

    public String load(Reg dest, Reg adrreg, String hexOffset, Size size) {
        return "\tLOAD" + size + " " + dest + ", (" + adrreg + " + " + hexOffset + ")";
    }

    public String store(Reg dest, int adr20) {
        return store(dest, adr20, Size.WORD);
    }

    public String store(Reg dest, int adr20, Size size) {
        return store(dest, String.valueOf(adr20), size);
    }

    public String store(Reg dest, String label) {
        return store(dest, label, Size.WORD);
    }

    public String store(Reg dest, String label, Size size) {
        return "\tSTORE" + size + " " + dest + ", (" + label + ")";
    }

    public String store(Reg dest, Reg adrreg) {
        return store(dest, adrreg, "0", Size.WORD);
    }

    public String store(Reg dest, Reg adrreg, Size size) {
        return store(dest, adrreg, "0", size);
    }

    public String store(Reg dest, Reg adrreg, String hexOffset) {
        return store(dest, adrreg, hexOffset, Size.WORD);
    }

    public String store(Reg dest, Reg adrreg, String hexOffset, Size size) {
        return "\tSTORE" + size + " " + dest + ", (" + adrreg + " + " + hexOffset + ")";
    }

    public String push(Reg src1) {
        return "\tPUSH " + src1;
    }

    public String pop(Reg dest) {
        return "\tPOP " + dest;
    }

    public String jp(int adr20) {
        return "\tJP %D " + adr20;
    }

    public String jp(int adr20, Condition condition) {
        return "\tJP_" + condition + " %D " + adr20;
    }

    public String jp(String label) {
        return "\tJP " + label;
    }

    public String jp(String label, Condition condition) {
        return "\tJP_" + condition + " " + label;
    }

    public String jp(Reg adrreg) {
        return "\tJP (" + adrreg + ")";
    }

    public String jp(Reg adrreg, Condition condition) {
        return "\tJP_" + condition + " (" + adrreg + ")";
    }

    public String jr(int adr) {
        return "\tJR %D" + adr;
    }

    public String jr(int adr, Condition condition) {
        return "\tJR_" + condition + " %D" + adr;
    }

    public String jr(String label) {
        return "\tJR " + label;
    }

    public String jr(String label, Condition condition) {
        return "\tJR_" + condition + " " + label;
    }

    public String call(int adr20) {
        return "\tCALL %D " + adr20;
    }

    public String call(int adr20, Condition condition) {
        return "\tCALL_" + condition + " %D " + adr20;
    }

    public String call(Reg adrreg) {
        return "\tCALL (" + adrreg + ")";
    }

    public String call(Reg adrreg, Condition condition) {
        return "\tCALL_" + condition + " (" + adrreg + ")";
    }

    public String call(String label) {
        return "\tCALL " + label;
    }

    public String call(String label, Condition condition) {
        return "\tCALL_" + condition + " " + label;
    }

    public String ret() {
        return "\tRET";
    }

    public String ret(Condition condition) {
        return "\tRET_" + condition;
    }

    public String halt() {
        return "\tHALT";
    }

    public String halt(Condition condition) {
        return "\tHALT_" + condition;
    }

    public String baseD() {
        return "\t`BASE D ";
    }

    public String ds(int size) {
        return "\t`DS %D " + size;
    }

    public String db(int value) {
        return "\tDB %D " + value;
    }

    public String dw(int value) {
        return "\tDW %D " + value;
    }

}
