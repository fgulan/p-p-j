package hr.fer.zemris.ppj.finite.automaton.transforms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import hr.fer.zemris.ppj.finite.automaton.DFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.generator.ENFAutomatonGenerator;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.transfer.FAutomatonTransition;

@SuppressWarnings("javadoc")
public class DFAConverterTest {

    static Set<State> states = new HashSet<>();
    static Set<State> acceptStates = new HashSet<>();
    static Set<FAutomatonTransition> newTransitions = new HashSet<>();
    static Set<Input> inputs = new HashSet<>();

    @Test
    public void test() {
        final ENFAutomatonGenerator generator = new ENFAutomatonGenerator();
        final String states = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17";
        final String acceptStates = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17";
        final String alphabet = "a b <S> <A> <B>";
        final List<String> transitions = new ArrayList<>();
        transitions.add("0 <S> 1");
        transitions.add("0 null 2");
        transitions.add("0 null 6");
        transitions.add("0 null 9");
        transitions.add("0 null 12");
        transitions.add("2 <A> 3");
        transitions.add("3 <A> 4");
        transitions.add("4 <B> 5");
        transitions.add("4 null 16");
        transitions.add("6 <B> 7");
        transitions.add("7 <S> 8");
        transitions.add("7 null 2");
        transitions.add("7 null 6");
        transitions.add("7 null 9");
        transitions.add("7 null 12");
        transitions.add("9 <A> 10");
        transitions.add("10 <A> 11");
        transitions.add("10 null 14");
        transitions.add("12 <B> 13");
        transitions.add("12 null 16");
        transitions.add("14 a 15");
        transitions.add("16 b 17");
        final String startState = "0";

        final ENFAutomaton eNFA = generator.fromTextDefinition(states, acceptStates, alphabet, transitions, startState);
        final DFAutomaton DFA = new DFAConverter().transform(eNFA);
        System.out.println(DFA);

    }

}
