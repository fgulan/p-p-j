package hr.fer.zemris.ppj.semantic.rule.expressions;

import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.semantic.rule.Generator;
import hr.fer.zemris.ppj.types.IntType;
import hr.fer.zemris.ppj.types.Type;
import hr.fer.zemris.ppj.types.functions.FunctionType;

/**
 * <code>PostfixExpressionChecker</code> is a checker for postfix expression.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public class PostfixExpression implements Checker, Generator {

    // <postfiks_izraz> ::= <primarni_izraz>
    // <postfiks_izraz> ::= <postfiks_izraz> OP_INC
    // <postfiks_izraz> ::= <postfiks_izraz> OP_DEC
    // <postfiks_izraz> ::= <postfiks_izraz> L_ZAGRADA D_ZAGRADA
    // <postfiks_izraz> ::= <postfiks_izraz> L_UGL_ZAGRADA <izraz> D_UGL_ZAGRADA
    // <postfiks_izraz> ::= <postfiks_izraz> L_ZAGRADA <lista_argumenata> D_ZAGRADA

    /**
     * Name of the node.
     */
    public static final String NAME = "<PostfixExpression>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<postfiks_izraz>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 52, 53, 54.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {
        final Node firstChild = node.getChild(0);
        final String firstSymbol = firstChild.name();

        // <postfiks_izraz> ::= <primarni_izraz>
        if ("<primarni_izraz>".equals(firstSymbol)) {

            // 1. provjeri(<primarni_izraz>)
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
        // <postfiks_izraz> ::= <postfiks_izraz> OP_INC
        if ("<postfiks_izraz>".equals(firstSymbol) && "OP_INC".equals(secondSymbol)) {
            // IDENTICNO KAO I FUNKCIJA ISPOD, U SLUCAJU BUGA ISPRAVITI OBJE

            // 1. provjeri(<postfiks_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <postfiks_izraz>.l-izraz = 1 && <postfiks_izraz>.tip ~ int
            final Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!(firstChild.getAttribute(Attribute.L_EXPRESSION).equals(true)
                    && type1.implicitConversion(new IntType()))) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new IntType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <postfiks_izraz> ::= <postfiks_izraz> OP_DEC
        if ("<postfiks_izraz>".equals(firstSymbol) && "OP_DEC".equals(secondSymbol)) {

            // 1. provjeri(<postfiks_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <postfiks_izraz>.l-izraz = 1 && <postfiks_izraz>.tip ~ int
            final Type type1 = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!(firstChild.getAttribute(Attribute.L_EXPRESSION).equals(true)
                    && type1.implicitConversion(new IntType()))) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, new IntType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        final Node thirdChild = node.getChild(2);
        final String thirdSymbol = thirdChild.name();
        // <postfiks_izraz> ::= <postfiks_izraz> L_ZAGRADA D_ZAGRADA
        if ("<postfiks_izraz>".equals(firstSymbol) && "L_ZAGRADA".equals(secondSymbol)
                && "D_ZAGRADA".equals(thirdSymbol)) {

            // 1. provjeri(<postfiks_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <postfiks_izraz>.tip = funkcija(void -> pov)
            final FunctionType function = (FunctionType) firstChild.getAttribute(Attribute.TYPE);
            if (!function.argumentList().isEmpty()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, function.returnType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <postfiks_izraz> ::= <postfiks_izraz> L_ZAGRADA <lista_argumenata> D_ZAGRADA
        if ("<postfiks_izraz>".equals(firstSymbol) && "L_ZAGRADA".equals(secondSymbol)
                && "<lista_argumenata>".equals(thirdSymbol)) {

            // 1. provjeri(<postfiks_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. provjeri(<lista_argumenata>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. <postfiks_izraz>.tip = funkcija(params -> pov)
            final Type type = (Type) firstChild.getAttribute(Attribute.TYPE);
            if (!type.isFunction()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            final FunctionType function = (FunctionType) type;
            if (function.argumentList().isEmpty()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjera implicitnih konverzija parametara
            final List<Type> declarationTypes = function.argumentList();
            @SuppressWarnings("unchecked")
            final List<Type> callingArguments = (List<Type>) thirdChild.getAttribute(Attribute.TYPES);
            if (declarationTypes.size() != callingArguments.size()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            for (int i = 0; i < declarationTypes.size(); i++) {
                final Type from = callingArguments.get(i);
                final Type to = declarationTypes.get(i);
                if (!from.implicitConversion(to)) {
                    SemanticErrorReporter.report(node);
                    return false;
                }
            }

            node.addAttribute(Attribute.TYPE, function.returnType());
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <postfiks_izraz> ::= <postfiks_izraz> L_UGL_ZAGRADA <izraz> D_UGL_ZAGRADA
        if ("<postfiks_izraz>".equals(firstSymbol) && "L_UGL_ZAGRADA".equals(secondSymbol)
                && "<izraz>".equals(thirdSymbol)) {

            // 1. provjeri(<postfiks_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            final Type type = (Type) firstChild.getAttribute(Attribute.TYPE);

            // 2. <postfiks_izraz>.tip = niz(X)
            if (!type.isArray()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjeri(<izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <izraz>.tip ~ int
            final Type ex = (Type) thirdChild.getAttribute(Attribute.TYPE);
            if (!ex.implicitConversion(new IntType())) {
                SemanticErrorReporter.report(node);
                return false;
            }

            final Type under = type.fromArray();
            node.addAttribute(Attribute.TYPE, under);
            node.addAttribute(Attribute.L_EXPRESSION, !under.isConst());
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
