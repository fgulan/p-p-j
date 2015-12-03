package hr.fer.zemris.ppj.grammar;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.ppj.finite.automaton.DFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.ENFAutomaton;
import hr.fer.zemris.ppj.finite.automaton.generator.ENFAutomatonGenerator;
import hr.fer.zemris.ppj.finite.automaton.transforms.DFAConverter;
import hr.fer.zemris.ppj.lr1.parser.LR1ParserTable;
import hr.fer.zemris.ppj.lr1.parser.LR1ParserTableFactory;

@SuppressWarnings("javadoc")
public class GeneratorAud28Test {

    private Grammar grammar;
    private ENFAutomaton eNFA;
    private DFAutomaton DFA;
    private LR1ParserTable table;

    /*
     * See SS: PPJ - Auditorne MI 28.
     */
    @Before
    public void setUpBeforeClass() throws Exception {
        final List<String> nonterminalSymbols = new ArrayList<>();
        nonterminalSymbols.add("<S>");
        nonterminalSymbols.add("<A>");
        nonterminalSymbols.add("<B>");

        final List<String> terminalSymbols = new ArrayList<>();
        terminalSymbols.add("b");
        terminalSymbols.add("c");

        final String startSymbol = "<S>";

        final GrammarBuilder builder = new GrammarBuilder(nonterminalSymbols, terminalSymbols, startSymbol);
        builder.addProduction(ProductionParser.fromText("<S>", "b <A> <B>"));
        builder.addProduction(ProductionParser.fromText("<A>", "b <B> c"));
        builder.addProduction(ProductionParser.fromText("<B>", "b"));

        grammar = builder.build();
    }

    @Test
    public void test() {
        eNFA = new ENFAutomatonGenerator().fromLR1Grammar(grammar);
        System.out.println(eNFA);
        DFA = new DFAConverter().transform(eNFA);
        System.out.println(DFA);
        table = LR1ParserTableFactory.fromDFA(DFA, ProductionParser.parseSymbol("<%>"));
        System.out.println(table);
    }
}
