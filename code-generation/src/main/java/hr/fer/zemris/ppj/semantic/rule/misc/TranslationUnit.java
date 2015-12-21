package hr.fer.zemris.ppj.semantic.rule.misc;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.semantic.rule.Generator;

/**
 * <code>TranslationUnitChecker</code> is a checker for translation unit.
 *
 * @author Matea Sabolic
 *
 * @version 1.0
 */
public class TranslationUnit implements Checker, Generator {

    // <prijevodna_jedinica> ::= <vanjska_deklaracija>
    // <prijevodna_jedinica> ::= <prijevodna_jedinica> <vanjska_deklaracija>

    /**
     * Name of the node.
     */
    public static final String NAME = "<TranslationUnit>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<prijevodna_jedinica>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 65.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final Node firstChild = node.getChild(0);
        final String firstSymbol = firstChild.name();
        // <prijevodna_jedinica> ::= <vanjska_deklaracija>
        if ("<vanjska_deklaracija>".equals(firstSymbol)) {

            // 1. provjeri(<vanjska_deklaracija>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            return true;
        }

        final Node secondChild = node.getChild(1);
        // <prijevodna_jedinica> ::= <prijevodna_jedinica> <vanjska_deklaracija>
        if ("<prijevodna_jedinica>".equals(firstSymbol)) {

            // 1. provjeri(<prijevodna_jedinica>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. provjeri(<vanjska_deklaracija>)
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

    @Override
    public void generate(Node node) {
        // TODO Auto-generated method stub
        
    }
}
