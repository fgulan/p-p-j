package hr.fer.zemris.ppj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>Node</code> represents a generative tree node.
 *
 * @author Jan Kelemen
 * @author Matea Sabolic
 *
 * @version 1.1
 */
public class Node {

    private final String name;
    private final Node parent;
    private final List<Node> children;
    private final Checker checker;

    private final Map<SemanticAttribute, Object> attributes = new HashMap<>();

    /**
     * Class constructor, specifies the name of the node.
     *
     * @param name
     *            the name.
     * @since 1.0
     */
    public Node(final String name, final Node parent) {
        this(name, new ArrayList<Node>(), parent, null);
    }

    /**
     * Class constructor, specifies the name and the children of the node.
     *
     * @param name
     *            the name.
     * @param children
     *            the children.
     * @param checker
     *            the semantic checker for the node
     * @since 1.0
     */
    public Node(final String name, final List<Node> children, final Node parent, final Checker checker) {
        this.name = name;
        this.parent = parent;
        this.children = children;
        this.checker = checker;
    }

    /**
     * @return name of the node.
     * @since 1.1
     */
    public String name() {
        return name;
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
     * Adds a attribute to the node.
     * 
     * @param type
     *            type of the attribute.
     * @param value
     *            value of the attribute.
     * @since 1.1
     */
    public void addAttribute(final SemanticAttribute type, final Object value) {
        attributes.put(type, value);
    }

    /**
     * @param type
     *            type of the attribute.
     * @return value of the specified attribute.
     * @since 1.1
     */
    public Object getAttribute(final SemanticAttribute type) {
        return attributes.get(type);
    }

    /**
     * Checks the semantics of the node.
     *
     * @return <code>true</code> if the node is semantically correct, <code>false</code> otherwise.
     * @since 1.1
     */
    public boolean check() {
        return checker.check(this);
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
