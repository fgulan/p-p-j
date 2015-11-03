package hr.fer.zemris.ppj.utility.text.manipulation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import hr.fer.zemris.ppj.utility.text.manipulation.RegularExpressionManipulator;

@SuppressWarnings("javadoc")
public class RegularExpressionManipulatorTest {

    private static RegularExpressionManipulator manipulator;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        final List<String> regularDefinitions = new ArrayList<>();

        regularDefinitions.add("{znak} a|b");
        regularDefinitions.add("{znamenka} 0|1|2|3");
        regularDefinitions.add("{hexZnamenka} {znamenka}|a|b|c|d");

        manipulator = new RegularExpressionManipulator(regularDefinitions);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        manipulator = null;
    }

    @Test
    public void testFindClosingBracket() {
        final String expression = "ab((c(d\\())e)";
        assertEquals(-1, RegularExpressionManipulator.findClosingBracket(expression, 0, '(', ')'));
        assertEquals(12, RegularExpressionManipulator.findClosingBracket(expression, 2, '(', ')'));
        assertEquals(10, RegularExpressionManipulator.findClosingBracket(expression, 3, '(', ')'));
        assertEquals(9, RegularExpressionManipulator.findClosingBracket(expression, 5, '(', ')'));

        assertEquals(-1, RegularExpressionManipulator.findClosingBracket(expression, 8, '(', ')'));
    }

    @Test
    public void testIsEcaped() {
        final String expression = "a\\b\\\\c\\\\\\d";
        assertFalse(RegularExpressionManipulator.isEscaped(expression, 0));
        assertTrue(RegularExpressionManipulator.isEscaped(expression, 2));
        assertFalse(RegularExpressionManipulator.isEscaped(expression, 5));
        assertTrue(RegularExpressionManipulator.isEscaped(expression, 9));
    }

    @Test
    public void testRemoveRegularDefinitions() {
        assertEquals("(0|1|2|3)|\\{znak}|((0|1|2|3)|a|b|c|d)",
                manipulator.removeRegularDefinitions("{znamenka}|\\{znak}|{hexZnamenka}"));
    }

    @Test
    public void testSplitOnOperator() {
        assertEquals("[a, b, (c|d), e, (a|(b|c))]",
                RegularExpressionManipulator.splitOnOperator("a|b|(c|d)|e|(a|(b|c))", '|').toString());
    }
}
