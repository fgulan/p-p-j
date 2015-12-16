package hr.fer.zemris.ppj.semantic.rule.instuctions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.FunctionWrapper;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.VariableType;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>JumpInstructionChecker</code> is a checker for jump instruction.
 *
 * @author Filip Gulan
 *
 * @version alpha
 */
public class JumpInstructionChecker implements Checker {

    // <naredba_skoka> ::= KR_CONTINUE TOCKAZAREZ
    // <naredba_skoka> ::= KR_BREAK TOCKAZAREZ
    // <naredba_skoka> ::= KR_RETURN TOCKAZAREZ
    // <naredba_skoka> ::= KR_RETURN <izraz> TOCKAZAREZ

    /**
     * Name of the node.
     */
    public static final String NAME = "<JumpInstruction>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<naredba_skoka>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 64, 65.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // <naredba_skoka> ::= KR_CONTINUE TOCKAZAREZ
        // <naredba_skoka> ::= KR_BREAK TOCKAZAREZ
        if ("KR_CONTINUE".equals(node.getChild(0).name()) || "KR_BREAK".equals(node.getChild(0).name())) {
            boolean insideLoop = (Boolean) node.getAttribute(Attribute.INSIDE_LOOP);
            if (!insideLoop) {
                SemanticErrorReporter.report(node);
                return false;
            }
            return true;
        }

        // <naredba_skoka> ::= KR_RETURN TOCKAZAREZ
        if ("KR_RETURN".equals(node.getChild(0).name()) && "TOCKAZAREZ".equals(node.getChild(1).name())) {
            String functionName = (String) node.getAttribute(Attribute.FUNCTION_NAME);
            FunctionWrapper function = node.identifierTable().function(functionName);

            if (!function.returnType().equals(VariableType.VOID)) {
                SemanticErrorReporter.report(node);
                return false;
            }
            return true;
        }

        // <naredba_skoka> ::= KR_RETURN <izraz> TOCKAZAREZ
        if ("KR_RETURN".equals(node.getChild(0).name()) && "<izraz>".equals(node.getChild(1).name())) {
            String functionName = (String) node.getAttribute(Attribute.FUNCTION_NAME);
            FunctionWrapper function = node.identifierTable().function(functionName);
            VariableType type = (VariableType) node.getChild(1).getAttribute(Attribute.TYPE);
            
            boolean ableToConvert = VariableType.implicitConversion(type, function.returnType());

            if (!ableToConvert) {
                SemanticErrorReporter.report(node);
                return false;
            }
            return true;
        }
        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

}
