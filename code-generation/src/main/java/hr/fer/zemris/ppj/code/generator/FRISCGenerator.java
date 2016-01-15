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

    public static void generateTo(OutputStream outputStream) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);

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
        generateCommand(COMMAND_FACTORY.move(40000, Reg.SP));
        generateCommand(COMMAND_FACTORY.call("F_MAIN"));
        generateCommand(COMMAND_FACTORY.halt());
    }

    public static void generateEpilogue() {
        // TODO: F_MUL
        // TODO: F_DIV
        // TODO: F_MOD
        // TODO: Globals
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
}
