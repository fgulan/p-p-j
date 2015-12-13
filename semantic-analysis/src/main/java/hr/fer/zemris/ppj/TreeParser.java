package hr.fer.zemris.ppj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.semantic.rule.declarations.DeclarationChecker;
import hr.fer.zemris.ppj.semantic.rule.declarations.DeclarationListChecker;
import hr.fer.zemris.ppj.semantic.rule.declarations.DeclaratorInitializationListChecker;
import hr.fer.zemris.ppj.semantic.rule.declarations.DirectDeclaratorChecker;
import hr.fer.zemris.ppj.semantic.rule.declarations.InitializationDeclaratorChecker;
import hr.fer.zemris.ppj.semantic.rule.declarations.InitializatorChecker;
import hr.fer.zemris.ppj.semantic.rule.declarations.OuterDeclarationChecker;
import hr.fer.zemris.ppj.semantic.rule.declarations.ParameterDeclarationChecker;
import hr.fer.zemris.ppj.semantic.rule.definitions.FunctionDefinitionChecker;
import hr.fer.zemris.ppj.semantic.rule.definitions.ParameterListChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.AdditiveExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.ArgumentListChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.AssignExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.AssignExpressionListChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.BinaryAndExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.BinaryOrExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.BinaryXorExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.CastExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.EqualityExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.ExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.LogicalAndExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.LogicalOrExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.MultiplicativeExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.PostfixExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.PrimaryExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.RelationalExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.TypeNameChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.TypeSpecifierChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.UnaryExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.UnaryOperatorChecker;
import hr.fer.zemris.ppj.semantic.rule.instuctions.BranchInstructionChecker;
import hr.fer.zemris.ppj.semantic.rule.instuctions.CompoundInstructionChecker;
import hr.fer.zemris.ppj.semantic.rule.instuctions.ExpressionInstructionChecker;
import hr.fer.zemris.ppj.semantic.rule.instuctions.InstructionChecker;
import hr.fer.zemris.ppj.semantic.rule.instuctions.InstructionListChecker;
import hr.fer.zemris.ppj.semantic.rule.instuctions.JumpInstructionChecker;
import hr.fer.zemris.ppj.semantic.rule.instuctions.LoopInstructionChecker;
import hr.fer.zemris.ppj.semantic.rule.misc.DefinedFunctionsChecker;
import hr.fer.zemris.ppj.semantic.rule.misc.MainFunctionChecker;
import hr.fer.zemris.ppj.semantic.rule.misc.TranslationUnitChecker;

/**
 * <code>TreeParser</code> is a parser for generative trees.
 *
 * @author Matea Sabolic
 *
 * @version alpha
 */
public class TreeParser {

    private static final Map<String, Checker> checkers = new HashMap<>();

    static {
        // declarations
        checkers.put(DeclarationChecker.HR_NAME, new DeclarationChecker());
        checkers.put(DeclarationListChecker.HR_NAME, new DeclarationListChecker());
        checkers.put(DeclaratorInitializationListChecker.HR_NAME, new DeclaratorInitializationListChecker());
        checkers.put(DirectDeclaratorChecker.HR_NAME, new DirectDeclaratorChecker());
        checkers.put(InitializationDeclaratorChecker.HR_NAME, new InitializationDeclaratorChecker());
        checkers.put(InitializatorChecker.HR_NAME, new InitializatorChecker());
        checkers.put(OuterDeclarationChecker.HR_NAME, new OuterDeclarationChecker());
        checkers.put(ParameterDeclarationChecker.HR_NAME, new ParameterDeclarationChecker());

        // definitions
        checkers.put(FunctionDefinitionChecker.HR_NAME, new FunctionDefinitionChecker());
        checkers.put(ParameterListChecker.HR_NAME, new ParameterListChecker());

        // expressions
        checkers.put(AdditiveExpressionChecker.HR_NAME, new AdditiveExpressionChecker());
        checkers.put(ArgumentListChecker.HR_NAME, new ArgumentListChecker());
        checkers.put(AssignExpressionChecker.HR_NAME, new AssignExpressionChecker());
        checkers.put(AssignExpressionListChecker.HR_NAME, new AssignExpressionListChecker());
        checkers.put(BinaryAndExpressionChecker.HR_NAME, new BinaryAndExpressionChecker());
        checkers.put(BinaryOrExpressionChecker.HR_NAME, new BinaryOrExpressionChecker());
        checkers.put(BinaryXorExpressionChecker.HR_NAME, new BinaryXorExpressionChecker());
        checkers.put(CastExpressionChecker.HR_NAME, new CastExpressionChecker());
        checkers.put(EqualityExpressionChecker.HR_NAME, new EqualityExpressionChecker());
        checkers.put(ExpressionChecker.HR_NAME, new ExpressionChecker());
        checkers.put(LogicalAndExpressionChecker.HR_NAME, new LogicalAndExpressionChecker());
        checkers.put(LogicalOrExpressionChecker.HR_NAME, new LogicalOrExpressionChecker());
        checkers.put(MultiplicativeExpressionChecker.HR_NAME, new MultiplicativeExpressionChecker());
        checkers.put(PostfixExpressionChecker.HR_NAME, new PostfixExpressionChecker());
        checkers.put(PrimaryExpressionChecker.HR_NAME, new PrimaryExpressionChecker());
        checkers.put(RelationalExpressionChecker.HR_NAME, new RelationalExpressionChecker());
        checkers.put(TypeNameChecker.HR_NAME, new TypeNameChecker());
        checkers.put(TypeSpecifierChecker.HR_NAME, new TypeSpecifierChecker());
        checkers.put(UnaryExpressionChecker.HR_NAME, new UnaryExpressionChecker());
        checkers.put(UnaryOperatorChecker.HR_NAME, new UnaryOperatorChecker());

        // instructions
        checkers.put(BranchInstructionChecker.HR_NAME, new BranchInstructionChecker());
        checkers.put(CompoundInstructionChecker.HR_NAME, new CompoundInstructionChecker());
        checkers.put(ExpressionInstructionChecker.HR_NAME, new ExpressionInstructionChecker());
        checkers.put(InstructionChecker.HR_NAME, new InstructionChecker());
        checkers.put(InstructionListChecker.HR_NAME, new InstructionListChecker());
        checkers.put(JumpInstructionChecker.HR_NAME, new JumpInstructionChecker());
        checkers.put(LoopInstructionChecker.HR_NAME, new LoopInstructionChecker());

        // misc
        checkers.put(DefinedFunctionsChecker.HR_NAME, new DefinedFunctionsChecker());
        checkers.put(MainFunctionChecker.HR_NAME, new MainFunctionChecker());
        checkers.put(TranslationUnitChecker.HR_NAME, new TranslationUnitChecker());
    }

    /**
     * Parses a tree from the defined input stream.
     *
     * @param scanner
     *            scanner.
     * @return parsed tree.
     * @since alpha
     */
    public static Node parse(final Scanner scanner) {
        Stack<Node> stack = new Stack<>();
        String line = null;
        int depth = 0;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line == null) {
                break;
            }

            if (!stack.isEmpty() && !stack.peek().name().startsWith("<")) {
                stack.pop();
            }

            int lineDepth = countDepth(line);
            if (depth > lineDepth) {
                for (int i = 0; i < (depth - lineDepth); i++) {
                    stack.pop();
                }
            }
            depth = lineDepth;

            Node parent = stack.isEmpty() ? null : stack.peek();
            Checker checker = checkers.get(line.trim());
            Node child = new Node(line.trim(), new ArrayList<>(), parent, new HashMap<>(), checker);

            // TODO: process terminal symbols during parsing

            if (parent != null) {
                parent.addChild(child);
            }
            stack.push(child);
        }

        while (stack.size() > 1) {
            stack.pop();
        }

        return stack.pop();
    }

    private static int countDepth(final String line) {
        int i = 0;
        for (i = 0; (i < line.length()) && (line.charAt(i) == ' '); i++) {
            ;
        }
        return i;
    }

}
