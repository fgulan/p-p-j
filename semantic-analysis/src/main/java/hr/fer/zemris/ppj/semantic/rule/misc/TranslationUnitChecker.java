package hr.fer.zemris.ppj.semantic.rule.misc;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>TranslationUnitChecker</code> is a checker for translation unit.
 *
 * @author Matea Sabolic
 *
 * @version alpha
 */
public class TranslationUnitChecker implements Checker {

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
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
