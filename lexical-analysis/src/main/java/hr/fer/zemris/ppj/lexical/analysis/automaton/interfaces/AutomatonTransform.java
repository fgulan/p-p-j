package hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces;

public interface AutomatonTransform<S extends Automaton, T extends Automaton> {

    T transform(S source);
}
