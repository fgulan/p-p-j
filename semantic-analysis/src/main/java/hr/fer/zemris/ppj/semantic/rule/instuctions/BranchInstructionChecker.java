package hr.fer.zemris.ppj.semantic.rule.instuctions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.types.IntType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>BranchInstructionChecker</code> is a checker for branch instruction.
 *
 * @author Filip Gulan
 *
 * @version alpha
 */
public class BranchInstructionChecker implements Checker {

    // <naredba_grananja> ::= KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba>
    // <naredba_grananja> ::= KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba> KR_ELSE <naredba>

    /**
     * Name of the node.
     */
    public static final String NAME = "<BranchInstruction>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<naredba_grananja>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 63, 64.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        int count = node.getChildren().size();

        // <naredba_grananja> ::= KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba> KR_ELSE <naredba>
        if (count == 7) {
            Node expression = node.getChild(2);
            Node firstInstruction = node.getChild(4);
            Node secondInstruction = node.getChild(6);

            if ("<naredba>".equals(firstInstruction.name()) && "<naredba>".equals(secondInstruction.name())
                    && "<izraz>".equals(expression.name())) {
                if (!expression.check()) {
                    SemanticErrorReporter.report(node);
                    return false;
                }

                Type type = (Type) expression.getAttribute(Attribute.TYPE);
                boolean ableToConvert = type.implicitConversion(new IntType());

                if (!ableToConvert) {
                    SemanticErrorReporter.report(node);
                    return false;
                }

                if (!firstInstruction.check()) {
                    SemanticErrorReporter.report(node);
                    return false;
                }
                if (!secondInstruction.check()) {
                    SemanticErrorReporter.report(node);
                    return false;
                }

                return true;
            }
        }

        // <naredba_grananja> ::= KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba>
        if (count == 5) {
            Node expression = node.getChild(2);
            Node firstInstruction = node.getChild(4);

            if ("<naredba>".equals(firstInstruction.name()) && "<izraz>".equals(expression.name())) {
                if (!expression.check()) {
                    SemanticErrorReporter.report(node);
                    return false;
                }

                Type type = (Type) expression.getAttribute(Attribute.TYPE);
                boolean ableToConvert = type.implicitConversion(new IntType());

                if (!ableToConvert) {
                    SemanticErrorReporter.report(node);
                    return false;
                }

                if (!firstInstruction.check()) {
                    SemanticErrorReporter.report(node);
                    return false;
                }

                return true;
            }
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

}
