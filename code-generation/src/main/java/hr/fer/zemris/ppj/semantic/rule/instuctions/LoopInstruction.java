package hr.fer.zemris.ppj.semantic.rule.instuctions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.semantic.rule.Checker;

import hr.fer.zemris.ppj.types.IntType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>LoopInstructionChecker</code> is a checker for loop instruction.
 *
 * @author Filip Gulan
 *
 * @version 1.0
 */
public class LoopInstruction implements Checker {

    // <naredba_petlje> ::= KR_WHILE L_ZAGRADA <izraz> D_ZAGRADA <naredba>
    // <naredba_petlje> ::= KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredbs> D_ZAGRADA <naredba>
    // <naredba_petlje> ::= KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredba> <izraz> D_ZAGRADA <naredba>

    /**
     * Name of the node.
     */
    public static final String NAME = "<LoopInstruction>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<naredba_petlje>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 64.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        // <naredba_petlje> ::= KR_WHILE L_ZAGRADA <izraz> D_ZAGRADA <naredba>
        Node expression = node.getChild(2);
        Node instruction = node.getChild(4);
        if ("<naredba>".equals(instruction.name()) && "<izraz>".equals(expression.name())) {
            node.addAttributeRecursive(Attribute.INSIDE_LOOP, true);

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

            if (!instruction.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            return true;
        }

        // <naredba_petlje> ::= KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredba> D_ZAGRADA <naredba>
        Node firstExpInstruction = node.getChild(2);
        Node secondExpInstruction = node.getChild(3);
        instruction = node.getChild(5);

        if ("<naredba>".equals(instruction.name()) && "<izraz_naredba>".equals(firstExpInstruction.name())
                && "<izraz_naredba>".equals(secondExpInstruction.name())) {
            node.addAttributeRecursive(Attribute.INSIDE_LOOP, true);

            if (!firstExpInstruction.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            if (!secondExpInstruction.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            final Type type = (Type) secondExpInstruction.getAttribute(Attribute.TYPE);
            final boolean ableToConvert = type == null ? true : type.implicitConversion(new IntType());

            if (!ableToConvert) {
                SemanticErrorReporter.report(node);
                return false;
            }

            if (!instruction.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            return true;
        }

        firstExpInstruction = node.getChild(2);
        secondExpInstruction = node.getChild(3);
        expression = node.getChild(4);
        instruction = node.getChild(6);

        if ("<naredba>".equals(instruction.name()) && "<izraz_naredba>".equals(firstExpInstruction.name())
                && "<izraz_naredba>".equals(secondExpInstruction.name()) && "<izraz>".equals(expression.name())) {
            node.addAttributeRecursive(Attribute.INSIDE_LOOP, true);

            if (!firstExpInstruction.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            if (!secondExpInstruction.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            final Type type = (Type) secondExpInstruction.getAttribute(Attribute.TYPE);
            final boolean ableToConvert = type.implicitConversion(new IntType());

            if (!ableToConvert) {
                SemanticErrorReporter.report(node);
                return false;
            }

            if (!expression.check()) {
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
        // TODO Auto-generated method stub
        
    }
}
