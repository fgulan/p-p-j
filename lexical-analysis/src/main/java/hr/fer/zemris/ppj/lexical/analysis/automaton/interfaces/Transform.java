package hr.fer.zemris.ppj.lexical.analysis.automaton.interfaces;

public interface Transform<S, T> {

    T transform(S source);
}
