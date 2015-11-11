package hr.fer.zemris.ppj.lr1.parser;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.ppj.Lexeme;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.grammar.interfaces.Symbol;
import hr.fer.zemris.ppj.lr1.parser.StackItem;
import hr.fer.zemris.ppj.lr1.parser.Node;
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

    /**
     * Class constructor, specifies the actions of the parser.
     *
     * @param table
     *            the table of actions.
     * @since alpha
     */
    public LR1Parser(final LR1ParserActionTable table) {
    	public ParserAction getAction(Symbol, String) { return micamaca; }
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
    public void analyze(List<Lexeme> lexemes, PrintStream outputStream, PrintStream errorStream) {
    	Stack<StackItem> stack = new Stack<StackItem>();
    	String currentState = new String();
    	StackItem currentItem = new StackItem();
    	Lexeme currentLexeme;
    	Symbol currentSymbol;
    	int reduce = 0;
    	List<Node> currentNodes = new ArrayList<Node>();
    	
    	
    	for (int i=0;i<lexemes.size();i++) {
    		if (stack.isEmpty()) currentState="0";
    		else {
    			currentItem=stack.pop();
    			currentState = currentItem.stateID;
    			stack.push(currentItem);
    		}
    		if (reduce==1) {
    			reduce=0;
    		}
    		else {
    			currentLexeme = lexemes.get(i);
    			currentSymbol = currentLexeme.value();
    		}
    		
    		
    		ParserAction action = table.getAction (currentSymbol, currentState);
    		
    		if (action instanceof PutAction) {
    			Lexeme newStackItem=new Lexeme(null, 0, currentSymbol);
    			currentItem.lexeme=newStackItem;
    			stack.push(currentItem);
    			currentItem.stateID=((PutAction) action).stateID();
    			stack.push(currentItem);
    		}
    		else if (action instanceof ReduceAction) {
    			i--;
    			Production currentProduction = ((ReduceAction) action).production();
    			currentSymbol = currentProduction.leftSide();
    			int x = currentProduction.rightSide().size();
    			boolean isEpsilon = currentProduction.isEpsilonProduction();
    			if (isEpsilon) x=0;
			
    			for (int j=0; j<2*x; j++) {
    				StackItem remove = stack.pop();
    			}

    			Node newNode = new Node();
    			Lexeme newLex = new Lexeme("", 0, currentSymbol);
    			newNode.lexeme = newLex;
    			List<Node> Children = new ArrayList<Node>();
    			
    			if (isEpsilon) {
    				Lexeme epsilon = new Lexeme("$", 0, null);
    				Node epsilonNode = new Node();
    				epsilonNode.parent=newNode;
    				epsilonNode.lexeme=epsilon;
    			}
    			for (int j=0; j<x; j++) {
    				Node temp = currentNodes.get(currentNodes.size()-1);
    				temp.parent = newNode;
    				Children.add(temp);
    				currentNodes.remove(currentNodes.size()-1);
    			}
    			currentNodes.add(newNode);
    		}
    		else if (action instanceof ShiftAction) {
    			currentItem.lexeme=currentLexeme;
    			stack.push(currentItem);
    			currentItem.stateID=((ShiftAction) action).stateID();
    			stack.push(currentItem);
    			
    			Node newNode = new Node();
    			newNode.lexeme=currentLexeme;
    			currentNodes.add(newNode);
    		}
    		else if (action instanceof RejectAction) {
    			outputStream.println("Dogodila se pogreska u "+currentLexeme.lineNumber()+". retku.");
    			
    		}
    		
    	}
    
    	
    	
    }
    

}
