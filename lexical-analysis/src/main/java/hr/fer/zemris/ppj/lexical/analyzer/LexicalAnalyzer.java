package hr.fer.zemris.ppj.lexical.analyzer;

import java.io.PrintStream;
import java.util.Map;

/**
 * <code>LexicalAnalyzer</code> represents a lexical analyzer.
 *
 * @author Filip Gulan
 *
 * @version 1.0
 */
public class LexicalAnalyzer {

    private final Map<String, LexerState> states;
    private LexerState currentState;

    private PrintStream printStream;
    private String source;

    private int lineCounter = 1;
    private int startIndex = 0;
    private int lastIndex = -1;
    private int finishIndex = -1;

    /**
     * Class constructor, specifies the states and the initial state of the analyzer.
     *
     * @param states
     *            the analyzer states.
     * @param initialState
     *            the initial analyzer state.
     * @since 1.0
     */
    public LexicalAnalyzer(final Map<String, LexerState> states, final LexerState initialState) {
        super();
        this.states = states;
        currentState = initialState;
    }

    /**
     * Analyzes the source and outputs the found lexemes to the specified output stream.
     *
     * @param source
     *            the source.
     * @param printStream
     *            the output stream.
     * @since 1.0
     */
    public void analyze(final String source, final PrintStream printStream) {
        this.source = source;
        this.printStream = printStream;

        for (final LexerState state : states.values()) {
            state.resetAutomatons();
        }

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

    /**
     * Returns the current state of the analyzer.
     *
     * @return the current state.
     * @since 1.0
     */
    public LexerState getCurrentState() {
        return currentState;
    }

    /**
     * Sets the current state of the analyzer.
     *
     * @param currentState
     *            the current state.
     * @since 1.0
     */
    public void setCurrentState(final LexerState currentState) {
        this.currentState = currentState;
    }

    /**
     * Sets the current state of the analyzer.
     *
     * @param stateName
     *            the current state.
     * @since 1.0
     */
    public void setCurrentStateFromName(final String stateName) {
        currentState = states.get(stateName);
    }

    /**
     * Returns the number of lines found currently.
     *
     * @return the number of lines.
     * @since 1.0
     */
    public int getLineCounter() {
        return lineCounter;
    }

    /**
     * Increments the line counter.
     *
     * @since 1.0
     */
    public void incrementLineCounter() {
        lineCounter++;
    }

    /**
     * @return the start index.
     * @since 1.0
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * Sets the start index.
     *
     * @param startIndex
     *            new start index.
     * @since 1.0
     */
    public void setStartIndex(final int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * Returns the point to which a source code has been processed.
     *
     * @return the point.
     * @since 1.0
     */
    public int getLastIndex() {
        return lastIndex;
    }

    /**
     * Sets the point to which a source code has been processed.
     *
     * @param lastIndex
     *            the point.
     * @since 1.0
     */
    public void setLastIndex(final int lastIndex) {
        this.lastIndex = lastIndex;
    }

    /**
     * Returns the point at which a current lexeme ends.
     *
     * @return the point.
     * @since 1.0
     */
    public int getFinishIndex() {
        return finishIndex;
    }

    /**
     * Sets the point at which a current lexeme ends.
     *
     * @param finishIndex
     *            the point.
     * @since 1.0
     */
    public void setFinishIndex(final int finishIndex) {
        this.finishIndex = finishIndex;
    }

    /**
     * @return returns the output stream.
     * @since 1.0
     */
    public PrintStream getOutput() {
        return printStream;
    }

    /**
     * @return returns the currently matched phrase.
     * @since 1.0
     */
    public String getCurrentPhrase() {
        return source.substring(startIndex, finishIndex + 1);
    }
}
