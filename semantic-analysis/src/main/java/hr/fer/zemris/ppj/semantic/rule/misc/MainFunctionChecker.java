package hr.fer.zemris.ppj.semantic.rule.misc;

import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>MainFunctionChecker</code> is a checker for main function.
 *
 * @author Matea Sabolic
 *
 * @version alpha
 */
public class MainFunctionChecker implements Checker {

    // main (void -> int)

    /**
     * Name of the node.
     */
    public static final String NAME = "<MainFunction>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<main>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 72.
     *
     * @since alpha
     */
    @Override
    public boolean check(Node node) {
        // TODO Auto-generated method stub
        return false;
    }

}
