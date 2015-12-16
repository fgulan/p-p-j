package hr.fer.zemris.ppj.semantic.rule.declarations;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.VariableType;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>DirectDeclaratorChecker</code> is a checker for direct declarator.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class DirectDeclaratorChecker implements Checker {

    private static final int MIN_ARRAY_SIZE = 1;
    private static final int MAX_ARRAY_SIZE = 1024;

    // <izravni_deklarator> ::= IDN
    // <izravni_deklarator> ::= IDN L_UGL_ZAGRADA BROJ D_UGL_ZAGRADA
    // <izravni_deklarator> ::= IDN L_ZAGRADA KR_VOID D_ZAGRADA
    // <izravni_deklarator> ::= IDN L_ZAGRADA <lista_parametara> D_ZAGRADA

    /**
     * Name of the node.
     */
    public static final String NAME = "<DirectDeclarator>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<izravni_deklarator>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 69, 70.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        Node firstChild = node.getChild(0);
        int size = node.childrenCount();

        if (!firstChild.check()) {
            SemanticErrorReporter.report(node);
            return false;
        }

        // <izravni_deklarator> ::= IDN
        if (size == 1) {

            // 1. ntip != void
            if (node.getAttribute(Attribute.ITYPE).equals(VariableType.VOID)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. IDN.ime nije deklarirano u lokalnom djelokrugu
            String name = (String) firstChild.getAttribute(Attribute.VALUE);
            if (node.identifierTable().isLocalDeclared(name)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. zabiljezi deklaraciju IDN.ime s odgovarajucim tipom
            node.identifierTable().declareVariable(name, (VariableType) node.getAttribute(Attribute.ITYPE));

            node.addAttribute(Attribute.TYPE, node.getAttribute(Attribute.ITYPE));
            return true;
        }

        Node thirdChild = node.getChild(2);
        String thirdSymbol = thirdChild.name();
        // <izravni_deklarator> ::= IDN L_UGL_ZAGRADA BROJ D_UGL_ZAGRADA
        if ("BROJ".equals(thirdSymbol)) {

            // 1. ntip != void
            if (node.getAttribute(Attribute.ITYPE).equals(VariableType.VOID)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. IDN.ime nije deklarirano u lokalnom djelokrugu
            String name = (String) firstChild.getAttribute(Attribute.VALUE);
            if (node.identifierTable().isLocalDeclared(name)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. BROJ.vrijednost je pozitivan broj (>0) ne veci od 1024
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            int value = ((Integer) thirdChild.getAttribute(Attribute.VALUE)).intValue();
            if ((value < MIN_ARRAY_SIZE) || (value > MAX_ARRAY_SIZE)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. zabiljezi deklaraciju IDN.ime s odgovarajucim tipom
            node.identifierTable().declareVariable(name, (VariableType) node.getAttribute(Attribute.ITYPE));

            node.addAttribute(Attribute.TYPE, node.getAttribute(Attribute.ITYPE));
            node.addAttribute(Attribute.ELEMENT_COUNT, value);
            return true;
        }

        // <izravni_deklarator> ::= IDN L_ZAGRADA KR_VOID D_ZAGRADA
        if ("KR_VOID".equals(thirdSymbol)) {
            String name = (String) firstChild.getAttribute(Attribute.VALUE);
            VariableType type = (VariableType) node.getAttribute(Attribute.ITYPE);

            List<VariableType> args = new ArrayList<>();
            if (!Utils.handleFunction(node.identifierTable(), name, args, type)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPES, args);
            node.addAttribute(Attribute.RETURN_VALUE, type);

            return true;
        }

        // <izravni_deklarator> ::= IDN L_ZAGRADA <lista_parametara> D_ZAGRADA
        if ("<lista_parametara>".equals(thirdSymbol)) {

            // 1. provjeri(<lista_parametara>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            String name = (String) firstChild.getAttribute(Attribute.VALUE);
            VariableType type = (VariableType) node.getAttribute(Attribute.ITYPE);
            List<VariableType> args = (List<VariableType>) thirdChild.getAttribute(Attribute.TYPES);

            if (!Utils.handleFunction(node.identifierTable(), name, args, type)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPES, args);
            node.addAttribute(Attribute.RETURN_VALUE, type);

            return true;
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

}
