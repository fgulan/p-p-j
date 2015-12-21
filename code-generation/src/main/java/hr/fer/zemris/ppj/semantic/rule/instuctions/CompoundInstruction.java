package hr.fer.zemris.ppj.semantic.rule.instuctions;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.semantic.rule.Generator;

/**
 * <code>CompoundInstructionChecker</code> is a checker for compound instruction.
 *
 * @author Filip Gulan
 *
 * @version 1.0
 */
public class CompoundInstruction implements Checker, Generator {

    // <slozena_naredba> ::= L_VIT_ZAGRADA <lista_naredbi> D_VIT_ZAGRADA
    // <slozena_naredba> ::= L_VIT_ZAGRADA <lista_deklaracija> <lista_naredbi> D_VIT_ZAGRADA

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
        // TODO Auto-generated method stub
        
    }
}
