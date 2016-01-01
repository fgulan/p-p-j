package hr.fer.zemris.ppj.manipulators.instuctions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.interfaces.Manipulator;

/**
 * <code>InstructionChecker</code> is a checker for instruction.
 *
 * @author Filip Gulan
 *
 * @version 1.0
 */
public class InstructionManipulator implements Manipulator {

    // <naredba> ::= <slozena_naredba>
    // <naredba> ::= <izraz_naredba>
    // <naredba> ::= <naredba_grananja>
    // <naredba> ::= <naredba_petlje>
    // <naredba> ::= <naredba_skoka>

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
        // TODO Auto-generated method stub

    }
}
