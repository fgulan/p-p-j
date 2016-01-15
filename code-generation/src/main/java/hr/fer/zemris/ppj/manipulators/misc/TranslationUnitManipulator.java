package hr.fer.zemris.ppj.manipulators.misc;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>TranslationUnitManipulator</code> is a manipulator for translation unit.
 *
 * @author Matea Sabolic
 *
 * @version 1.1
 */
public class TranslationUnitManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

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
        switch (Production.fromNode(node)) {
            case TRANSLATION_UNIT_1: {
                node.getChild(0).generate();
                break;
            }

            case TRANSLATION_UNIT_2: {
                node.getChild(0).generate();
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
