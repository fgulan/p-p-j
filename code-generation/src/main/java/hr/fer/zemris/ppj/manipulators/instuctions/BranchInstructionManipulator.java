package hr.fer.zemris.ppj.manipulators.instuctions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.interfaces.Manipulator;
import hr.fer.zemris.ppj.types.IntType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>BranchInstructionChecker</code> is a checker for branch instruction.
 *
 * @author Filip Gulan
 *
 * @version 1.0
 */
public class BranchInstructionManipulator implements Manipulator {

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
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final int count = node.getChildren().size();

        // <naredba_grananja> ::= KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba> KR_ELSE <naredba>
        if (count == 7) {
            final Node expression = node.getChild(2);
            final Node firstInstruction = node.getChild(4);
            final Node secondInstruction = node.getChild(6);

            if ("<naredba>".equals(firstInstruction.name()) && "<naredba>".equals(secondInstruction.name())
                    && "<izraz>".equals(expression.name())) {
                if (!expression.check()) {
                    SemanticErrorReporter.report(node);
                    return false;
                }

                final Type type = (Type) expression.getAttribute(Attribute.TYPE);
                final boolean ableToConvert = type.implicitConversion(new IntType());

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
            final Node expression = node.getChild(2);
            final Node firstInstruction = node.getChild(4);

            if ("<naredba>".equals(firstInstruction.name()) && "<izraz>".equals(expression.name())) {
                if (!expression.check()) {
                    SemanticErrorReporter.report(node);
                    return false;
                }

                final Type type = (Type) expression.getAttribute(Attribute.TYPE);
                final boolean ableToConvert = type.implicitConversion(new IntType());

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

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
            case BRANCH_INSTRUCTION_1: {
                break;
            }

            case BRANCH_INSTRUCTION_2: {
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
