package hr.fer.zemris.ppj.semantic.rule;

import hr.fer.zemris.ppj.Node;

/**
 * <code>Checker</code> is a interface for semantic analyisis checkers.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public interface Checker {

    /**
     * Checks semantic rules of the node.
     *
     * @param node
     *            the node.
     * @return true if the node passes the semantic check.
     */
    boolean check(Node node);
    void generate(Node node);
}
