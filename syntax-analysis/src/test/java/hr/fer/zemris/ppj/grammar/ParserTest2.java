package hr.fer.zemris.ppj.grammar;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.ppj.finite.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.generator.ENFAutomatonGenerator;

@SuppressWarnings("javadoc")
public class ParserTest2 {

    private Grammar grammar;

    /*
     * See SS: PPJ - 151.
     */
    @Before
    public void setUpBeforeClass() throws Exception {
        final List<String> nonterminalSymbols = new ArrayList<>();
        nonterminalSymbols.add("<S'>");
        nonterminalSymbols.add("<S>");
        nonterminalSymbols.add("<C>");

        final List<String> terminalSymbols = new ArrayList<>();
        terminalSymbols.add("c");
        terminalSymbols.add("d");

        final String startSymbol = "<S'>";

        final GrammarBuilder builder = new GrammarBuilder(nonterminalSymbols, terminalSymbols, startSymbol);
        builder.addProduction(ProductionParser.fromText("<S>", "<C> <C>"));
        builder.addProduction(ProductionParser.fromText("<S'>", "<S>"));
        builder.addProduction(ProductionParser.fromText("<C>", "c <C>"));
        builder.addProduction(ProductionParser.fromText("<C>", "d"));

        grammar = builder.build();
    }

    @Test
    public void testStartsWith() {
        final ENFAutomaton automaton = new ENFAutomatonGenerator().fromLR1Grammar(grammar);
        System.out.println(automaton);
    }
}
