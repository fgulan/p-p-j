package hr.fer.zemris.ppj.semantic.rule.declarations;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>DeclarationListChecker</code> is a checker for declaration list.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class DeclarationListChecker implements Checker {

    // <lista_deklaracija> ::= <deklaracija>
    // <lista_deklaracija> ::= <lista_deklaracija> <deklaracija>

    /**
     * Name of the node.
     */
    public static final String NAME = "<DeclarationList>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<lista_deklaracija>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 68.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        Node firstChild = node.getChild(0);
        String firstSymbol = firstChild.name();

        // <lista_deklaracija> ::= <deklaracija>
        if ("<deklaracija>".equals(firstSymbol)) {

            // 1. provjeri(<deklaracija>
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            return true;
        }

        Node secondChild = node.getChild(1);
        // <lista_deklaracija> ::= <lista_deklaracija> <deklaracija>
        if ("<lista_deklaracija>".equals(firstSymbol)) {

            // 1. provjeri(<lista_deklaracija>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. provjeri(<deklaracija>)
            if (!secondChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            return true;
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

}
