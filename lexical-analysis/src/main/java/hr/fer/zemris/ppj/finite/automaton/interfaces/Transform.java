package hr.fer.zemris.ppj.finite.automaton.interfaces;

public interface Transform<S, T> {

    T transform(S source);
}
