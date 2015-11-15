package hr.fer.zemris.ppj.lr1.parser;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private String name;
    private List<Node> children;

    public Node(final String name, final List<Node> children) {
        this.name = name;
        this.children = children == null ? new ArrayList<>() : children;
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

    @Override
    public String toString() {
        String result = print(0);
        return result.substring(0, result.length() - 1);
    }
}
