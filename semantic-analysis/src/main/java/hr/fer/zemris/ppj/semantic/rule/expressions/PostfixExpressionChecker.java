package hr.fer.zemris.ppj.semantic.rule.expressions;

import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.SemanticErrorReporter;
import hr.fer.zemris.ppj.VariableType;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>PostfixExpressionChecker</code> is a checker for postfix expression.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class PostfixExpressionChecker implements Checker {

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
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        Node firstChild = node.getChild(0);
        String firstSymbol = firstChild.name();

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

        Node secondChild = node.getChild(1);
        String secondSymbol = secondChild.name();
        // <postfiks_izraz> ::= <postfiks_izraz> OP_INC
        if ("<postfiks_izraz".equals(firstSymbol) && "OP_INC".equals(secondSymbol)) {
            // IDENTICNO KAO I FUNKCIJA ISPOD, U SLUCAJU BUGA ISPRAVITI OBJE

            // 1. provjeri(<postfiks_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <postfiks_izraz>.l-izraz = 1 && <postfiks_izraz>.tip ~ int
            if (!(firstChild.getAttribute(Attribute.L_EXPRESSION).equals(true) && VariableType
                    .implicitConversion((VariableType) firstChild.getAttribute(Attribute.TYPE), VariableType.INT))) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, VariableType.INT);
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <postfiks_izraz> ::= <postfiks_izraz> OP_DEC
        if ("<postfiks_izraz".equals(firstSymbol) && "OP_DEC".equals(secondSymbol)) {

            // 1. provjeri(<postfiks_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <postfiks_izraz>.l-izraz = 1 && <postfiks_izraz>.tip ~ int
            if (!(firstChild.getAttribute(Attribute.L_EXPRESSION).equals(true) && VariableType
                    .implicitConversion((VariableType) firstChild.getAttribute(Attribute.TYPE), VariableType.INT))) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, VariableType.INT);
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        Node thirdChild = node.getChild(2);
        String thirdSymbol = thirdChild.name();
        // <postfiks_izraz> ::= <postfiks_izraz> L_ZAGRADA D_ZAGRADA
        if ("<postfiks_izraz>".equals(firstSymbol) && "L_ZAGRADA".equals(secondSymbol)
                && "D_ZAGRADA".equals(thirdSymbol)) {

            // 1. provjeri(<postfiks_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <postfiks_izraz>.tip = funkcija(void -> pov)
            String identificator = (String) firstChild.getChild(0).getChild(0).getAttribute(Attribute.VALUE);
            // TODO: provjera
            if (false) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, null);
            node.addAttribute(Attribute.L_EXPRESSION, false);
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
            String identificator = (String) firstChild.getChild(0).getChild(0).getAttribute(Attribute.VALUE);
            // TODO: provjera
            if (false) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 3. provjera implicitnih konverzija parametara
            // TODO: nabavi iz tablice simbola
            List<VariableType> declarationTypes = null;
            List<VariableType> callingArguments = (List<VariableType>) thirdChild.getAttribute(Attribute.TYPES);
            if (declarationTypes.size() != callingArguments.size()) {
                SemanticErrorReporter.report(node);
                return false;
            }
            for (int i = 0; i < declarationTypes.size(); i++) {
                if (!VariableType.implicitConversion(callingArguments.get(i), declarationTypes.get(i))) {
                    SemanticErrorReporter.report(node);
                    return false;
                }
            }

            node.addAttribute(Attribute.TYPE, null);
            node.addAttribute(Attribute.L_EXPRESSION, false);
            return true;
        }

        // <postfiks_izraz> ::= <postfiks_izraz> L_UGL_ZAGRADA <izraz> D_UGL_ZAGRADA
        if ("<postfiks_izraz>".equals(firstSymbol) && "L_UGL_ZAGRADA".equals(secondSymbol)
                && "<izraz>".equals(thirdSymbol)) {

            // TODO: dohvati iz tablice simbola
            VariableType type = VariableType.fromArrayType(null);

            // 1. provjeri(<postfiks_izraz>)
            if (!firstChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 2. <postfiks_izraz>.tip = niz(X)
            node.addAttribute(Attribute.TYPE, VariableType.toArrayType(type));

            // 3. provjeri(<izraz>)
            if (!thirdChild.check()) {
                SemanticErrorReporter.report(node);
                return false;
            }

            // 4. <izraz>.tip ~ int
            if (!VariableType.implicitConversion((VariableType) thirdChild.getAttribute(Attribute.TYPE),
                    VariableType.INT)) {
                SemanticErrorReporter.report(node);
                return false;
            }

            node.addAttribute(Attribute.TYPE, type);
            node.addAttribute(Attribute.L_EXPRESSION, !VariableType.isConst(type));
            return true;
        }

        System.err.println("Shold never happen");
        SemanticErrorReporter.report(node);
        return false;
    }

}
