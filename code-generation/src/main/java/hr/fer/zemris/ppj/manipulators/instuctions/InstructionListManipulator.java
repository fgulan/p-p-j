package hr.fer.zemris.ppj.manipulators.instuctions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>InstructionListManipulator</code> is a manipulator for instruction list.
 *
 * @author Filip Gulan
 *
 * @version 1.1
 */
public class InstructionListManipulator implements Manipulator {

    // <lista_naredbi> ::= <naredba>
    // <lista_naredbi> ::= <lista_naredbi> <naredba>

    /**
     * Name of the node.
     */
    public static final String NAME = "<InstructionList>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<lista_naredbi>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 62, 63.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        // <lista_naredbi> ::= <naredba>
        Node instruction = node.getChild(0);
        if ("<naredba>".equals(instruction.name())) {
            if (!instruction.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            return true;
        }

        // <lista_naredbi> ::= <lista_naredbi> <naredba>
        final Node instructionList = node.getChild(0);
        instruction = node.getChild(1);
        if ("<lista_naredbi>".equals(instructionList.name()) && "<naredba>".equals(instruction.name())) {
            if (!instructionList.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            if (!instruction.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            return true;
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
            case INSTRUCTION_LIST_1: {
                break;
            }

            case INSTRUCTION_LIST_2: {
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
