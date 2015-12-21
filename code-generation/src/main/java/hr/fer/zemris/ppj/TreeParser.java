package hr.fer.zemris.ppj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import hr.fer.zemris.ppj.identifier.table.IdentifierTable;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.semantic.rule.declarations.Declaration;
import hr.fer.zemris.ppj.semantic.rule.declarations.DeclarationList;
import hr.fer.zemris.ppj.semantic.rule.declarations.DeclaratorInitializationList;
import hr.fer.zemris.ppj.semantic.rule.declarations.DirectDeclarator;
import hr.fer.zemris.ppj.semantic.rule.declarations.InitializationDeclarator;
import hr.fer.zemris.ppj.semantic.rule.declarations.Initializator;
import hr.fer.zemris.ppj.semantic.rule.declarations.OuterDeclaration;
import hr.fer.zemris.ppj.semantic.rule.declarations.ParameterDeclaration;
import hr.fer.zemris.ppj.semantic.rule.definitions.FunctionDefinition;
import hr.fer.zemris.ppj.semantic.rule.definitions.ParameterList;
import hr.fer.zemris.ppj.semantic.rule.expressions.AdditiveExpression;
import hr.fer.zemris.ppj.semantic.rule.expressions.ArgumentList;
import hr.fer.zemris.ppj.semantic.rule.expressions.AssignExpression;
import hr.fer.zemris.ppj.semantic.rule.expressions.AssignExpressionList;
import hr.fer.zemris.ppj.semantic.rule.expressions.BinaryAndExpression;
import hr.fer.zemris.ppj.semantic.rule.expressions.BinaryOrExpression;
import hr.fer.zemris.ppj.semantic.rule.expressions.BinaryXorExpression;
import hr.fer.zemris.ppj.semantic.rule.expressions.CastExpression;
import hr.fer.zemris.ppj.semantic.rule.expressions.EqualityExpression;
import hr.fer.zemris.ppj.semantic.rule.expressions.Expression;
import hr.fer.zemris.ppj.semantic.rule.expressions.LogicalAndExpression;
import hr.fer.zemris.ppj.semantic.rule.expressions.LogicalOrExpression;
import hr.fer.zemris.ppj.semantic.rule.expressions.MultiplicativeExpression;
import hr.fer.zemris.ppj.semantic.rule.expressions.PostfixExpression;
import hr.fer.zemris.ppj.semantic.rule.expressions.PrimaryExpression;
import hr.fer.zemris.ppj.semantic.rule.expressions.RelationalExpression;
import hr.fer.zemris.ppj.semantic.rule.expressions.TypeName;
import hr.fer.zemris.ppj.semantic.rule.expressions.TypeSpecifier;
import hr.fer.zemris.ppj.semantic.rule.expressions.UnaryExpression;
import hr.fer.zemris.ppj.semantic.rule.expressions.UnaryOperator;
import hr.fer.zemris.ppj.semantic.rule.instuctions.BranchInstruction;
import hr.fer.zemris.ppj.semantic.rule.instuctions.CompoundInstruction;
import hr.fer.zemris.ppj.semantic.rule.instuctions.ExpressionInstruction;
import hr.fer.zemris.ppj.semantic.rule.instuctions.Instruction;
import hr.fer.zemris.ppj.semantic.rule.instuctions.InstructionList;
import hr.fer.zemris.ppj.semantic.rule.instuctions.JumpInstruction;
import hr.fer.zemris.ppj.semantic.rule.instuctions.LoopInstruction;
import hr.fer.zemris.ppj.semantic.rule.misc.DefinedFunctions;
import hr.fer.zemris.ppj.semantic.rule.misc.MainFunction;
import hr.fer.zemris.ppj.semantic.rule.misc.TranslationUnit;
import hr.fer.zemris.ppj.semantic.rule.terminals.Char;
import hr.fer.zemris.ppj.semantic.rule.terminals.ConstCharArray;
import hr.fer.zemris.ppj.semantic.rule.terminals.Identifier;
import hr.fer.zemris.ppj.semantic.rule.terminals.Int;

/**
 * <code>TreeParser</code> is a parser for generative trees.
 *
 * @author Matea Sabolic
 *
 * @version 1.0
 */
public class TreeParser {

    private static final Map<String, Checker> checkers = new HashMap<>();

    static {
        // declarations
        checkers.put(Declaration.HR_NAME, new Declaration());
        checkers.put(DeclarationList.HR_NAME, new DeclarationList());
        checkers.put(DeclaratorInitializationList.HR_NAME, new DeclaratorInitializationList());
        checkers.put(DirectDeclarator.HR_NAME, new DirectDeclarator());
        checkers.put(InitializationDeclarator.HR_NAME, new InitializationDeclarator());
        checkers.put(Initializator.HR_NAME, new Initializator());
        checkers.put(OuterDeclaration.HR_NAME, new OuterDeclaration());
        checkers.put(ParameterDeclaration.HR_NAME, new ParameterDeclaration());

        // definitions
        checkers.put(FunctionDefinition.HR_NAME, new FunctionDefinition());
        checkers.put(ParameterList.HR_NAME, new ParameterList());

        // expressions
        checkers.put(AdditiveExpression.HR_NAME, new AdditiveExpression());
        checkers.put(ArgumentList.HR_NAME, new ArgumentList());
        checkers.put(AssignExpression.HR_NAME, new AssignExpression());
        checkers.put(AssignExpressionList.HR_NAME, new AssignExpressionList());
        checkers.put(BinaryAndExpression.HR_NAME, new BinaryAndExpression());
        checkers.put(BinaryOrExpression.HR_NAME, new BinaryOrExpression());
        checkers.put(BinaryXorExpression.HR_NAME, new BinaryXorExpression());
        checkers.put(CastExpression.HR_NAME, new CastExpression());
        checkers.put(EqualityExpression.HR_NAME, new EqualityExpression());
        checkers.put(Expression.HR_NAME, new Expression());
        checkers.put(LogicalAndExpression.HR_NAME, new LogicalAndExpression());
        checkers.put(LogicalOrExpression.HR_NAME, new LogicalOrExpression());
        checkers.put(MultiplicativeExpression.HR_NAME, new MultiplicativeExpression());
        checkers.put(PostfixExpression.HR_NAME, new PostfixExpression());
        checkers.put(PrimaryExpression.HR_NAME, new PrimaryExpression());
        checkers.put(RelationalExpression.HR_NAME, new RelationalExpression());
        checkers.put(TypeName.HR_NAME, new TypeName());
        checkers.put(TypeSpecifier.HR_NAME, new TypeSpecifier());
        checkers.put(UnaryExpression.HR_NAME, new UnaryExpression());
        checkers.put(UnaryOperator.HR_NAME, new UnaryOperator());

        // instructions
        checkers.put(BranchInstruction.HR_NAME, new BranchInstruction());
        checkers.put(CompoundInstruction.HR_NAME, new CompoundInstruction());
        checkers.put(ExpressionInstruction.HR_NAME, new ExpressionInstruction());
        checkers.put(Instruction.HR_NAME, new Instruction());
        checkers.put(InstructionList.HR_NAME, new InstructionList());
        checkers.put(JumpInstruction.HR_NAME, new JumpInstruction());
        checkers.put(LoopInstruction.HR_NAME, new LoopInstruction());

        // misc
        checkers.put(DefinedFunctions.HR_NAME, new DefinedFunctions());
        checkers.put(MainFunction.HR_NAME, new MainFunction());
        checkers.put(TranslationUnit.HR_NAME, new TranslationUnit());

        // terminals
        checkers.put(Char.HR_NAME, new Char());
        checkers.put(ConstCharArray.HR_NAME, new ConstCharArray());
        checkers.put(Identifier.HR_NAME, new Identifier());
        checkers.put(Int.HR_NAME, new Int());
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
                final Checker checker = checkers.get(line);
                child = new Node(line, new ArrayList<Node>(), parent, new HashMap<Attribute, Object>(), identifierTable,
                        checker);
                stack.push(child);
            }
            else {
                // terminal node
                final String[] split = line.split(" ", 3);
                final String name = split[0];
                final int lineNumber = Integer.valueOf(split[1]);
                final String value = split[2];
                final Checker checker = checkers.get(name);
                child = new Node(name, new ArrayList<Node>(), parent, new HashMap<Attribute, Object>(), identifierTable,
                        checker);
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
