package hr.fer.zemris.ppj.lexical.analysis.automaton;

import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Automaton;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.State;
import hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces.Transition;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transforms.ENFAtoDFA;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transforms.EquivalentRemover;
import hr.fer.zemris.ppj.lexical.analysis.automaton.transforms.UnreachableRemover;

public class Automatons {

    public static Set<State> extractStates(Set<Transition> transitions, boolean extractOld, boolean extractNew) {
        Set<State> extracted = new HashSet<>();
        for (Transition transition : transitions) {
            if (extractOld) {
                extracted.add(transition.getOldState());
            }

            if (extractNew) {
                State newState = transition.getNewState();
                if (newState != null) {
                    extracted.add(transition.getNewState());
                }
            }
        }

        return extracted;
    }

    public static void apply(Automaton automaton, String input) {
        automaton.apply(new BasicInput(input));
    }

    public static void apply(Automaton automaton, char input) {
        automaton.apply(new BasicInput(input));
    }

    public static void apply(Automaton automaton, int input) {
        automaton.apply(new BasicInput(input));
    }

    public static DFAutomaton minimize(ENFAutomaton source) {
        DFAutomaton converted = new ENFAtoDFA().transform(source);
        return minimize(converted);
    }

    public static DFAutomaton minimize(DFAutomaton source) {
        DFAutomaton minimized = new UnreachableRemover().transform(source);
        minimized = new EquivalentRemover().transform(minimized);

        return minimized;
    }
}
