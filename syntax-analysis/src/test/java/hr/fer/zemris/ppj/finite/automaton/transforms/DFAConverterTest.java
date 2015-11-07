package hr.fer.zemris.ppj.finite.automaton.transforms;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import hr.fer.zemris.ppj.finite.automaton.BasicInput;
import hr.fer.zemris.ppj.finite.automaton.BasicState;
import hr.fer.zemris.ppj.finite.automaton.DFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Input;
import hr.fer.zemris.ppj.finite.automaton.interfaces.State;
import hr.fer.zemris.ppj.finite.automaton.interfaces.TransferFunction;
import hr.fer.zemris.ppj.finite.automaton.interfaces.Transition;
import hr.fer.zemris.ppj.finite.automaton.transfer.DeterministicTransition;
import hr.fer.zemris.ppj.finite.automaton.transfer.ENFAutomatonTransferFunction;
import hr.fer.zemris.ppj.finite.automaton.transfer.EpsilonTransition;
import hr.fer.zemris.ppj.finite.automaton.transfer.FAutomatonTransition;

@SuppressWarnings("javadoc")
public class DFAConverterTest {

    static Set<State> states = new HashSet<>();
    static Set<State> acceptStates = new HashSet<>();
    static Set<FAutomatonTransition> newTransitions = new HashSet<>();
    static Set<Input> inputs = new HashSet<>();
    private static DFAConverter converter = new DFAConverter();

    @Test
    public void test() {

        final State state1 = new BasicState("1");
        states.add(state1);
        final State state2 = new BasicState("2");
        states.add(state2);
        final State state3 = new BasicState("3");
        states.add(state3);
        final State state4 = new BasicState("4");
        states.add(state4);

        final Input inputA = new BasicInput("a");
        inputs.add(inputA);
        final Input inputB = new BasicInput("b");
        inputs.add(inputB);
        final Input inputC = new BasicInput("c");
        inputs.add(inputC);

        final DeterministicTransition transition1 = new DeterministicTransition(state1, state2, inputA);
        newTransitions.add(transition1);
        final DeterministicTransition transition2 = new DeterministicTransition(state1, state4, inputC);
        newTransitions.add(transition2);
        final DeterministicTransition transition3 = new DeterministicTransition(state2, state3, inputB);
        newTransitions.add(transition2);
        final DeterministicTransition transition4 = new DeterministicTransition(state3, state2, inputA);
        newTransitions.add(transition4);
        final DeterministicTransition transition5 = new DeterministicTransition(state4, state3, inputC);
        newTransitions.add(transition5);

        final EpsilonTransition transition6 = new EpsilonTransition(state1, state1);
        newTransitions.add(transition6);
        final EpsilonTransition transition7 = new EpsilonTransition(state2, state1);
        newTransitions.add(transition7);
        final EpsilonTransition transition8 = new EpsilonTransition(state4, state3);
        newTransitions.add(transition8);
        final EpsilonTransition transition9 = new EpsilonTransition(state3, state3);
        newTransitions.add(transition9);

        final ENFAutomatonTransferFunction function = new ENFAutomatonTransferFunction(newTransitions);
        final ENFAutomaton source = new ENFAutomaton(states, acceptStates, inputs, function, state1);
        converter = new DFAConverter();
        final DFAutomaton newDFA = converter.transform(source);

        final Set<Input> inputsDFA = newDFA.getAlphabet();
        final Set<State> statesDFA = newDFA.getAcceptStates();
        final TransferFunction transferFunction = newDFA.getTransferFunction();
        final Set<Transition> transitionsDFA = transferFunction.getTransitions();

        for (final Transition trans : transitionsDFA) {
            System.out.println(trans);
        }
        System.out.println(converter.stateMap);
    }

}
