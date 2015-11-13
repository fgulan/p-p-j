package hr.fer.zemris.ppj.grammar;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.ppj.finite.automaton.DFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.transforms.DFAConverter;
import hr.fer.zemris.ppj.lr1.parser.LR1ParserTable;
import hr.fer.zemris.ppj.lr1.parser.LR1ParserTableFactory;
import hr.fer.zemris.ppj.lr1.parser.ParserBuilder;

@SuppressWarnings("javadoc")
public class Generator151Test {

    private Grammar grammar;
    private ENFAutomaton eNFA;
    private DFAutomaton DFA;
    private LR1ParserTable table;

    /*
     * See SS: PPJ - 148.
     */
    @Before
    public void setUpBeforeClass() throws Exception {
        List<String> nonterminalSymbols = new ArrayList<>();
        nonterminalSymbols.add("<%>");
        nonterminalSymbols.add("<S>");
        nonterminalSymbols.add("<C>");

        List<String> terminalSymbols = new ArrayList<>();
        terminalSymbols.add("c");
        terminalSymbols.add("d");

        String startSymbol = "<%>";

        GrammarBuilder builder = new GrammarBuilder(nonterminalSymbols, terminalSymbols, startSymbol);
        builder.addProduction(ProductionParser.fromText("<%>", "<S>"));
        builder.addProduction(ProductionParser.fromText("<S>", "<C> <C>"));
        builder.addProduction(ProductionParser.fromText("<C>", "c <C>"));
        builder.addProduction(ProductionParser.fromText("<C>", "d"));

        grammar = builder.build();
    }

    @Test
    public void test() {
        eNFA = ParserBuilder.fromLR1Grammar(grammar);
        DFA = new DFAConverter().transform(eNFA);
        table = LR1ParserTableFactory.fromDFA(DFA, ProductionParser.parseSymbol("<%>"));
        System.out.println(table);
    }

}
