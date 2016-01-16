package hr.fer.zemris.ppj.code.generator;

import java.util.ArrayList;

import hr.fer.zemris.ppj.identifier.table.IdentifierTypeWrapper;

/**
 * <code>CallStack</code> is a simulator of the call stack.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class CallStack {

    private static class Pair {

        private String name;
        private IdentifierTypeWrapper wrapper;

        public Pair(String name, IdentifierTypeWrapper wrapper) {
            this.name = name;
            this.wrapper = wrapper;
        }

        public String name() {
            return name;
        }

        public IdentifierTypeWrapper wrapper() {
            return wrapper;
        }
    }

    private static final ArrayList<IdentifierTypeWrapper> localCallStack = new ArrayList<>();

    public static boolean isLocal(String name) {
        for (int i = localCallStack.size() - 1; i >= 0; i--) {
            if (localCallStack.get(i).name().equals(name)) {
                return true;
            }
        }

        return false;
    }

}
