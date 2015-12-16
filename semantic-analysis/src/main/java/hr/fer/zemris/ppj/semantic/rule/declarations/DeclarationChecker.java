package hr.fer.zemris.ppj.semantic.rule.declarations;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>DeclarationChecker</code> is a checker for declaration.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class DeclarationChecker implements Checker {

    // <deklaracija> ::= <ime_tipa> <lista_init_deklaratora> TOCKAZAREZ

    /**
     * Name of the node.
     */
    public static final String NAME = "<Declaration>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<deklaracija>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 68.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        Node typeNode = node.getChild(0);

        if ("<ime_tipa>".equals(typeNode.name())) {

            // 1.provjeri(<ime_tipa>)
            if (!typeNode.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            Node initDeclListNode = node.getChild(1);

            // 2. provjeri(<lista_init_deklaratora>) uz nasljedno svojstvo <lista_init_deklaratora>.ntip <-
            // <ime_tipa>.tip
            initDeclListNode.addAttributeRecursive(Attribute.ITYPE, typeNode.getAttribute(Attribute.TYPE));
            if (!initDeclListNode.check()) {
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
