package hr.fer.zemris.ppj.lr1.parser;

import java.util.List;

import hr.fer.zemris.ppj.Lexeme;

public class Node {
	Lexeme lexeme;
	Node parent;
	List<Node> children;
}
