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
 * <code>RelationalExpressionManipulator</code> is a manipulator for relational expression.
 *
 * @author Jan Kelemen
 *
 * @version 1.1
 */
public class RelationalExpressionManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

    /**
     * Name of the node.
     */
    public static final String NAME = "<RelationalExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<odnosni_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 58.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final Node firstChild = node.getChild(0);
        final String firstSymbol = firstChild.name();

        // <odnosni_izraz> ::= <aditivni_izraz>
        if ("<aditivni_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<aditivni_izraz>)
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

        // <odnosni_izraz> ::= <odnosni_izraz> OP_LT <aditivni_izraz>
        if ("OP_LT".equals(secondSymbol)) {

            // 1. provjeri(<odnosni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <odnosni_izraz>.tip ~ int
            final Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<aditivni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <aditivni_izraz>.tip ~ int
            final Type type2 = (Type) thirdChild.getAttribute(Attribute.TYPE);
            if (!type2.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new IntType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <odnosni_izraz> ::= <odnosni_izraz> OP_GT <aditivni_izraz>
        if ("OP_GT".equals(secondSymbol)) {

            // 1. provjeri(<odnosni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <odnosni_izraz>.tip ~ int
            final Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<aditivni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <aditivni_izraz>.tip ~ int
            final Type type2 = (Type) thirdChild.getAttribute(Attribute.TYPE);
            if (!type2.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new IntType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <odnosni_izraz> ::= <odnosni_izraz> OP_LTE <aditivni_izraz>
        if ("OP_LTE".equals(secondSymbol)) {

            // 1. provjeri(<odnosni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <odnosni_izraz>.tip ~ int
            final Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<aditivni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <aditivni_izraz>.tip ~ int
            final Type type2 = (Type) thirdChild.getAttribute(Attribute.TYPE);
            if (!type2.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new IntType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <odnosni_izraz> ::= <odnosni_izraz> OP_GTE <aditivni_izraz>
        if ("OP_GTE".equals(secondSymbol)) {

            // 1. provjeri(<odnosni_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <odnosni_izraz>.tip ~ int
            final Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type1.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<aditivni_izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <aditivni_izraz>.tip ~ int
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
            case RELATIONAL_EXPRESSION_1: {
                // RELATIONAL_EXPRESSION_1("<odnosni_izraz> ::= <aditivni_izraz>"),
                node.getChild(0).generate();
                break;
            }

            case RELATIONAL_EXPRESSION_2: {
                // RELATIONAL_EXPRESSION_2("<odnosni_izraz> ::= <odnosni_izraz> OP_LT <aditivni_izraz>"),
                node.getChild(0).generate();
                node.getChild(2).generate();
                FRISCGenerator.generateBinaryOperation(BinaryOperation.LT);
                break;
            }

            case RELATIONAL_EXPRESSION_3: {
                // RELATIONAL_EXPRESSION_3("<odnosni_izraz> ::= <odnosni_izraz> OP_GT <aditivni_izraz>"),
                node.getChild(0).generate();
                node.getChild(2).generate();
                FRISCGenerator.generateBinaryOperation(BinaryOperation.GT);
                break;
            }

            case RELATIONAL_EXPRESSION_4: {
                // RELATIONAL_EXPRESSION_4("<odnosni_izraz> ::= <odnosni_izraz> OP_LTE <aditivni_izraz>"),
                node.getChild(0).generate();
                node.getChild(2).generate();
                FRISCGenerator.generateBinaryOperation(BinaryOperation.LE);
                break;
            }

            case RELATIONAL_EXPRESSION_5: {
                // RELATIONAL_EXPRESSION_5("<odnosni_izraz> ::= <odnosni_izraz> OP_GTE <aditivni_izraz>"),
                node.getChild(0).generate();
                node.getChild(2).generate();
                FRISCGenerator.generateBinaryOperation(BinaryOperation.GE);
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
