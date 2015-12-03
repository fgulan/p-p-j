package hr.fer.zemris.ppj.lr1.parser;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.ppj.Lexeme;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.grammar.ProductionParser;
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

    /**
     * Class constructor, specifies the actions of the parser.
     *
     * @param table
     *            the table of actions.
     * @param syncSymbols
     *            sync symbols of the parser.
     * @since alpha
     */
    public LR1Parser(final LR1ParserTable table, final List<Symbol> syncSymbols) {
        this.table = table;
        this.syncSymbols = syncSymbols;
    }

    /**
     * Analyzes the list of lexemes.
     *
     * @param lexemes
     *            the lexemes.
     * @param errorStream
     *            the error output stream.
     * @return root of the generative tree.
     * @since alpha
     */
    public Node analyze(final List<Lexeme> lexemes, final PrintStream errorStream) {
        final Stack<String> stack = new Stack<>();
        stack.push("0");

        final Stack<Node> tree = new Stack<>();
        for (int i = 0; i < lexemes.size();) { // Increment expression is left out on purpose
            final Lexeme lexeme = lexemes.get(i);
            final String state = stack.peek();
            final String symbol = lexeme.type();

            final ParserAction action = table.getAction(state, symbol);
            errorStream.println(state + " " + symbol + " " + action.toString());
            if (action instanceof ShiftAction) {
                // Shift the matched terminal t onto the parse stack and scan the next input symbol into the lookahead
                // buffer.
                // Push next state n onto the parse stack as the new current state.
                final ShiftAction shift = (ShiftAction) action;
                stack.push(symbol);
                tree.push(new Node(lexeme.toString()));
                stack.push(shift.stateID());
                i++;
            }
            else if (action instanceof ReduceAction) {
                // Apply grammar rule rm: Lhs -> S1 S2 ... SL
                // Remove the matched topmost L symbols (and parse trees and associated state numbers) from the parse
                // stack.
                // This exposes a prior state p that was expecting an instance of the Lhs symbol.
                // Join the L parse trees together as one parse tree with new root symbol Lhs.
                // Lookup the next state n from row p and column Lhs of the LHS Goto table.
                // Push the symbol and tree for Lhs onto the parse stack.
                // Push next state n onto the parse stack as the new current state.
                // The lookahead and input stream remain unchanged.
                final ReduceAction reduce = (ReduceAction) action;
                final Production production = reduce.production();

                if (production.isEpsilonProduction()) {
                    tree.push(new Node(production.leftSide().toString(), Arrays.asList(new Node("$"))));
                }
                else {
                    final List<Node> children = new ArrayList<>();
                    for (int j = 0; j < (production.rightSide().size() * 2); j++) {
                        stack.pop();
                        if ((j % 2) == 0) {
                            children.add(0, tree.pop());
                        }
                    }
                    tree.push(new Node(production.leftSide().toString(), children));
                }

                final PutAction put = (PutAction) table.getAction(stack.peek(), production.leftSide().toString());
                stack.push(production.leftSide().toString());
                stack.push(put.stateID());
            }
            else if (action instanceof AcceptAction) {
                // Lookahead t is the eof marker. End of parsing. If the state stack contains just the start state
                // report success. Otherwise, report a syntax error.
                if ("#".equals(symbol)) {
                    return tree.pop();
                }
                break; // Shouldn't happen.
            }
            else if (action instanceof RejectAction) {
                // Report a syntax error. The parser ends, or attempts some recovery.
                errorStream.println("Error on line: " + lexeme.lineNumber() + ", found: (" + lexeme + "), expected: "
                        + table.symbolsWithActionsFromState(state));

                // Find next sync symbol
                while ((i < (lexemes.size() - 1))
                        && !syncSymbols.contains(ProductionParser.parseSymbol(lexemes.get(i).type()))) {
                    i++;
                }

                final String syncSymbol = lexemes.get(i).type().toString();
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
        return new Node("Error");
    }

}
