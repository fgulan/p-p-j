package hr.fer.zemris.ppj.lexical.analysis.text.manipulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>RegularExpressionManipulator</code> is a utility class for manipulation with regular expressions.
 *
 * @author Jan Kelemen
 *
 * @version 1.0.1
 */
public class RegularExpressionManipulator {

    /**
     * Regular definitions extracted from input file.
     */
    private final Map<String, String> regularDefinitions = new HashMap<>();

    /**
     * Class constructor, specifies the regular definitions known to the manipulator.
     *
     * @param regularDefinitions
     *            the regular definitions.
     * @since 1.0.0
     */
    public RegularExpressionManipulator(final List<String> regularDefinitions) {
        for (final String definition : regularDefinitions) {
            addRegularDefinition(definition);
        }
    }

    /**
     * Finds a matching closing bracket for the opening bracket at specified index <code>index</code>.
     *
     * @param expression
     *            the expression-
     * @param index
     *            the index.
     * @param openingBracket
     *            symbol for the opening bracket.
     * @param closingBracket
     *            symbol for the closing bracket.
     * @return index of a closing bracket, or <code>-1</code> if the closing bracket isn't found, or if the specified
     *         index doesn't point to a opening bracket.
     * @throws IndexOutOfBoundsException
     *             If a invalid index is passed.
     * @since 1.0.0
     */
    public static int findClosingBracket(final String expression, final int index, final char openingBracket,
            final char closingBracket) {
        if ((index < 0) || (index >= expression.length())) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", doesn't point to a character in the expression.");
        }

        if ((expression.charAt(index) != openingBracket) && !isEscaped(expression, index)) {
            return -1;
        }

        int order = 0;
        for (int i = index; i < expression.length(); i++) {
            if (!isEscaped(expression, i)) {
                if (expression.charAt(i) == openingBracket) {
                    order++;
                }
                else if (expression.charAt(i) == closingBracket) {
                    order--;
                    if (order == 0) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Checks if a symbol at specified index is escaped.
     *
     * @param expression
     *            the expression.
     * @param index
     *            the index.
     * @return <code>true</code> if the symbol is escaped, <code>false</code> otherwise.
     * @throws IndexOutOfBoundsException
     *             If a invalid index is passed.
     * @since 1.0.0
     */
    public static boolean isEscaped(final String expression, final int index) {
        if ((index < 0) || (index >= expression.length())) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", doesn't point to a character in the expression.");
        }
        int order = 0;
        int i = index;
        while (((i - 1) >= 0) && (expression.charAt(i - 1) == '\\')) {
            order++;
            i--;
        }
        return (order % 2) != 0;
    }

    /**
     * Splits a regular expression on the specified operator.
     *
     * @param expression
     *            the expression.
     * @param operator
     *            the operator.
     * @return split expression.
     * @since 1.0.0
     */
    public static List<String> splitOnOperator(final String expression, final char operator) {
        final List<String> subexpressions = new ArrayList<>();

        int order = 0;
        int lastStored = 0;
        for (int i = 0; i < expression.length(); i++) {
            if ((expression.charAt(i) == '(') && !isEscaped(expression, i)) {
                order++;
            }
            else if ((expression.charAt(i) == ')') && !isEscaped(expression, i)) {
                order--;
            }
            else if ((order == 0) && (expression.charAt(i) == operator) && !isEscaped(expression, i)) {
                subexpressions.add(expression.substring(lastStored, i));

                lastStored = i + 1;
            }
        }

        if (lastStored < expression.length()) {
            subexpressions.add(expression.substring(lastStored));
        }

        return subexpressions;
    }

    /**
     * Removes regular definitions from the specified regular expression.
     *
     * @param expression
     *            the regular expression.
     * @return regular expression without regular definitions.
     * @throws IllegalArgumentException
     *             If a expression contains unknown regular definition.
     * @throws IllegalArgumentException
     *             If a expression isn't properly formatted.
     * @since 1.0.0
     */
    public String removeRegularDefinitions(final String expression) {
        String newExpression = "";
        for (int i = 0; i < expression.length(); i++) {
            if ((expression.charAt(i) == '{') && !isEscaped(expression, i)) {
                final int closingBracketIndex = findClosingBracket(expression, i, '{', '}');
                if (closingBracketIndex == -1) {
                    throw new IllegalArgumentException(
                            "Expression: " + expression + ", contains a unescaped regular definition name.");
                }

                final String regularDefinitionName = expression.substring(i + 1, closingBracketIndex);
                if (!regularDefinitions.containsKey(regularDefinitionName)) {
                    throw new IllegalArgumentException(
                            "Expression: " + expression + ", contains a unknown regular definition.");
                }

                newExpression += "(" + regularDefinitions.get(regularDefinitionName) + ")";

                i = closingBracketIndex;
            }
            else {
                newExpression += expression.charAt(i);
            }
        }
        return newExpression;
    }

    /**
     * Adds a regular definition.
     *
     * @param line
     *            contains a regular definition name and the associated regular expression. <br>
     *            Format: {regularDefinitionName} regularExpression
     * @since 1.0.0
     */
    private void addRegularDefinition(final String line) {
        final String regularDefinitionName = line.substring(1, line.indexOf('}'));
        final String regularExpression = removeRegularDefinitions(line.substring(line.indexOf(' ') + 1));
        regularDefinitions.put(regularDefinitionName, regularExpression);
    }
}
