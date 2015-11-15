package hr.fer.zemris.ppj.lr1.parser;

import java.io.PrintStream;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.ppj.Lexeme;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.grammar.interfaces.Symbol;
import hr.fer.zemris.ppj.lr1.parser.actions.AcceptAction;
import hr.fer.zemris.ppj.lr1.parser.actions.ParserAction;
import hr.fer.zemris.ppj.lr1.parser.actions.PutAction;
import hr.fer.zemris.ppj.lr1.parser.actions.ReduceAction;
import hr.fer.zemris.ppj.lr1.parser.actions.RejectAction;
import hr.fer.zemris.ppj.lr1.parser.actions.ShiftAction;

/**
 * <code>LR1Parser</code> represents a canonical LR(1) parser.
 *
 * @author Matea Sabolic
 *
 * @version alpha
 */
public class LR1Parser {

    private final LR1ParserTable table;
    private final List<Symbol> syncSymbols;
    private final Symbol startSymbol;

    /**
     * Class constructor, specifies the actions of the parser.
     *
     * @param table
     *            the table of actions.
     * @param syncSymbols
     *            sync symbols of the parser.
     * @since alpha
     */
    public LR1Parser(final LR1ParserTable table, final List<Symbol> syncSymbols, final Symbol startSymbol) {
        this.table = table;
        this.syncSymbols = syncSymbols;
        this.startSymbol = startSymbol;
    }

    /**
     * Analyzes the list of lexemes.
     *
     * @param lexemes
     *            the lexemes.
     * @param outputStream
     *            the output stream.
     * @param errorStream
     *            the error output stream.
     * @since alpha
     */
    public Node analyze(List<Lexeme> lexemes, PrintStream outputStream, PrintStream errorStream) {
        Stack<String> stack = new Stack<>();
        stack.push("0");

        Stack<Node> tree = new Stack<>();

        for (int i = 0; i < lexemes.size();) { // Increment expression is left out on purpose
            Lexeme lexeme = lexemes.get(i);
            String symbol = lexeme.type().toString();
            String state = stack.peek();

            ParserAction action = table.getAction(state, symbol);
            errorStream.println(state + " " + symbol + " " + action.toString());
            if (action instanceof ShiftAction) {
                ShiftAction shift = (ShiftAction) action;
                stack.push(symbol);
                stack.push(shift.stateID());

                tree.push(new Node(lexeme.toString(), null));
                i++;
            }
            else if (action instanceof ReduceAction) {
                ReduceAction reduce = (ReduceAction) action;
                Production production = reduce.production();

                Node node = new Node(production.leftSide().toString(), null);

                if (production.isEpsilonProduction()) {
                    node.addChild(new Node("$", null));
                }
                else {
                    Stack<Node> temp = new Stack<>();
                    for (int j = 0; j < (production.rightSide().size() * 2); j++) {
                        stack.pop();
                        if ((j % 2) == 0) {
                            temp.push(tree.pop());
                        }
                    }
                    int size = temp.size();
                    for (int j = 0; j < size; j++) {
                        node.addChild(temp.pop());
                    }
                }

                tree.push(node);

                PutAction put = (PutAction) table.getAction(stack.peek(), production.leftSide().toString());
                stack.push(production.leftSide().toString());
                stack.push(put.stateID());
            }
            else if (action instanceof AcceptAction) {
                if ("#".equals(symbol)) {
                    return tree.pop();
                }
                break; // Shouldn't happen.
            }
            else if (action instanceof RejectAction) {
                errorStream.println("Error on line: " + lexeme.lineNumber() + ", found: (" + symbol + "), expected: "
                        + table.symbolsWithActionsFromState(state));

                // Find next sync symbol
                while ((i < (lexemes.size() - 1)) && !syncSymbols.contains(lexemes.get(i).value())) {
                    i++;
                }

                String syncSymbol = lexemes.get(i).type().toString();
                // Find first state with defined action for the sync symbol
                while (table.getAction(stack.peek(), syncSymbol) instanceof RejectAction) {
                    stack.pop();
                    stack.pop();
                    tree.pop();
                }
            }
            else {
                errorStream.println("Unimplemented action.");
            }
        }
        return tree.pop();
    }

}
