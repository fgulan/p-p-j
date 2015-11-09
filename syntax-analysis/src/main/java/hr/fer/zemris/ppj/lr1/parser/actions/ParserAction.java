package hr.fer.zemris.ppj.lr1.parser.actions;

import hr.fer.zemris.ppj.lr1.parser.LR1Parser;

/**
 * <code>Action</code> is a interface for LR(1) parser actions.
 *
 * @author Jan Kelemen
 *
 * @version 1.0
 */
public interface ParserAction {

    /**
     * Executes the action on the given parser.
     *
     * @param parser
     *            the parser.
     */
    void execute(LR1Parser parser);

}
