package hr.fer.zemris.ppj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import hr.fer.zemris.ppj.identifier.table.IdentifierTable;
import hr.fer.zemris.ppj.interfaces.Manipulator;
import hr.fer.zemris.ppj.manipulators.declarations.DeclarationListManipulator;
import hr.fer.zemris.ppj.manipulators.declarations.DeclarationManipulator;
import hr.fer.zemris.ppj.manipulators.declarations.DeclaratorInitializationListManipulator;
import hr.fer.zemris.ppj.manipulators.declarations.DirectDeclaratorManipulator;
import hr.fer.zemris.ppj.manipulators.declarations.InitializationDeclaratorManipulator;
import hr.fer.zemris.ppj.manipulators.declarations.InitializatorManipulator;
import hr.fer.zemris.ppj.manipulators.declarations.OuterDeclarationManipulator;
import hr.fer.zemris.ppj.manipulators.declarations.ParameterDeclarationManipulator;
import hr.fer.zemris.ppj.manipulators.definitions.FunctionDefinitionManipulator;
import hr.fer.zemris.ppj.manipulators.definitions.ParameterListManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.AdditiveExpressionManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.ArgumentListManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.AssignExpressionListManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.AssignExpressionManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.BinaryAndExpressionManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.BinaryOrExpressionManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.BinaryXorExpressionManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.CastExpressionManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.EqualityExpressionManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.ExpressionManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.LogicalAndExpressionManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.LogicalOrExpressionManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.MultiplicativeExpressionManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.PostfixExpressionManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.PrimaryExpressionManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.RelationalExpressionManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.TypeNameManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.TypeSpecifierManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.UnaryExpressionManipulator;
import hr.fer.zemris.ppj.manipulators.expressions.UnaryOperatorManipulator;
import hr.fer.zemris.ppj.manipulators.instuctions.BranchInstructionManipulator;
import hr.fer.zemris.ppj.manipulators.instuctions.CompoundInstructionManipulator;
import hr.fer.zemris.ppj.manipulators.instuctions.ExpressionInstructionManipulator;
import hr.fer.zemris.ppj.manipulators.instuctions.InstructionManipulator;
import hr.fer.zemris.ppj.manipulators.instuctions.InstructionListManipulator;
import hr.fer.zemris.ppj.manipulators.instuctions.JumpInstructionManipulator;
import hr.fer.zemris.ppj.manipulators.instuctions.LoopInstructionManipulator;
import hr.fer.zemris.ppj.manipulators.misc.DefinedFunctionsManipulator;
import hr.fer.zemris.ppj.manipulators.misc.MainFunctionManipulator;
import hr.fer.zemris.ppj.manipulators.misc.TranslationUnitManipulator;
import hr.fer.zemris.ppj.manipulators.terminals.CharManipulator;
import hr.fer.zemris.ppj.manipulators.terminals.ConstCharArrayManipulator;
import hr.fer.zemris.ppj.manipulators.terminals.IdentifierManipulator;
import hr.fer.zemris.ppj.manipulators.terminals.IntManipulator;

/**
 * <code>TreeParser</code> is a parser for generative trees.
 *
 * @author Matea Sabolic
 *
 * @version 1.0
 */
public class TreeParser {

    private static final Map<String, Manipulator> manipulators = new HashMap<>();

    static {
        // declarations
        manipulators.put(DeclarationManipulator.HR_NAME, new DeclarationManipulator());
        manipulators.put(DeclarationListManipulator.HR_NAME, new DeclarationListManipulator());
        manipulators.put(DeclaratorInitializationListManipulator.HR_NAME,
                new DeclaratorInitializationListManipulator());
        manipulators.put(DirectDeclaratorManipulator.HR_NAME, new DirectDeclaratorManipulator());
        manipulators.put(InitializationDeclaratorManipulator.HR_NAME, new InitializationDeclaratorManipulator());
        manipulators.put(InitializatorManipulator.HR_NAME, new InitializatorManipulator());
        manipulators.put(OuterDeclarationManipulator.HR_NAME, new OuterDeclarationManipulator());
        manipulators.put(ParameterDeclarationManipulator.HR_NAME, new ParameterDeclarationManipulator());

        // definitions
        manipulators.put(FunctionDefinitionManipulator.HR_NAME, new FunctionDefinitionManipulator());
        manipulators.put(ParameterListManipulator.HR_NAME, new ParameterListManipulator());

        // expressions
        manipulators.put(AdditiveExpressionManipulator.HR_NAME, new AdditiveExpressionManipulator());
        manipulators.put(ArgumentListManipulator.HR_NAME, new ArgumentListManipulator());
        manipulators.put(AssignExpressionManipulator.HR_NAME, new AssignExpressionManipulator());
        manipulators.put(AssignExpressionListManipulator.HR_NAME, new AssignExpressionListManipulator());
        manipulators.put(BinaryAndExpressionManipulator.HR_NAME, new BinaryAndExpressionManipulator());
        manipulators.put(BinaryOrExpressionManipulator.HR_NAME, new BinaryOrExpressionManipulator());
        manipulators.put(BinaryXorExpressionManipulator.HR_NAME, new BinaryXorExpressionManipulator());
        manipulators.put(CastExpressionManipulator.HR_NAME, new CastExpressionManipulator());
        manipulators.put(EqualityExpressionManipulator.HR_NAME, new EqualityExpressionManipulator());
        manipulators.put(ExpressionManipulator.HR_NAME, new ExpressionManipulator());
        manipulators.put(LogicalAndExpressionManipulator.HR_NAME, new LogicalAndExpressionManipulator());
        manipulators.put(LogicalOrExpressionManipulator.HR_NAME, new LogicalOrExpressionManipulator());
        manipulators.put(MultiplicativeExpressionManipulator.HR_NAME, new MultiplicativeExpressionManipulator());
        manipulators.put(PostfixExpressionManipulator.HR_NAME, new PostfixExpressionManipulator());
        manipulators.put(PrimaryExpressionManipulator.HR_NAME, new PrimaryExpressionManipulator());
        manipulators.put(RelationalExpressionManipulator.HR_NAME, new RelationalExpressionManipulator());
        manipulators.put(TypeNameManipulator.HR_NAME, new TypeNameManipulator());
        manipulators.put(TypeSpecifierManipulator.HR_NAME, new TypeSpecifierManipulator());
        manipulators.put(UnaryExpressionManipulator.HR_NAME, new UnaryExpressionManipulator());
        manipulators.put(UnaryOperatorManipulator.HR_NAME, new UnaryOperatorManipulator());

        // instructions
        manipulators.put(BranchInstructionManipulator.HR_NAME, new BranchInstructionManipulator());
        manipulators.put(CompoundInstructionManipulator.HR_NAME, new CompoundInstructionManipulator());
        manipulators.put(ExpressionInstructionManipulator.HR_NAME, new ExpressionInstructionManipulator());
        manipulators.put(InstructionManipulator.HR_NAME, new InstructionManipulator());
        manipulators.put(InstructionListManipulator.HR_NAME, new InstructionListManipulator());
        manipulators.put(JumpInstructionManipulator.HR_NAME, new JumpInstructionManipulator());
        manipulators.put(LoopInstructionManipulator.HR_NAME, new LoopInstructionManipulator());

        // misc
        manipulators.put(DefinedFunctionsManipulator.HR_NAME, new DefinedFunctionsManipulator());
        manipulators.put(MainFunctionManipulator.HR_NAME, new MainFunctionManipulator());
        manipulators.put(TranslationUnitManipulator.HR_NAME, new TranslationUnitManipulator());

        // terminals
        manipulators.put(CharManipulator.HR_NAME, new CharManipulator());
        manipulators.put(ConstCharArrayManipulator.HR_NAME, new ConstCharArrayManipulator());
        manipulators.put(IdentifierManipulator.HR_NAME, new IdentifierManipulator());
        manipulators.put(IntManipulator.HR_NAME, new IntManipulator());
    }

    /**
     * Parses a tree from the defined input stream.
     *
     * @param scanner
     *            scanner.
     * @return parsed tree.
     * @since 1.0
     */
    public static Node parse(final Scanner scanner) {
        final Stack<Node> stack = new Stack<>();
        final Stack<IdentifierTable> identifierTableStack = new Stack<>();
        identifierTableStack.push(IdentifierTable.GLOBAL_SCOPE);

        String line = null;
        int depth = 0;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line == null) {
                break;
            }

            final int lineDepth = countDepth(line);
            if (depth > lineDepth) {
                for (int i = 0; i < (depth - lineDepth); i++) {
                    stack.pop();
                }
            }
            depth = lineDepth;
            final Node parent = stack.isEmpty() ? null : stack.peek();

            Node child = null;

            line = line.trim();

            if (parent != null) {
                if ("<definicija_funkcije>".equals(line)) {
                    identifierTableStack.push(new IdentifierTable(identifierTableStack.peek()));
                }
                else if ("<slozena_naredba>".equals(line) && !"<definicija_funkcije>".equals(parent.name())) {
                    identifierTableStack.push(new IdentifierTable(identifierTableStack.peek()));
                }
            }
            final IdentifierTable identifierTable = identifierTableStack.peek();

            if (line.startsWith("<")) {
                // nonterminal node
                final Manipulator manipulator = manipulators.get(line);
                child = new Node(line, new ArrayList<Node>(), parent, new HashMap<Attribute, Object>(), identifierTable,
                        manipulator);
                stack.push(child);
            }
            else {
                // terminal node
                final String[] split = line.split(" ", 3);
                final String name = split[0];
                final int lineNumber = Integer.valueOf(split[1]);
                final String value = split[2];
                final Manipulator manipulator = manipulators.get(name);
                child = new Node(name, new ArrayList<Node>(), parent, new HashMap<Attribute, Object>(), identifierTable,
                        manipulator);
                child.addAttribute(Attribute.LINE_NUMBER, lineNumber);
                child.addAttribute(Attribute.VALUE, value);
            }

            if (parent != null) {
                parent.addChild(child);

                if ("<definicija_funkcije>".equals(parent.name())) {
                    if ("D_VIT_ZAGRADA".equals(child.name())) {
                        identifierTableStack.pop();
                    }
                }
                else if ("<slozena_naredba>".equals(parent.name())) {
                    if ("D_VIT_ZAGRADA".equals(child.name())) {
                        identifierTableStack.pop();
                    }
                }
            }
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
