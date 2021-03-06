package hr.fer.zemris.ppj.manipulators.instuctions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>InstructionManipulator</code> is a manipulator for instruction.
 *
 * @author Filip Gulan
 *
 * @version 1.1
 */
public class InstructionManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

    /**
     * Name of the node.
     */
    public static final String NAME = "<Instruction>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<naredba>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 63.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final Node instruction = node.getChild(0);

        // <naredba> ::= <slozena_naredba>
        if ("<slozena_naredba>".equals(instruction.name())) {
            if (!instruction.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            return true;
        }

        // <naredba> ::= <izraz_naredba>
        if ("<izraz_naredba>".equals(instruction.name())) {
            if (!instruction.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            return true;
        }

        // <naredba> ::= <naredba_grananja>
        if ("<naredba_grananja>".equals(instruction.name())) {
            if (!instruction.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            return true;
        }

        // <naredba> ::= <naredba_petlje>
        if ("<naredba_petlje>".equals(instruction.name())) {
            if (!instruction.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            return true;
        }

        // <naredba> ::= <naredba_skoka>
        if ("<naredba_skoka>".equals(instruction.name())) {
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
        case INSTRUCTION_1: {
            // INSTRUCTION_1("<naredba> ::= <slozena_naredba>"),
            node.getChild(0).generate();
            break;
        }

        case INSTRUCTION_2: {
            // INSTRUCTION_2("<naredba> ::= <izraz_naredba>"),
            node.getChild(0).generate();
            break;
        }

        case INSTRUCTION_3: {
            // INSTRUCTION_3("<naredba> ::= <naredba_grananja>"),
            node.getChild(0).generate();
            break;
        }

        case INSTRUCTION_4: {
            // INSTRUCTION_4("<naredba> ::= <naredba_petlje>"),
            node.getChild(0).generate();
            break;
        }

        case INSTRUCTION_5: {
            // INSTRUCTION_5("<naredba> ::= <naredba_skoka>")
            node.getChild(0).generate();
            break;
        }

        default:
            System.err.println("Generation reached undefined production!");
            break;
        }
    }
}
