package hr.fer.zemris.ppj.finite.automaton;

import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.finite.automaton.interfaces.Automaton;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Transition;
import hr.fer.zemris.ppj.finite.automaton.transforms.ENFAtoDFA;
import hr.fer.zemris.ppj.finite.automaton.transforms.EquivalentRemover;
import hr.fer.zemris.ppj.finite.automaton.transforms.UnreachableRemover;

public class Automatons {

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

    public static void apply(final Automaton automaton, final String input) {
        automaton.apply(new BasicInput(input));
    }

    public static void apply(final Automaton automaton, final char input) {
        automaton.apply(new BasicInput(input));
    }

    public static void apply(final Automaton automaton, final int input) {
        automaton.apply(new BasicInput(input));
    }

    public static DFAutomaton minimize(final ENFAutomaton source) {
        final DFAutomaton converted = new ENFAtoDFA().transform(source);
        return minimize(converted);
    }

    public static DFAutomaton minimize(final DFAutomaton source) {
        DFAutomaton minimized = new UnreachableRemover().transform(source);
        minimized = new EquivalentRemover().transform(minimized);

        return minimized;
    }
}
