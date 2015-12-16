package hr.fer.zemris.ppj.semantic.rule.declarations;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>DeclaratorInitializationListChecker</code> is a checker for declarator initialization list.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
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
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        Node firstChild = node.getChild(0);
        String firstSymbol = firstChild.name();

        if ("<init_deklarator>".equals(firstSymbol)) {

            // 1. provjeri(<init_deklarator>) uz nasljedno svojstvo <init_deklarator>.ntip <-
            // <lista_init_deklaratora>.ntip
            firstChild.addAttributeRecursive(Attribute.ITYPE, node.getAttribute(Attribute.ITYPE));
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            return true;
        }

        Node thirdChild = node.getChild(2);
        if ("<lista_init_deklaratora>".equals(firstSymbol)) {

            // 1. provjeri(<lista_init_deklaratora>2) uz nasljedno svojstvo <lista_init_deklaratora>2.ntip <-
            // <lista_init_deklaratora>1.ntip
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. provjeri(<init_deklarator>) uz nasljedno svojstvo <init_deklarator>.ntip <-
            // <lista_init_deklaratora>1.ntip
            if (!thirdChild.check()) {
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
