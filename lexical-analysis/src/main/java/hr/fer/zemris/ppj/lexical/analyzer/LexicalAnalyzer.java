package hr.fer.zemris.ppj.lexical.analyzer;

import java.io.PrintStream;
import java.util.Map;

public class LexicalAnalyzer {

    private final Map<String, LexerState> states;
    private LexerState currentState;
    private final String source;
    private final PrintStream printStream;

    private int lineCounter = 1;
    private int startIndex = 0;
    private int lastIndex = -1;
    private int finishIndex = -1;

    public LexicalAnalyzer(final Map<String, LexerState> states, final LexerState currentState, final String source,
            final PrintStream printStream) {
        super();
        this.states = states;
        this.currentState = currentState;
        this.source = source;
        ;
        this.printStream = printStream;
    }

    public void analyze() {
        LexerRule activeRule = null;
        while (finishIndex < source.length()) {
            while (currentState.isAlive()) {
                finishIndex++;
                final LexerRule tempRule = currentState.getActiveRule();
                if (tempRule != null) {
                    lastIndex = finishIndex - 1;
                    activeRule = tempRule;
                }

                if (finishIndex < source.length()) {
                    // TODO sugavo je ovako, morat cu jos to rijesit
                    currentState.apply(String.valueOf(source.charAt(finishIndex)));
                }
                else {
                    break;
                }
            }

            if (activeRule != null) {
                finishIndex = lastIndex;
                activeRule.execute(this);
                activeRule = null;
                currentState.resetAutomatons();
            }
            else {
                finishIndex = startIndex++;
                currentState.resetAutomatons();
            }
        }
    }

    public LexerState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(final LexerState currentState) {
        this.currentState = currentState;
    }

    public void setCurrentStateFromName(final String stateName) {
        currentState = states.get(stateName);
    }

    public int getLineCounter() {
        return lineCounter;
    }

    public void incrementLineCounter() {
        lineCounter++;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(final int startIndex) {
        this.startIndex = startIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(final int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public int getFinishIndex() {
        return finishIndex;
    }

    public void setFinishIndex(final int finishIndex) {
        this.finishIndex = finishIndex;
    }

    public PrintStream getOutput() {
        return printStream;
    }

    public String getCurrentPhrase() {
        return source.substring(startIndex, finishIndex + 1);
    }
}