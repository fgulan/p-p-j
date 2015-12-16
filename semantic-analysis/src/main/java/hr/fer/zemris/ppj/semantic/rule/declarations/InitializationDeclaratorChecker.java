package hr.fer.zemris.ppj.semantic.rule.declarations;

import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.VariableType;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>InitializationDeclaratorChecker</code> is a checker for initialization declarator.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class InitializationDeclaratorChecker implements Checker {

    // <init_deklarator> ::= <izravni_deklarator>
    // <init_deklarator> ::= <izravni_deklarator> OP_PRIDRUZI <inicijalizator>

    /**
     * Name of the node.
     */
    public static final String NAME = "<InitializationDeclarator>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<init_deklarator>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 69.
     *
     * @since alpha
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean check(Node node) {
        Node firstChild = node.getChild(0);

        int size = node.childrenCount();

        // <init_deklarator> ::= <izravni_deklarator>
        if (size == 1) {

            // 1. provjeri(<izravni_deklarator>) uz nasljedno svojstvo <izravni_deklarator>.ntip <-
            // <init_deklarator>.ntip
            firstChild.addAttributeRecursive(Attribute.ITYPE, node.getAttribute(Attribute.ITYPE));
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <itravni_deklarator>.tip != { const(T), niz(const(T)) }
            VariableType type = (VariableType) firstChild.getAttribute(Attribute.TYPE);
            if (VariableType.isConst(type)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            return true;
        }

        Node thirdChild = node.getChild(2);
        // <init_deklarator> ::= <izravni_deklarator> OP_PRIDRUZI <inicijalizator>
        if (size == 3) {

            // 1. provjeri(<izravni_deklarator>) uz nasljedno svojstvo <izravni_deklarator>.ntip <-
            // <init_deklarator>.ntip
            firstChild.addAttributeRecursive(Attribute.ITYPE, node.getAttribute(Attribute.ITYPE));
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. provjeri(<inicijalizator>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. ako je bla bla
            VariableType type = (VariableType) firstChild.getAttribute(Attribute.TYPE);
            if (VariableType.isArrayType(type)) {
                VariableType underType = VariableType.fromArrayType(type);

                Integer initElemCount = (Integer) thirdChild.getAttribute(Attribute.ELEMENT_COUNT);
                Integer declElemCount = (Integer) firstChild.getAttribute(Attribute.ELEMENT_COUNT);

                if (!(Integer.compare(initElemCount, declElemCount) <= 0)) {
                    SemanticErrorReporter.report(node);
                    return false;
                }

                for (VariableType initType : (List<VariableType>) thirdChild.getAttribute(Attribute.TYPES)) {
                    if (!VariableType.implicitConversion(initType, underType)) {
                        SemanticErrorReporter.report(node);
                        return false;
                    }
                }
            }
            else if (!VariableType.implicitConversion((VariableType) thirdChild.getAttribute(Attribute.TYPE), type)) {
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
