package hr.fer.zemris.ppj.lexical.analysis.analyzer;

import java.io.PrintStream;
import java.util.Map;

public class LexicalAnalyzer {

    private Map<String, LexerState> states;
    private LexerState currentState;
    private String source;
    
    private int lineCounter = 1;
    private int startIndex = 0;
    private int lastIndex = -1;
    private int finishIndex = -1;
    
    public LexicalAnalyzer(Map<String, LexerState> states, LexerState currentState, String source) {
        super();
        this.states = states;
        this.currentState = currentState;
        this.source = source;
    }
    
    
    
//    public void doLexing() {
//        LexerRule accRule = null;
//
//        while (finishIndex < input.length() - 1) {
//            while (currentState.isAnyAlive()) {
//                ++finishIndex;
//                LexerRule tmpRule = currentState.getAccepted();
//                if (tmpRule == null) {
//                } else {
//                    accRule = tmpRule;
//                    lastIndex = finishIndex - 1;
//                }
//                if (finishIndex < input.length()) {
//                    currentState.pushCharToAutomatons(String.valueOf(input.charAt(finishIndex)));
//                } else {
//                    break;
//                }
//            }
//
//            if (accRule == null) {
//                finishIndex = startIndex++;
//                currentState.resetAutomatons();
//            } else {
//                finishIndex = lastIndex;
//                accRule.doActions(this);
//                accRule = null;
//                currentState.resetAutomatons();
//            }
//        }
//    }
    
    public void analyze() {
        LexerRule activeRule = null;
        while (finishIndex < source.length()) {
            if (currentState.isAlive()) {
                finishIndex++;
                LexerRule tempRule = currentState.getActiveRule();
                if (tempRule != null) {
                    lastIndex = finishIndex - 1;
                    activeRule = tempRule;
                }
                
                if (finishIndex < source.length()) {
                    currentState.apply(source.charAt(finishIndex));
                } else {
                    break;
                }
            }
            
            if (activeRule != null) {
                finishIndex = lastIndex;
                activeRule.execute(this);
                activeRule = null;
                currentState.resetAutomatons();
            } else {
                finishIndex = startIndex++;
                currentState.resetAutomatons();
            }
        }
    }

    public LexerState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(LexerState currentState) {
        this.currentState = currentState;
    }
    
    public void setCurrentStateFromName(String stateName) {
        currentState = states.get(stateName);
    }

    public int getLineCounter() {
        return lineCounter;
    }

    public void incrementLineCounter() {
        this.lineCounter++;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public int getFinishIndex() {
        return finishIndex;
    }

    public void setFinishIndex(int finishIndex) {
        this.finishIndex = finishIndex;
    }
    
    public PrintStream getOutput() {
        //TODO 
        return null;
    }
    
    public String getCurrentPhrase() {
        return source.substring(startIndex, finishIndex);
    }
}
