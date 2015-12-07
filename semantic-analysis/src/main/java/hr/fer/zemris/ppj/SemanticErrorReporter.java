package hr.fer.zemris.ppj;

/**
 * <code>SemanticErrorReporter</code>
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class SemanticErrorReporter {

    /**
     * Reports a error on production.
     *
     * @param node
     *            left side of the production.
     * @since alpha
     */
    public static void report(final Node node) {
        String result = node.name() + " ::=";
        for (Node child : node.getChildren()) {
            result += " " + child.toString();
        }

        System.out.println(result);
    }

}
