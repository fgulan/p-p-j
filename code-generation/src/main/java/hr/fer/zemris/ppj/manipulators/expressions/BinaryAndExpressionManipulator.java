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
 * <code>BinaryAndExpressionManipulator</code> is a manipulator for binary and expression.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
 */
public class BinaryAndExpressionManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

    /**
     * Name of the node.
     */
    public static final String NAME = "<BinaryAndExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<bin_i_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 59.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final Node firstChild = node.getChild(0);
        final String firstSymbol = firstChild.name();

        // <bin_i_izraz> ::= <jednakosni_izraz>
        if ("<jednakosni_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<jednakosni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, firstChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.L_EXPRESSION, firstChild.getAttribute(Attribute.L_EXPRESSION));
            return true;
        }

        final Node thirdChild = node.getChild(2);
        // <bin_i_izraz> ::= <bin_i_izraz> OP_BIN_I <jednakosni_izraz>
        if ("<bin_i_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<bin_i_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <bin_i_izraz>.tip ~ int
            final Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<jednakosni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <jednakosni_izraz>.tip ~ int
            final Type type2 = (Type) firstChild.getAttribute(Attribute.TYPE);
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
        case BINARY_AND_EXPRESSION_1: {
            // BINARY_AND_EXPRESSION_1("<bin_i_izraz> ::= <jednakosni_izraz>"),
            node.getChild(0).generate();
            break;
        }

        case BINARY_AND_EXPRESSION_2: {
            // BINARY_AND_EXPRESSION_2("<bin_i_izraz> ::= <bin_i_izraz> OP_BIN_I <jednakosni_izraz>"),
            node.getChild(0).generate();
            node.getChild(2).generate();
            FRISCGenerator.generateBinaryOperation(BinaryOperation.AND);
            break;
        }

        default:
            System.err.println("Generation reached undefined production!");
            break;
        }
    }
}
