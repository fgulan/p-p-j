package hr.fer.zemris.ppj.semantic.rule.declarations;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>ParameterDeclarationChecker</code> is a checker for parameter declaration.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class ParameterDeclarationChecker implements Checker {

    // <deklaracija_parametra> ::= <ime_tipa> IDN
    // <deklaracija_parametre> ::= <ime_tipa> IDN L_UGL_ZAGRADA D_UGL_ZAGRADA

    /**
     * Name of the node.
     */
    public static final String NAME = "<ParameterDeclaration>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<deklaracija_parametra>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 67.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {

        Node typeNode = node.getChild(0);
        Node idnNode = node.getChild(1);

        if (!typeNode.check() || !idnNode.check()) {
            return Utils.badNode(node);
        }

        Type type = (Type) typeNode.getAttribute(Attribute.TYPE);
        String name = (String) idnNode.getAttribute(Attribute.VALUE);

        if (node.childrenCount() > 2) {
            type = type.toArray();
        }

        node.addAttribute(Attribute.TYPE, type);
        node.addAttribute(Attribute.VALUE, name);

        return true;
    }

}
