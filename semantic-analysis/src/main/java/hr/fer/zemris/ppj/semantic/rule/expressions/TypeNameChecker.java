package hr.fer.zemris.ppj.semantic.rule.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.VariableType;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>TypeNameChecker</code> is a checker for type name.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class TypeNameChecker implements Checker {

    // <ime_tipa> ::= <specifikator_tipa>
    // <ime_tipa> ::= KR_CONST <specifikator_tipa>

    /**
     * Name of the node.
     */
    public static final String NAME = "<TypeName>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<ime_tipa>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 56.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        Node firstChild = node.getChild(0);
        String firstSymbol = firstChild.name();
        // <ime_tipa> ::= <specifikator_tipa>
        if ("<specifikator_tipa>".equals(firstSymbol)) {

            // 1. provjeri(<specifikator_tipa>
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, firstChild.getAttribute(Attribute.TYPE));
            return true;
        }

        Node secondChild = node.getChild(1);
        // <ime_tipa> ::= KR_CONST <specifikator_tipa>
        if ("KR_CONST".equals(firstSymbol)) {

            // 1. provjeri(<specifikator_tipa>)
            if (!secondChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <specifikator_tipa>.tip != void
            if (VariableType.VOID.equals(secondChild.getAttribute(Attribute.TYPE))) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE,
                    VariableType.toConst((VariableType) secondChild.getAttribute(Attribute.TYPE)));
            return true;
        }
        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

}
