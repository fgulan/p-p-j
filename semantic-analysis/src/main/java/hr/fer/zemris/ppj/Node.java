package hr.fer.zemris.ppj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ppj.identifier.table.IdentifierTable;
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

    private final Map<Attribute, Object> attributes = new HashMap<>();

    private final IdentifierTable identifierTable;

    /**
     * Class constructor, specifies the name of the node.
     *
     * @param name
     *            the name.
     * @param parent
     *            the parent.
     * @since 1.0
     */
    public Node(final String name, final Node parent) {
        this(name, new ArrayList<Node>(), parent, new HashMap<Attribute, Object>(), new IdentifierTable(), null);
    }

    /**
     * Class constructor, specifies the name and the children of the node.
     *
     * @param name
     *            the name.
     * @param children
     *            the children.
     * @param parent
     *            parent of the node.
     * @param attributes
     *            attributes of the node.
     * @param identifierTable
     *            identifier table of the node.
     * @param checker
     *            the semantic checker for the node
     * @since 1.0
     */
    public Node(final String name, final List<Node> children, final Node parent,
            final Map<Attribute, Object> attributes, final IdentifierTable identifierTable, final Checker checker) {
        this.name = name;
        this.parent = parent;
        this.children = children;
        this.attributes.putAll(attributes);
        this.identifierTable = identifierTable;
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
     * @return number of children.
     * @since 1.1
     */
    public int childrenCount() {
        return children.size();
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
     * @param index
     *            index of the child.
     * @return child at specified index.
     * @since 1.1
     */
    public Node getChild(final int index) {
        return children.get(index);
    }

    /**
     * @return children of the node.
     * @since 1.1
     */
    public List<Node> getChildren() {
        return Collections.unmodifiableList(children);
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
    public void addAttribute(final Attribute type, final Object value) {
        attributes.put(type, value);
    }

    public void addAttributeRecursive(final Attribute type, final Object value) {
        attributes.put(type, value);

        if (children != null) {
            for (final Node child : children) {
                child.addAttributeRecursive(type, value);
            }
        }
    }

    /**
     * @param type
     *            type of the attribute.
     * @return value of the specified attribute.
     * @since 1.1
     */
    public Object getAttribute(final Attribute type) {
        return attributes.get(type);
    }

    /**
     * @return identifier table of the node.
     * @since 1.1
     */
    public IdentifierTable identifierTable() {
        return identifierTable;
    }

    /**
     * @return parent of the node.
     * @since 1.1
     */
    public Node parent() {
        return parent;
    }

    /**
     * Checks the semantics of the node.
     *
     * @return <code>true</code> if the node is semantically correct, <code>false</code> otherwise.
     * @since 1.1
     */
    public boolean check() {
        // if a checker isn't defined, the node is a terminal \ {ZNAK, BROJ, IDN, NIZ_ZNAKOVA}
        if (checker == null) {
            return true;
        }

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
        result += toString() + "\n";
        for (final Node child : children) {
            result += child.print(depth + 1);
        }
        return result;
    }

    // DEFINIRANO ISKLJUCIVO ZA POTREBE DOJAVE GRESKE
    @Override
    public String toString() {
        if (name.startsWith("<")) {
            return name;
        }

        return name + "(" + attributes.get(Attribute.LINE_NUMBER) + "," + attributes.get(Attribute.VALUE) + ")";
    }
}
