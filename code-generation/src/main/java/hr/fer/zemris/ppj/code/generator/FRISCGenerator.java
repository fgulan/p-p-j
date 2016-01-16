package hr.fer.zemris.ppj.code.generator;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.code.Reg;
import hr.fer.zemris.ppj.code.command.CommandFactory;

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

    private static String currentFunction = "";

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

    public static void setCurrentFunction(String functionName) {
        currentFunction = functionName;
    }

    public static void generateIdentificator(String identifier) {
        if (CallStack.isLocal(identifier)) {
            generateCommand(COMMAND_FACTORY.move("F_" + identifier.toUpperCase(), Reg.R0));
            generateCommand(COMMAND_FACTORY.push(Reg.R0));
        }
        else {
            generateCommand(COMMAND_FACTORY.load(Reg.R0, generateGlobalLabel(identifier)));
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

    public static void generateFunctionCall(String name) {
        generateCommand(COMMAND_FACTORY.call(Reg.R0));
    }

    public static void contextSave() {
        generateCommand(COMMAND_FACTORY.push(Reg.R0));
        generateCommand(COMMAND_FACTORY.push(Reg.R1));
        generateCommand(COMMAND_FACTORY.push(Reg.R2));
        generateCommand(COMMAND_FACTORY.push(Reg.R3));
        generateCommand(COMMAND_FACTORY.push(Reg.R4));
        generateCommand(COMMAND_FACTORY.push(Reg.R5));
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
    
    private static boolean isBigInteger(int value) {
        int upper12 = (value & 0xfff00000);
        boolean signumBit = (value & 0x00010000) != 0;
        int bitCount = Integer.bitCount(upper12);
        return !(bitCount == 12 && signumBit || bitCount == 0 && !signumBit);
    }
}
