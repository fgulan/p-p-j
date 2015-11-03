package hr.fer.zemris.ppj.finite.automaton;

import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.interfaces.Automaton;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Transition;
import hr.fer.zemris.ppj.finite.automaton.transforms.DFAConverter;
import hr.fer.zemris.ppj.finite.automaton.transforms.EquivalentRemover;
import hr.fer.zemris.ppj.finite.automaton.transforms.UnreachableRemover;

/**
 * <code>Automatons</code> is a utility class for manipulation with automatons.
 *
 * @author Domagoj Polancec
 *
 * @version 1.0
 */
public class Automatons {

    /**
     * Extracts states old or new states from the transition set.
     *
     * @param transitions
     *            the transition from which states will be extracted.
     * @param extractOld
     *            flag which marks if old states need to be extracted.
     * @param extractNew
     *            flag which marks if new states need to be extracted.
     * @return extracted states.
     * @since 1.0
     */
    public static Set<State> extractStates(final Set<Transition> transitions, final boolean extractOld,
            final boolean extractNew) {
        final Set<State> extracted = new HashSet<>();
        for (final Transition transition : transitions) {
            if (extractOld) {
                extracted.add(transition.getOldState());
            }

            if (extractNew) {
                final State newState = transition.getNewState();
                if (newState != null) {
                    extracted.add(transition.getNewState());
                }
            }
        }

        return extracted;
    }

    /**
     * Applies a input to the automaton.
     *
     * @param automaton
     *            the automaton.
     * @param input
     *            the input.
     * @since 1.0
     */
    public static void apply(final Automaton automaton, final String input) {
        automaton.apply(new BasicInput(input));
    }

    /**
     * Applies a input to the automaton.
     *
     * @param automaton
     *            the automaton.
     * @param input
     *            the input.
     * @since 1.0
     */
    public static void apply(final Automaton automaton, final char input) {
        automaton.apply(new BasicInput(input));
    }

    /**
     * Applies a input to the automaton.
     *
     * @param automaton
     *            the automaton.
     * @param input
     *            the input.
     * @since 1.0
     */
    public static void apply(final Automaton automaton, final int input) {
        automaton.apply(new BasicInput(input));
    }

    /**
     * Converts the given automaton to a DFA.
     *
     * @param source
     *            the automaton,
     * @return converted automaton.
     * @since 1.0
     */
    public static DFAutomaton convertToDFA(final Automaton source) {
        if (source instanceof ENFAutomaton) {
            return new DFAConverter().transform((ENFAutomaton) source);
        }
        return (DFAutomaton) source;
    }

    /**
     * Minimizes the automaton to a minimal DFA.
     *
     * @param source
     *            the automaton.
     * @return minimized automaton.
     * @since 1.0
     */
    public static DFAutomaton minimize(final Automaton source) {
        final DFAutomaton converted = convertToDFA(source);
        final DFAutomaton minimized = new EquivalentRemover().transform(new UnreachableRemover().transform(converted));
        return minimized;
    }

}
