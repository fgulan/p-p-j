package hr.fer.zemris.ppj.manipulators.expressions;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.BinaryOperation;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.code.generator.FRISCGenerator;
import hr.fer.zemris.ppj.interfaces.Manipulator;
import hr.fer.zemris.ppj.types.IntType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>BinaryOrExpressionManipulator</code> is a manipulator for binary or expression.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
 */
public class BinaryOrExpressionManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

    /**
     * Name of the node.
     */
    public static final String NAME = "<BinaryOrExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<bin_ili_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 60.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final Node firstChild = node.getChild(0);
        final String firstSymbol = firstChild.name();

        // <bin_ili_izraz> ::= <bin_xili_izraz>
        if ("<bin_xili_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<bin_xili_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, firstChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.L_EXPRESSION, firstChild.getAttribute(Attribute.L_EXPRESSION));
            return true;
        }

        final Node thirdChild = node.getChild(2);
        // <bin_ili_izraz> ::= <bin_ili_izraz> OP_BIN_ILI <bin_xili_izraz>
        if ("<bin_ili_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<bin_ili_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <bin_ili_izraz>.tip ~ int
            final Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<bin_xili_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <bin_xili_izraz>.tip ~ int
            final Type type2 = (Type) thirdChild.getAttribute(Attribute.TYPE);
            if (!type2.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new IntType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
        case BINARY_OR_EXPRESSION_1: {
            // BINARY_OR_EXPRESSION_1("<bin_ili_izraz> ::= <bin_xili_izraz>"),
            node.getChild(0).generate();
            break;
        }

        case BINARY_OR_EXPRESSION_2: {
            // BINARY_OR_EXPRESSION_2("<bin_ili_izraz> ::= <bin_ili_izraz> OP_BIN_ILI <bin_xili_izraz>"),
            node.getChild(0).generate();
            node.getChild(2).generate();
            FRISCGenerator.generateBinaryOperation(BinaryOperation.OR);
            break;
        }

        default:
            System.err.println("Generation reached undefined production!");
            break;
        }
    }
}
