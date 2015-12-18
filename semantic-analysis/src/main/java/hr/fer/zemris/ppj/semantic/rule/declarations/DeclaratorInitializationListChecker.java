package hr.fer.zemris.ppj.semantic.rule.declarations;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>DeclaratorInitializationListChecker</code> is a checker for declarator initialization list.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
 */
public class DeclaratorInitializationListChecker implements Checker {

    // <lista_init_deklaratora> ::= <init_deklarator>
    // <lista_init_deklaratora> ZAREZT <init_deklarator>

    /**
     * Name of the node.
     */
    public static final String NAME = "<DeclaratorInitializationList>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<lista_init_deklaratora>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 68.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {

        for (final Node child : node.getChildren()) {
            child.addAttribute(Attribute.ITYPE, node.getAttribute(Attribute.ITYPE));
            if (!child.check()) {
                return Utils.badNode(child);
            }
        }

        return true;
    }

}
