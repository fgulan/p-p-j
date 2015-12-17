package hr.fer.zemris.ppj;

import java.util.List;

import hr.fer.zemris.ppj.identifier.table.IdentifierTable;
import hr.fer.zemris.ppj.types.Type;
import hr.fer.zemris.ppj.types.functions.FunctionType;

/**
 * Provides utility methods for various operations.
 *
 * @author Domagoj Polancec
 *
 * @since alpha
 *
 */
public class Utils {

    private Utils() {
    }

    /**
     * Naughty nodes go here.
     *
     * @param node
     *            node that was naughty
     * @return always false, never true
     */
    public static boolean badNode(Node node) {
        SemanticErrorReporter.report(node);
        return false;
    }

    /**
     * Handles functions with care.
     *
     * @param table
     *            local scope table (GLOBAL_SCOPE can be passed here as well, we don't judge)
     * @param name
     *            function name (don't pass nulls here)
     * @param arguments
     *            should be resolved peacefully
     * @param returnType
     *            it does what it says on the tin, really
     * @return true if function was added to the scope table or if you like redundancy, false otherwise
     */
    public static boolean handleFunction(IdentifierTable table, String name, List<Type> arguments, Type returnType) {
        FunctionType function = table.localFunction(name);
        if (function != null) {
            if (!function.argumentList().equals(arguments) || !function.returnType().equals(returnType)) {
                return false;
            }
        }
        else {
            return table.declareFunction(name, returnType, arguments);
        }

        return true;
    }
}
