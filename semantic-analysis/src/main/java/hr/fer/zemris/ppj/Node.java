package hr.fer.zemris.ppj;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>Node</code> represents a generative tree node.
 *
 * @author Jan Kelemen
 * @author Matea Sabolic
 *
 * @version 1.0
 */
public class Node {

    private final String name;
    private final List<Node> children;

    /**
     * Class constructor, specifies the name of the node.
     *
     * @param name
     *            the name.
     * @since 1.0
     */
    public Node(final String name) {
        this(name, new ArrayList<Node>());
    }

    /**
     * Class constructor, specifies the name and the children of the node.
     *
     * @param name
     *            the name.
     * @param children
     *            the children.
     * @since 1.0
     */
    public Node(final String name, final List<Node> children) {
        this.name = name;
        this.children = children;
    }

    /**
     * Adds a child to the node.
     *
     * @param child
     *            the child to be added.
     * @since 1.0
     */
    public void addChild(final Node child) {
        children.add(child);
    }

    /**
     * Prints the tree.
     *
     * @param depth
     *            starting depth of the node.
     * @return textual representation of the tree.
     * @since 1.0
     */
    public String print(final int depth) {
        String result = "";
        for (int i = 0; i < depth; i++) {
            result += " ";
        }
        result += name + "\n";
        for (final Node child : children) {
            result += child.print(depth + 1);
        }
        return result;
    }
}
