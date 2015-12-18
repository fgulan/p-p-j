package hr.fer.zemris.ppj;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>SemanticErrorReporter</code>
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class SemanticErrorReporter {

    private static final List<Node> reportedNodes = new ArrayList<>();
    private static final List<String> reports = new ArrayList<>();

    /**
     * Used to report a single semantic error during parsing.
     *
     * @param node
     *            node with the error.
     * @since 1.0
     */
    public static void report(final Node node) {
        reportedNodes.add(node);
        report(generateReport(node));
    }

    /**
     * Used to report a single semantic error during parsing.
     * 
     * @param report
     * @since 1.0
     */
    public static void report(final String report) {
        reports.add(report);
    }

    /**
     * Generates a final report (only the first semantic error).
     *
     * @since 1.0
     */
    public static void finalReport() {
        if (!reports.isEmpty()) {
            System.out.println(reports.get(0));
        }
    }

    private static String generateReport(final Node node) {
        String result = node.name() + " ::=";
        for (final Node child : node.getChildren()) {
            result += " " + child.toString();
        }
        return result;
    }

}
