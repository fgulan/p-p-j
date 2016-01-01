package hr.fer.zemris.ppj.interfaces;

import hr.fer.zemris.ppj.Node;

/**
 * <code>Generator</code> is a interface for code generators.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public interface Generator {

    /**
     * Generates the code for the node.
     *
     * @param node
     *            the node.
     */
    void generate(Node node);

}
