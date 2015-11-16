package hr.fer.zemris.ppj.lr1.parser;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private String name;
    private List<Node> children;

    public Node(final String name) {
        this(name, new ArrayList<Node>());
    }

    public Node(final String name, final List<Node> children) {
        this.name = name;
        this.children = children;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public String print(int depth) {
        String result = "";
        for (int i = 0; i < depth; i++) {
            result += " ";
        }
        result += name + "\n";
        for (Node child : children) {
            result += child.print(depth + 1);
        }
        return result;
    }
}
