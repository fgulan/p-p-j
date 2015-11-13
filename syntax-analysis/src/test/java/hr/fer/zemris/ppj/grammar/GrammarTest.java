package hr.fer.zemris.ppj.grammar;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class GrammarTest {

    private Grammar grammar;

    /*
     * See SS: PPJ - 100-103.
     */
    @Before
    public void setUpBeforeClass() throws Exception {
        List<String> nonterminalSymbols = new ArrayList<>();
        nonterminalSymbols.add("<A>");
        nonterminalSymbols.add("<B>");
        nonterminalSymbols.add("<C>");
        nonterminalSymbols.add("<D>");
        nonterminalSymbols.add("<E>");

        List<String> terminalSymbols = new ArrayList<>();
        terminalSymbols.add("a");
        terminalSymbols.add("b");
        terminalSymbols.add("c");
        terminalSymbols.add("d");
        terminalSymbols.add("e");
        terminalSymbols.add("f");

        String startSymbol = "<A>";

        GrammarBuilder builder = new GrammarBuilder(nonterminalSymbols, terminalSymbols, startSymbol);
        builder.addProduction(ProductionParser.fromText("<A>", "<B> <C> c"));
        builder.addProduction(ProductionParser.fromText("<A>", "e <D> <B>"));
        builder.addProduction(ProductionParser.fromText("<B>", "$"));
        builder.addProduction(ProductionParser.fromText("<B>", "b <C> <D> <E>"));
        builder.addProduction(ProductionParser.fromText("<C>", "<D> a <B>"));
        builder.addProduction(ProductionParser.fromText("<C>", "c a"));
        builder.addProduction(ProductionParser.fromText("<D>", "$"));
        builder.addProduction(ProductionParser.fromText("<D>", "d <D>"));
        builder.addProduction(ProductionParser.fromText("<E>", "e <A> f"));
        builder.addProduction(ProductionParser.fromText("<E>", "c"));

        grammar = builder.build();
    }

    @Test
    public void testStartsWith() {
        System.out.println(grammar.startsWith("<B> <C> e"));
        System.out.println(grammar.startsWith("e <D> <B>"));
        System.out.println(grammar.startsWith("$"));
        System.out.println(grammar.startsWith("b <C> <D> <E>"));
        System.out.println(grammar.startsWith("<D> a <B>"));
        System.out.println(grammar.startsWith("c a"));
        System.out.println(grammar.startsWith("$"));
        System.out.println(grammar.startsWith("d <D>"));
        System.out.println(grammar.startsWith("e <A> f"));
        System.out.println(grammar.startsWith("c"));
    }
}
