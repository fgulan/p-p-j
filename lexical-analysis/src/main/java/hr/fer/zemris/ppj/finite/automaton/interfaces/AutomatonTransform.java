package hr.fer.zemris.ppj.finite.automaton.interfaces;

/**
 * <code>AutomatonTransform</code> definies a interface for automaton transformations.
 *
 * @author Domagoj Polancec
 *
 * @param <S>
 *            type of the source automaton in the transformation.
 * @param <T>
 *            type of the automaton after the transformation.
 * @version 1.0
 */
public interface AutomatonTransform<S extends Automaton, T extends Automaton> {

    /**
     * Transforms the automaton.
     *
     * @param source
     *            the source automaton.
     * @return transformed automaton.
     */
    T transform(S source);
}
