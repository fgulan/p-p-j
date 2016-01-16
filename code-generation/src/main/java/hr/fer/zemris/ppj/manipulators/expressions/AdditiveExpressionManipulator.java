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
 * <code>AdditiveExpressionManipulator</code> is a manipulator for aditive expression.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
 */
public class AdditiveExpressionManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

    /**
     * Name of the node.
     */
    public static final String NAME = "<AdditiveExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<aditivni_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 57, 58.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final Node firstChild = node.getChild(0);
        final String firstSymbol = firstChild.name();

        // <aditivni_izraz> ::= <multiplikativni_izraz>
        if ("<multiplikativni_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<multiplikativni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, firstChild.getAttribute(Attribute.TYPE));
            node.addAttribute(Attribute.L_EXPRESSION, firstChild.getAttribute(Attribute.L_EXPRESSION));
            return true;
        }

        final Node secondChild = node.getChild(1);
        final String secondSymbol = secondChild.name();
        final Node thirdChild = node.getChild(2);

        // <aditivni_izraz> ::= <aditivni_izraz> PLUS <multiplikativni_izraz>
        if ("PLUS".equals(secondSymbol)) {

            // 1. provjeri(<aditivni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            final Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            // 2. <aditivni_izraz>.tip ~ int
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<multiplikativni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            final Type type2 = (Type) thirdChild.getAttribute(Attribute.TYPE);
            // 4. <multiplikativni_izraz>.tip ~ int
            if (!type2.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new IntType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <aditivni_izraz> ::= <aditivni_izraz> MINUS <multiplikativni_izraz>
        if ("MINUS".equals(secondSymbol)) {

            // 1. provjeri(<aditivni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <aditivni_izraz>.tip ~ int
            final Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<multiplikativni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <multiplikativni_izraz>.tip ~ int
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
        case ADDITIVE_EXPRESSION_1: {
            // ADDITIVE_EXPRESSION_1("<aditivni_izraz> ::= <multiplikativni_izraz>"),
            node.getChild(0).generate();
            break;
        }

        case ADDITIVE_EXPRESSION_2: {
            // ADDITIVE_EXPRESSION_2("<aditivni_izraz> ::= <aditivni_izraz> PLUS <multiplikativni_izraz>"),
            node.getChild(0).generate();
            node.getChild(2).generate();
            FRISCGenerator.generateBinaryOperation(BinaryOperation.PLUS);
            break;
        }

        case ADDITIVE_EXPRESSION_3: {
            // ADDITIVE_EXPRESSION_3("<aditivni_izraz> ::= <aditivni_izraz> MINUS <multiplikativni_izraz>"),
            node.getChild(0).generate();
            node.getChild(2).generate();
            break;
        }

        default:
            System.err.println("Generation reached undefined production!");
            break;
        }
    }
}
