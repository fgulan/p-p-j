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
 * <code>MultiplicativeExpressionManipulator</code> is a manipulator for multiplicative expression.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
 */
public class MultiplicativeExpressionManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

    /**
     * Name of the node.
     */
    public static final String NAME = "<MultiplicativeExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<multiplikativni_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 57.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final Node firstChild = node.getChild(0);
        final String firstSymbol = firstChild.name();

        // <multiplikativni_izraz> ::= <cast_izraz>
        if ("<cast_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<cast_izraz>)
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
        // <multiplikativni_izraz> ::= <multiplikativni_izraz> OP_PUTA <cast_izraz>
        if ("OP_PUTA".equals(secondSymbol)) {

            // 1. provjeri(<multiplikativni_izraz>
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <multiplikativni_izraz>.tip ~ int
            final Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<cast_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <cast_izraz>.tip ~ int
            final Type type2 = (Type) thirdChild.getAttribute(Attribute.TYPE);
            if (!type2.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new IntType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <multiplikativni_izraz> ::= <multiplikativni_izraz> OP_DIJELI <cast_izraz>
        if ("OP_DIJELI".equals(secondSymbol)) {

            // 1. provjeri(<multiplikativni_izraz>
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <multiplikativni_izraz>.tip ~ int
            final Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<cast_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <cast_izraz>.tip ~ int
            final Type type2 = (Type) thirdChild.getAttribute(Attribute.TYPE);
            if (!type2.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new IntType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <multiplikativni_izraz> ::= <multiplikativni_izraz> OP_MOD <cast_izraz>
        if ("OP_MOD".equals(secondSymbol)) {

            // 1. provjeri(<multiplikativni_izraz>
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <multiplikativni_izraz>.tip ~ int
            final Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<cast_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <cast_izraz>.tip ~ int
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
        case MULTIPLICATIVE_EXPRESSION_1: {
            // MULTIPLICATIVE_EXPRESSION_1("<multiplikativni_izraz> ::= <cast_izraz>"),
            node.getChild(0).generate();
            break;
        }

        case MULTIPLICATIVE_EXPRESSION_2: {
            // MULTIPLICATIVE_EXPRESSION_2("<multiplikativni_izraz> ::= <multiplikativni_izraz> OP_PUTA <cast_izraz>"),
            node.getChild(0).generate();
            node.getChild(2).generate();
            FRISCGenerator.generateBinaryOperation(BinaryOperation.MUL);
            break;
        }

        case MULTIPLICATIVE_EXPRESSION_3: {
            // MULTIPLICATIVE_EXPRESSION_3("<multiplikativni_izraz> ::= <multiplikativni_izraz> OP_DIJELI
            // <cast_izraz>"),
            node.getChild(0).generate();
            node.getChild(2).generate();
            FRISCGenerator.generateBinaryOperation(BinaryOperation.DIV);
            break;
        }

        case MULTIPLICATIVE_EXPRESSION_4: {
            // MULTIPLICATIVE_EXPRESSION_4("<multiplikativni_izraz> ::= <multiplikativni_izraz> OP_MOD <cast_izraz>"),
            node.getChild(0).generate();
            node.getChild(2).generate();
            FRISCGenerator.generateBinaryOperation(BinaryOperation.MOD);
            break;
        }

        default:
            System.err.println("Generation reached undefined production!");
            break;
        }
    }
}
