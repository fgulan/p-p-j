package hr.fer.zemris.ppj.code.generator;

import java.util.ArrayList;

import hr.fer.zemris.ppj.code.Reg;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>CallStack</code> is a simulator of the call stack.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class CallStack {

    private static class Tuple {

        private String name;
        private Type type;
        private boolean first;

        public Tuple(String name, Type type, boolean first) {
            this.name = name;
            this.type = type;
            this.first = first;
        }

        public String name() {
            return name;
        }

        public Type type() {
            return type;
        }

        public boolean scopeStart() {
            return first;
        }

        @Override
        public String toString() {
            return name + " " + type + " " + first;
        }
    }

    private static final CommandFactory ch = new CommandFactory();

    private static final ArrayList<Tuple> callStack = new ArrayList<>();
    private static boolean scopeStart = false;

    public static boolean isLocal(String name) {
        for (int i = callStack.size() - 1; i >= 0; i--) {
            if (name.equals(callStack.get(i).name())) {
                return true;
            }
        }

        return false;
    }

    public static void setScopeStart() {
        scopeStart = true;
    }

    public static void clearScope() {
        if (scopeStart) {
            return;
        }

        boolean hasScope = false;
        for (int i = callStack.size() - 1; i >= 0; i--) {
            if (callStack.get(i).scopeStart()) {
                hasScope = true;
                break;
            }
        }

        for (int i = callStack.size() - 1; hasScope && (i >= 0); i--) {
            if (callStack.get(i).scopeStart()) {
                callStack.remove(callStack.size() - 1);
                FRISCGenerator.generateCommand(ch.pop(Reg.R0));
                break;
            }
            callStack.remove(callStack.size() - 1);
            FRISCGenerator.generateCommand(ch.pop(Reg.R0));
        }
    }

    /**
     * Used as a placeholder space on context save.
     *
     * @since alpha
     */
    public static void push() {
        callStack.add(new Tuple(null, null, false));
    }

    public static void push(String name, Type type) {
        callStack.add(new Tuple(name, type, scopeStart));
        scopeStart = false;
    }

    public static void pop() {
        callStack.remove(callStack.size() - 1);
    }

    public static Type at(String name) {
        for (int i = callStack.size() - 1; i >= 0; i--) {
            if (name.equals(callStack.get(i).name())) {
                return callStack.get(i).type();
            }
        }
        return null;
    }

    public static int offset(String name) {
        int depth = 0;
        for (int i = callStack.size() - 1; i >= 0; i--) {
            if (name.equals(callStack.get(i).name())) {
                break;
            }
            depth++;
        }
        return depth * 4;
    }
}
