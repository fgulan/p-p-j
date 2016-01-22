package hr.fer.zemris.ppj.manipulators.instuctions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.code.generator.CallStack;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>CompoundInstructionManipulator</code> is a manipulator for compound instruction.
 *
 * @author Filip Gulan
 *
 * @version 1.1
 */
public class CompoundInstructionManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

    /**
     * Name of the node.
     */
    public static final String NAME = "<CompoundInstruction>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<slozena_naredba>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 62.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        // <slozena_naredba> ::= L_VIT_ZAGRADA <lista_naredbi> D_VIT_ZAGRADA
        Node instructionList = node.getChild(1);
        if ("<lista_naredbi>".equals(instructionList.name())) {
            if (!instructionList.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            return true;
        }

        // <slozena_naredba> ::= L_VIT_ZAGRADA <lista_deklaracija> <lista_naredbi> D_VIT_ZAGRADA
        final Node declarationList = node.getChild(1);
        instructionList = node.getChild(2);
        if ("<lista_deklaracija>".equals(declarationList.name()) && "<lista_naredbi>".equals(instructionList.name())) {
            if (!declarationList.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            if (!instructionList.check()) {
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
            case COMPOUND_INSTRUCTION_1: {
                // COMPOUND_INSTRUCTION_1("<slozena_naredba> ::= L_VIT_ZAGRADA <lista_naredbi> D_VIT_ZAGRADA"),
                node.getChild(1).generate();
                break;
            }

            case COMPOUND_INSTRUCTION_2: {
                // COMPOUND_INSTRUCTION_2("<slozena_naredba> ::= L_VIT_ZAGRADA <lista_deklaracija> <lista_naredbi>
                // D_VIT_ZAGRADA"),
                CallStack.setScopeStart();
                node.getChild(1).generate();
                node.getChild(2).generate();
                CallStack.clearScope();
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
