package hr.fer.zemris.ppj.grammar;

import java.util.HashSet;
import java.util.Set;

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
        Set<String> nonterminalSymbols = new HashSet<>();
        nonterminalSymbols.add("<A>");
        nonterminalSymbols.add("<B>");
        nonterminalSymbols.add("<C>");
        nonterminalSymbols.add("<D>");
        nonterminalSymbols.add("<E>");

        Set<String> terminalSymbols = new HashSet<>();
        terminalSymbols.add("a");
        terminalSymbols.add("b");
        terminalSymbols.add("c");
        terminalSymbols.add("d");
        terminalSymbols.add("e");
        terminalSymbols.add("f");

        String startSymbol = "<A>";

        GrammarBuilder builder = new GrammarBuilder(nonterminalSymbols, terminalSymbols, startSymbol);
        builder.addProduction("<A>", "<B> <C> c");
        builder.addProduction("<A>", "e <D> <B>");
        builder.addProduction("<B>", "$");
        builder.addProduction("<B>", "b <C> <D> <E>");
        builder.addProduction("<C>", "<D> a <B>");
        builder.addProduction("<C>", "c a");
        builder.addProduction("<D>", "$");
        builder.addProduction("<D>", "d <D>");
        builder.addProduction("<E>", "e <A> f");
        builder.addProduction("<E>", "c");

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
