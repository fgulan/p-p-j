package hr.fer.zemris.ppj;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>SemanticErrorReporter</code>
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class SemanticErrorReporter {

    private static final List<Node> reportedNodes = new ArrayList<>();
    private static final List<String> reports = new ArrayList<>();

    /**
     * Reports a error on production.
     *
     * @param node
     *            left side of the production.
     * @since alpha
     */
    public static void report(final Node node) {
        reportedNodes.add(node);
        report(generateReport(node));
    }

    public static void report(final String report) {
        reports.add(report);
    }

    /**
     * Generates a final report (only the first semantic error).
     *
     * @since alpha
     */
    public static void finalReport() {
        if (!reports.isEmpty()) {
            System.out.println(reports.get(0));

            if (!reportedNodes.isEmpty()) {
                System.out.println(reportedNodes.get(0).print(0));
            }
        }
    }

    private static String generateReport(final Node node) {
        String result = node.name() + " ::=";
        for (Node child : node.getChildren()) {
            result += " " + child.toString();
        }
        return result;
    }

}
