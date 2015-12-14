package hr.fer.zemris.ppj.semantic.rule.declarations;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>DirectDeclaratorChecker</code> is a checker for direct declarator.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class DirectDeclaratorChecker implements Checker {

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

        Node idn = node.getChild(0);
        if (!idn.check()){
            return Utils.badNode(node);
        }
        
        String name = (String) idn.getAttribute(Attribute.VALUE);
        if (node.identifierTable().isFunctionDeclared(name)
                || node.identifierTable().isVariableDeclared(name)){
            return Utils.badNode(node);
        }
        
        //TODO check other productions
        
        return false;
    }

}
