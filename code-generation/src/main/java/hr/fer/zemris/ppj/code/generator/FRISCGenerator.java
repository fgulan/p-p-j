package hr.fer.zemris.ppj.code.generator;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.BinaryOperation;
import hr.fer.zemris.ppj.code.Reg;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.code.command.Condition;
import hr.fer.zemris.ppj.types.Type;
import hr.fer.zemris.ppj.types.VoidType;
import hr.fer.zemris.ppj.types.functions.FunctionType;

public class FRISCGenerator {

    private static class Pair {

        private String label;
        private String command;

        public Pair(String label, String command) {
            this.label = label;
            this.command = command;
        }

        public String label() {
            return label;
        }

        public String command() {
            return command;
        }
    }

    private static final CommandFactory COMMAND_FACTORY = new CommandFactory();

    private static final List<Pair> program = new ArrayList<>();

    private static final List<Pair> globals = new ArrayList<>();

    private static int ifCounter = 0;
    private static int ifElseCounter = 0;

    private static int loopCounter = 0;

    public static void generateTo(OutputStream outputStream) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);

        program.addAll(globals);

        for (Pair pair : program) {
            System.out.println(pair.label + pair.command);
        }
    }

    public static String generateGlobalLabel(String name) {
        return "G_" + name.toUpperCase();
    }

    public static String generateFunctionLabel(String name) {
        return "F_" + name.toUpperCase();
    }

    public static void generateCommand(String command) {
        generateCommand("", command);
    }

    public static void generateCommand(String label, String command) {
        program.add(new Pair(label, command));
    }

    public static void generatePreamble() {
        generateCommand(COMMAND_FACTORY.baseD());
        generateCommand(COMMAND_FACTORY.moveH(Integer.toString(40000), Reg.SP));
        generateCommand(COMMAND_FACTORY.call("F_MAIN"));
        generateCommand(COMMAND_FACTORY.halt());
    }

    public static void generateEpilogue() {
        // TODO: F_MUL
        // TODO: F_DIV
        // TODO: F_MOD
    }

    public static void defineClobal(String name, String command) {
        globals.add(new Pair(generateGlobalLabel(name), command));
    }

    public static void generateIdentifier(String identifier) {
        if (CallStack.isLocal(identifier)) {
            Type type = CallStack.at(identifier);
            if (type == null) {
                generateCommand(COMMAND_FACTORY.move("F_" + identifier.toUpperCase(), Reg.R0));
                generateCommand(COMMAND_FACTORY.push(Reg.R0));
            } else {
                int offset = CallStack.offset(identifier);
                generateCommand(COMMAND_FACTORY.load(Reg.R0, Reg.SP, Integer.toHexString(offset)));
                generateCommand(COMMAND_FACTORY.push(Reg.R0));
            }
        } else {
            String label = generateGlobalLabel(identifier);
            generateCommand(COMMAND_FACTORY.load(Reg.R0, label));
            generateCommand(COMMAND_FACTORY.push(Reg.R0));
        }
    }

    public static void generateNumber(int value) {
        if (isBigInteger(value)) {
            String label = "BIG_INT_" + globals.size();
            globals.add(new Pair(label, COMMAND_FACTORY.dw(value)));

            generateCommand(COMMAND_FACTORY.load(Reg.R0, label));
            generateCommand(COMMAND_FACTORY.push(Reg.R0));
        } else {
            generateCommand(COMMAND_FACTORY.move(value, Reg.R0));
            generateCommand(COMMAND_FACTORY.push(Reg.R0));
        }
    }

    public static void generateFunctionCall(String name, FunctionType type) {
        generateCommand(COMMAND_FACTORY.call("F_" + name.toUpperCase()));
        if (!(type.returnType() instanceof VoidType)) {
            generateCommand(COMMAND_FACTORY.push(Reg.R6));
            CallStack.push();
        }
    }

    public static void contextSave() {
        generateCommand(COMMAND_FACTORY.push(Reg.R0));
        CallStack.push();
        generateCommand(COMMAND_FACTORY.push(Reg.R1));
        CallStack.push();
        generateCommand(COMMAND_FACTORY.push(Reg.R2));
        CallStack.push();
        generateCommand(COMMAND_FACTORY.push(Reg.R3));
        CallStack.push();
        generateCommand(COMMAND_FACTORY.push(Reg.R4));
        CallStack.push();
        generateCommand(COMMAND_FACTORY.push(Reg.R5));
        CallStack.push();
    }

    public static void contextLoad() {
        generateCommand(COMMAND_FACTORY.pop(Reg.R5));
        generateCommand(COMMAND_FACTORY.pop(Reg.R4));
        generateCommand(COMMAND_FACTORY.pop(Reg.R3));
        generateCommand(COMMAND_FACTORY.pop(Reg.R2));
        generateCommand(COMMAND_FACTORY.pop(Reg.R1));
        generateCommand(COMMAND_FACTORY.pop(Reg.R0));
    }

    public static void generateUnaryNegate() {
        generateCommand(COMMAND_FACTORY.pop(Reg.R0));
        generateCommand(COMMAND_FACTORY.move(0, Reg.R1));
        generateCommand(COMMAND_FACTORY.sub(Reg.R1, Reg.R0, Reg.R0));
        generateCommand(COMMAND_FACTORY.push(Reg.R0));
    }

    public static void generateBinaryOperation(BinaryOperation operation) {
        generateCommand(COMMAND_FACTORY.pop(Reg.R1));
        generateCommand(COMMAND_FACTORY.pop(Reg.R0));
        switch (operation) {
        case MUL:
            // check if either number is 0
            generateCommand(COMMAND_FACTORY.cmp(Reg.R0, 0));
            generateCommand(COMMAND_FACTORY.jp("MUL_ZERO", Condition.EQUAL));
            
            // check if either number is 0
            generateCommand(COMMAND_FACTORY.cmp(Reg.R1, 0));
            generateCommand(COMMAND_FACTORY.jp("MUL_ZERO", Condition.EQUAL));
            
            // determine sign
            generateCommand(COMMAND_FACTORY.rotl(Reg.R0, 1, Reg.R4));
            generateCommand(COMMAND_FACTORY.and(Reg.R4, 1, Reg.R4));
            generateCommand(COMMAND_FACTORY.rotl(Reg.R1, 1, Reg.R5));
            generateCommand(COMMAND_FACTORY.and(Reg.R5, 1, Reg.R5));
            generateCommand(COMMAND_FACTORY.xor(Reg.R4, Reg.R5, Reg.R4));
            // the sign is now in R4
            
            // remove signs from original numbers
            generateCommand(COMMAND_FACTORY.shl(Reg.R0, 1, Reg.R0));
            generateCommand(COMMAND_FACTORY.shr(Reg.R0, 1, Reg.R0));
            generateCommand(COMMAND_FACTORY.shl(Reg.R1, 1, Reg.R1));
            generateCommand(COMMAND_FACTORY.shr(Reg.R1, 1, Reg.R1));
            
            // consecutively add numbers
            generateCommand("MUL_START", "");
            generateCommand(COMMAND_FACTORY.cmp(Reg.R1, 1));
            generateCommand(COMMAND_FACTORY.jp("APPLY_SIGN", Condition.EQUAL));
            generateCommand(COMMAND_FACTORY.add(Reg.R0, Reg.R0, Reg.R0));
            generateCommand(COMMAND_FACTORY.sub(Reg.R1, 1, Reg.R1));
            generateCommand(COMMAND_FACTORY.jp("MUL_START"));
            
            generateCommand("APPLY_SIGN", "");
            generateCommand(COMMAND_FACTORY.cmp(Reg.R4, 0));
            generateCommand(COMMAND_FACTORY.jp("MUL_END", Condition.EQUAL));
            generateCommand(COMMAND_FACTORY.move(0, Reg.R1));
            generateCommand(COMMAND_FACTORY.sub(Reg.R1, Reg.R0, Reg.R0));
            generateCommand(COMMAND_FACTORY.jp("MUL_END"));
            
            // deal with mul with zero
            generateCommand("MUL_ZERO", "");
            generateCommand(COMMAND_FACTORY.move(0, Reg.R0));
            
            // end mul
            generateCommand("MUL_END", "");
            
            break;
        case DIV:
            
            // determine sign
            generateCommand(COMMAND_FACTORY.rotl(Reg.R0, 1, Reg.R4));
            generateCommand(COMMAND_FACTORY.and(Reg.R4, 1, Reg.R4));
            generateCommand(COMMAND_FACTORY.rotl(Reg.R1, 1, Reg.R5));
            generateCommand(COMMAND_FACTORY.and(Reg.R5, 1, Reg.R5));
            generateCommand(COMMAND_FACTORY.xor(Reg.R4, Reg.R5, Reg.R4));
            generateCommand(COMMAND_FACTORY.rotr(Reg.R4, 1, Reg.R4));
            // the sign is now in R4
            
            // remove signs from original numbers
            generateCommand(COMMAND_FACTORY.shl(Reg.R0, 1, Reg.R0));
            generateCommand(COMMAND_FACTORY.shr(Reg.R0, 1, Reg.R0));
            generateCommand(COMMAND_FACTORY.shl(Reg.R1, 1, Reg.R1));
            generateCommand(COMMAND_FACTORY.shr(Reg.R1, 1, Reg.R1));
            
            generateCommand(COMMAND_FACTORY.move(0, Reg.R5));
            
            // consecutively subtract numbers
            generateCommand("DIV_START", "");
            generateCommand(COMMAND_FACTORY.cmp(Reg.R0, Reg.R1));
            generateCommand(COMMAND_FACTORY.jp("APPLY_SIGN", Condition.UNSIGNED_LESS_THAN));
            generateCommand(COMMAND_FACTORY.sub(Reg.R0, Reg.R1, Reg.R0));
            generateCommand(COMMAND_FACTORY.add(Reg.R5, 1, Reg.R5));
            generateCommand(COMMAND_FACTORY.jp("DIV_START"));
            
           
            
            generateCommand("APPLY_SIGN", "");
            generateCommand(COMMAND_FACTORY.move(Reg.R5, Reg.R0));
            generateCommand(COMMAND_FACTORY.cmp(Reg.R4, 0));
            generateCommand(COMMAND_FACTORY.jp("DIV_END", Condition.EQUAL));
            generateCommand(COMMAND_FACTORY.move(0, Reg.R1));
            generateCommand(COMMAND_FACTORY.sub(Reg.R1, Reg.R0, Reg.R0));
            
            // end div
            generateCommand("DIV_END", "");
            break;
        case MOD:
            // determine signs
            generateCommand(COMMAND_FACTORY.rotl(Reg.R0, 1, Reg.R4));
            generateCommand(COMMAND_FACTORY.and(Reg.R4, 1, Reg.R4));
            generateCommand(COMMAND_FACTORY.rotl(Reg.R1, 1, Reg.R5));
            generateCommand(COMMAND_FACTORY.and(Reg.R5, 1, Reg.R5));
            generateCommand(COMMAND_FACTORY.xor(Reg.R4, Reg.R5, Reg.R4));
            // the sign for second is in R5, the sign that determines if the result needs to be inverted is in R4
            
            // remove signs from original numbers
            generateCommand(COMMAND_FACTORY.shl(Reg.R0, 1, Reg.R0));
            generateCommand(COMMAND_FACTORY.shr(Reg.R0, 1, Reg.R0));
            generateCommand(COMMAND_FACTORY.shl(Reg.R1, 1, Reg.R1));
            generateCommand(COMMAND_FACTORY.shr(Reg.R1, 1, Reg.R1));
            
            generateCommand(COMMAND_FACTORY.move(Reg.R0, Reg.R3));
            // consecutively subtract numbers
            generateCommand("MOD_START", "");
            generateCommand(COMMAND_FACTORY.cmp(Reg.R0, Reg.R1));
            generateCommand(COMMAND_FACTORY.jp("INV_CHK", Condition.UNSIGNED_LESS_THAN));
            generateCommand(COMMAND_FACTORY.sub(Reg.R0, Reg.R1, Reg.R0));
            generateCommand(COMMAND_FACTORY.jp("MOD_START"));
            
            // inversion check
            generateCommand("INV_CHK", "");
            
            // determine if inversion is necessary
            generateCommand(COMMAND_FACTORY.cmp(Reg.R4, 0));
            generateCommand(COMMAND_FACTORY.jp("APPLY_SIGN", Condition.EQUAL));
            
            // invert
            generateCommand(COMMAND_FACTORY.sub(Reg.R3, Reg.R0, Reg.R0));
            
            generateCommand("APPLY_SIGN", "");
            generateCommand(COMMAND_FACTORY.cmp(Reg.R5, 0));
            generateCommand(COMMAND_FACTORY.jp("MOD_END", Condition.EQUAL));
            generateCommand(COMMAND_FACTORY.move(0, Reg.R1));
            generateCommand(COMMAND_FACTORY.sub(Reg.R1, Reg.R0, Reg.R0));
            
            // mod end
            generateCommand("MOD_END", "");
            break;
        case ADD:
            generateCommand(COMMAND_FACTORY.add(Reg.R0, Reg.R1, Reg.R0));
            break;
        case SUB:
            generateCommand(COMMAND_FACTORY.sub(Reg.R0, Reg.R1, Reg.R0));
            break;
        case OR:
            generateCommand(COMMAND_FACTORY.or(Reg.R0, Reg.R1, Reg.R0));
            break;
        case AND:
            generateCommand(COMMAND_FACTORY.and(Reg.R0, Reg.R1, Reg.R0));
            break;
        case XOR:
            generateCommand(COMMAND_FACTORY.xor(Reg.R0, Reg.R1, Reg.R0));
            break;
        case LT:
            generateCommand(COMMAND_FACTORY.cmp(Reg.R0, Reg.R1));
            setFlagsLessThan();
            break;
        case GT:
            generateCommand(COMMAND_FACTORY.cmp(Reg.R1, Reg.R0));
            setFlagsLessThan();
            break;
        case LE:
            generateCommand(COMMAND_FACTORY.cmp(Reg.R1, Reg.R0));
            setFlagsLessThan();
            generateCommand(COMMAND_FACTORY.xor(Reg.R0, 1, Reg.R0));
            break;
        case GE:
            generateCommand(COMMAND_FACTORY.cmp(Reg.R0, Reg.R1));
            setFlagsLessThan();
            generateCommand(COMMAND_FACTORY.xor(Reg.R0, 1, Reg.R0));
            break;
        case EQ:
            generateCommand(COMMAND_FACTORY.cmp(Reg.R0, Reg.R1));
            setFlagsEqual();
            break;
        case NE:
            generateCommand(COMMAND_FACTORY.cmp(Reg.R0, Reg.R1));
            setFlagsEqual();
            generateCommand(COMMAND_FACTORY.xor(Reg.R0, 1, Reg.R0));
            break;
        default:
            break;
        }
        generateCommand(COMMAND_FACTORY.push(Reg.R0));
    }

    private static void setFlagsEqual() {
        generateCommand(COMMAND_FACTORY.move(Reg.SR, Reg.R0));
        generateCommand(COMMAND_FACTORY.shr(Reg.R0, 3, Reg.R0));
        generateCommand(COMMAND_FACTORY.and(Reg.R0, 1, Reg.R0));
    }

    private static void setFlagsLessThan() {
        generateCommand(COMMAND_FACTORY.move(Reg.SR, Reg.R0));
        generateCommand(COMMAND_FACTORY.shr(Reg.R0, 2, Reg.R1));
        generateCommand(COMMAND_FACTORY.xor(Reg.R0, Reg.R1, Reg.R0));
        generateCommand(COMMAND_FACTORY.and(Reg.R0, 1, Reg.R0));
    }

    private static boolean isBigInteger(int value) {
        int upper12 = (value & 0xfff00000);
        boolean signumBit = (value & 0x00010000) != 0;
        int bitCount = Integer.bitCount(upper12);
        return !(((bitCount == 12) && signumBit) || ((bitCount == 0) && !signumBit));
    }

    public static void generateAssignmentOperation() {
        generateCommand(COMMAND_FACTORY.pop(Reg.R0));
        generateCommand(COMMAND_FACTORY.pop(Reg.R1));
        generateCommand(COMMAND_FACTORY.push(Reg.R0));
    }

    public static void generateStartIfIntstruction() {
        String label = "IF_START_" + ifCounter;
        generateCommand(label, COMMAND_FACTORY.pop(Reg.R0));
        generateCommand(COMMAND_FACTORY.cmp(Reg.R0, 0));
        generateCommand(COMMAND_FACTORY.jp("IF_END_" + ifCounter, Condition.EQUAL));
        ifCounter++;
    }

    public static void generateEndIfIntstruction() {
        ifCounter--;
        String label = "IF_END_" + ifCounter;
        generateCommand(label, "");
    }

    public static void generateStartLoopIntstruction() {
        loopCounter++;
        String label = "LO_START_" + loopCounter;
        generateCommand(label, "");
    }

    public static void generateLoopCheckInstruction() {
        generateCommand(COMMAND_FACTORY.pop(Reg.R0));
        generateCommand(COMMAND_FACTORY.cmp(Reg.R0, 0));
        generateCommand(COMMAND_FACTORY.jp("LO_END_" + loopCounter, Condition.EQUAL));
    }

    public static void generateLoopJumpToStartInstruction() {
        generateCommand(COMMAND_FACTORY.jp("LO_START_" + loopCounter));
    }

    public static void generateLoopJumpToEndInstruction() {
        generateCommand(COMMAND_FACTORY.jp("LO_END_" + loopCounter));
    }

    public static void generateEndLoopIntstruction() {
        String label = "LO_END_" + loopCounter;
        generateCommand(label, "");
        loopCounter--;
    }

    public static void generateStartIfElseIntstruction() {
        String label = "IF_ELSE_START_" + ifElseCounter;
        generateCommand(label, COMMAND_FACTORY.pop(Reg.R0));
        generateCommand(COMMAND_FACTORY.cmp(Reg.R0, 0));
        generateCommand(COMMAND_FACTORY.jp("ELSE_START_" + ifElseCounter, Condition.EQUAL));
        ifElseCounter++;
    }

    public static void generateElseIntstruction() {
        int count = ifElseCounter - 1;
        String label = "IF_ELSE_END_" + count;
        generateCommand(label, COMMAND_FACTORY.jp("ELSE_END_" + count));
        generateCommand("ELSE_START_" + count, "");
    }

    public static void generateEndIfElseIntstruction() {
        ifElseCounter--;
        String label = "ELSE_END_" + ifElseCounter;
        generateCommand(label, "");
    }

    public static void generatePostIncrement() {
        generateCommand(COMMAND_FACTORY.pop(Reg.R0));
        generateCommand(COMMAND_FACTORY.load(Reg.R1, Reg.R0));
        generateCommand(COMMAND_FACTORY.push(Reg.R1));
        generateCommand(COMMAND_FACTORY.add(Reg.R1, 1, Reg.R1));
        generateCommand(COMMAND_FACTORY.store(Reg.R1, Reg.R0));
    }

    public static void generatePostDecrement() {
        generateCommand(COMMAND_FACTORY.pop(Reg.R0));
        generateCommand(COMMAND_FACTORY.load(Reg.R1, Reg.R0));
        generateCommand(COMMAND_FACTORY.push(Reg.R1));
        generateCommand(COMMAND_FACTORY.sub(Reg.R1, 1, Reg.R1));
        generateCommand(COMMAND_FACTORY.store(Reg.R1, Reg.R0));
    }

    public static void generatePreIncrement() {
        generateCommand(COMMAND_FACTORY.pop(Reg.R0));
        generateCommand(COMMAND_FACTORY.load(Reg.R1, Reg.R0));
        generateCommand(COMMAND_FACTORY.add(Reg.R1, 1, Reg.R1));
        generateCommand(COMMAND_FACTORY.store(Reg.R1, Reg.R0));
        generateCommand(COMMAND_FACTORY.push(Reg.R1));
    }

    public static void generatePreDecrement() {
        generateCommand(COMMAND_FACTORY.pop(Reg.R0));
        generateCommand(COMMAND_FACTORY.load(Reg.R1, Reg.R0));
        generateCommand(COMMAND_FACTORY.sub(Reg.R1, 1, Reg.R1));
        generateCommand(COMMAND_FACTORY.store(Reg.R1, Reg.R0));
        generateCommand(COMMAND_FACTORY.push(Reg.R1));
    }

    public static void generateLogicalNot() {
        generateCommand(COMMAND_FACTORY.pop(Reg.R0));
        generateCommand(COMMAND_FACTORY.and(Reg.R0, Reg.R0, Reg.R0));
        setFlagsEqual();
        generateCommand(COMMAND_FACTORY.xor(Reg.R0, 1, Reg.R0));
        generateCommand(COMMAND_FACTORY.push(Reg.R0));
    }

    public static void generateBitwiseNot() {
        generateCommand(COMMAND_FACTORY.pop(Reg.R0));
        generateCommand(COMMAND_FACTORY.xor(Reg.R0, -1, Reg.R0));
    }
    
    public static void generateInc(int increment) {
        generateCommand(COMMAND_FACTORY.pop(Reg.R0));
        generateCommand(COMMAND_FACTORY.move(increment, Reg.R1));
        generateCommand(COMMAND_FACTORY.add(Reg.R0, Reg.R1, Reg.R0));
        generateCommand(COMMAND_FACTORY.push(Reg.R0));
    }
}
