package hr.fer.zemris.ppj.grammar;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.ppj.finite.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.lr1.parser.ParserBuilder;

@SuppressWarnings("javadoc")
public class ParserTest {

    private Grammar grammar;

    /*
     * See SS: PPJ - 148.
     */
    @Before
    public void setUpBeforeClass() throws Exception {
        List<String> nonterminalSymbols = new ArrayList<>();
        nonterminalSymbols.add("<S>");
        nonterminalSymbols.add("<A>");
        nonterminalSymbols.add("<B>");

        List<String> terminalSymbols = new ArrayList<>();
        terminalSymbols.add("a");
        terminalSymbols.add("b");

        String startSymbol = "<S>";

        GrammarBuilder builder = new GrammarBuilder(nonterminalSymbols, terminalSymbols, startSymbol);
        builder.addProduction(ProductionParser.fromText("<S>", "<A>"));
        builder.addProduction(ProductionParser.fromText("<A>", "<B> <A>"));
        builder.addProduction(ProductionParser.fromText("<A>", "$"));
        builder.addProduction(ProductionParser.fromText("<B>", "a <B>"));
        builder.addProduction(ProductionParser.fromText("<B>", "b"));

        grammar = builder.build();
    }

    @Test
    public void testStartsWith() {
        ENFAutomaton automaton = ParserBuilder.fromLR1Grammar(grammar);
        System.out.println(automaton);
    }
}
