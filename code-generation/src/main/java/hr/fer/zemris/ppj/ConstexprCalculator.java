package hr.fer.zemris.ppj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.ppj.types.CharType;
import hr.fer.zemris.ppj.types.ConstCharType;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>ConstexprCalculator</code> calculates values of const expressions.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class ConstexprCalculator {

    /**
     * @param node
     *            <inicijalizator> node.
     * @return calculated value.
     * @since alpha
     */
    public static List<Integer> calculate(Node node) {
        // <inicijalizator> =*> NIZ_ZNAKOVA
        Node current = node;
        while (current.childrenCount() != 0) {
            current = current.getChild(0);
        }

        if ("NIZ_ZNAKOVA".equals(current.name())) {
            String value = (String) current.getAttribute(Attribute.VALUE);

            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < value.length(); i++) {
                result.add((int) value.charAt(i));
            }
            result.add(0);

            return result;
        }

        if (Production.fromNode(node) == Production.INITIALIZATOR_2) {
            Node expressionsList = node.getChild(1);
            Stack<Node> stack = new Stack<>();
            Node temp = expressionsList;
            Production pro = Production.fromNode(temp);
            while (Production.fromNode(temp) == Production.ASSIGN_EXPRESSION_LIST_2) {
                stack.push(temp.getChild(2));
                temp = temp.getChild(0);
            }
            stack.push(temp.getChild(0));

            List<Integer> result = new ArrayList<>();
            while (!stack.isEmpty()) {
                result.add(calculateAssignExpression(stack.pop()));
            }

            return result;
        }

        return Arrays.asList(calculateAssignExpression(node.getChild(0)));
    }

    private static int calculateAssignExpression(Node node) {
        if (Production.fromNode(node) == Production.ASSIGN_EXPRESSION_2) {
            System.err.println("This isn't implemented, desperate try to get something");
            return calculateAssignExpression(node.getChild(2));
        }

        return calculateLogicalOrExpression(node.getChild(0));
    }

    private static int calculateLogicalOrExpression(Node node) {
        if (Production.fromNode(node) == Production.LOGICAL_OR_EXPRESSION_1) {
            return calculateLogicalAndExpression(node.getChild(0));
        }

        int left = calculateLogicalOrExpression(node.getChild(0));
        int right = calculateLogicalAndExpression(node.getChild(2));

        if ((left != 0) || (right != 0)) {
            return 1;
        }
        return 0;

    }

    private static int calculateLogicalAndExpression(Node node) {
        if (Production.fromNode(node) == Production.LOGICAL_AND_EXPRESSION_1) {
            return calculateBinaryOrExpression(node.getChild(0));
        }

        int left = calculateLogicalAndExpression(node.getChild(0));
        int right = calculateBinaryOrExpression(node.getChild(2));

        if ((left != 0) && (right != 0)) {
            return 1;
        }
        return 0;
    }

    private static int calculateBinaryOrExpression(Node node) {
        if (Production.fromNode(node) == Production.BINARY_OR_EXPRESSION_1) {
            return calculateBinaryXorExpression(node.getChild(0));
        }

        int left = calculateBinaryOrExpression(node.getChild(0));
        int right = calculateBinaryXorExpression(node.getChild(2));

        return left | right;

    }

    private static int calculateBinaryXorExpression(Node node) {
        if (Production.fromNode(node) == Production.BINARY_XOR_EXPRESSION_1) {
            return calculateBinaryAndExpression(node.getChild(0));
        }

        int left = calculateBinaryXorExpression(node.getChild(0));
        int rigth = calculateBinaryAndExpression(node.getChild(2));

        return left ^ rigth;

    }

    private static int calculateBinaryAndExpression(Node node) {
        if (Production.fromNode(node) == Production.BINARY_AND_EXPRESSION_1) {
            return calculateEqualityExpression(node.getChild(0));
        }

        int left = calculateBinaryAndExpression(node.getChild(0));
        int right = calculateEqualityExpression(node.getChild(2));

        return left & right;

    }

    private static int calculateEqualityExpression(Node node) {
        if (Production.fromNode(node) == Production.EQUALITY_EXPRESSION_1) {
            return calculateRelationalExpression(node.getChild(0));
        }

        int left = calculateEqualityExpression(node.getChild(0));
        int right = calculateRelationalExpression(node.getChild(2));

        if (Production.fromNode(node) == Production.EQUALITY_EXPRESSION_2) {
            if (left == right) {
                return 1;
            }
            return 0;
        }

        if (left != right) {
            return 1;
        }
        return 0;
    }

    private static int calculateRelationalExpression(Node node) {
        if (Production.fromNode(node) == Production.RELATIONAL_EXPRESSION_1) {
            return calculateAdditiveExpression(node.getChild(0));
        }

        int left = calculateRelationalExpression(node);
        int right = calculateAdditiveExpression(node.getChild(2));

        if (Production.fromNode(node) == Production.RELATIONAL_EXPRESSION_2) {
            if (left < right) {
                return 1;
            }
            return 0;
        }

        if (Production.fromNode(node) == Production.RELATIONAL_EXPRESSION_3) {
            if (left > right) {
                return 1;
            }
            return 0;
        }

        if (Production.fromNode(node) == Production.RELATIONAL_EXPRESSION_4) {
            if (left <= right) {
                return 1;
            }
            return 0;
        }

        if (left >= right) {
            return 1;
        }
        return 0;
    }

    private static int calculateAdditiveExpression(Node node) {
        if (Production.fromNode(node) == Production.ADDITIVE_EXPRESSION_1) {
            return calculateMultiplicativeExpression(node.getChild(0));
        }

        int left = calculateAdditiveExpression(node.getChild(0));
        int right = calculateMultiplicativeExpression(node.getChild(2));
        if (Production.fromNode(node) == Production.ADDITIVE_EXPRESSION_2) {
            return left + right;
        }

        return left - right;
    }

    private static int calculateMultiplicativeExpression(Node node) {
        if (Production.fromNode(node) == Production.MULTIPLICATIVE_EXPRESSION_1) {
            return calculateCastExpression(node.getChild(0));
        }

        int left = calculateMultiplicativeExpression(node.getChild(0));
        int right = calculateCastExpression(node.getChild(2));

        if (Production.fromNode(node) == Production.MULTIPLICATIVE_EXPRESSION_2) {
            return left * right;
        }

        if (Production.fromNode(node) == Production.MULTIPLICATIVE_EXPRESSION_3) {
            return left / right;
        }

        return left % right;
    }

    private static int calculateCastExpression(Node node) {
        if (Production.fromNode(node) == Production.CAST_EXPRESSION_1) {
            return calculateUnaryExpression(node.getChild(0));
        }

        int value = calculateCastExpression(node.getChild(3));

        Type type = (Type) node.getAttribute(Attribute.TYPE);
        if ((type instanceof CharType) || (type instanceof ConstCharType)) {
            return (char) value;
        }

        return value;
    }

    private static int calculateUnaryExpression(Node node) {
        if (Production.fromNode(node) == Production.UNARY_EXPRESSION_1) {
            return calculatePostfixExpression(node.getChild(0));
        }

        if (Production.fromNode(node) == Production.UNARY_EXPRESSION_2) {
            int value = calculateUnaryExpression(node.getChild(1));
            return value + 1;
        }

        if (Production.fromNode(node) == Production.UNARY_EXPRESSION_3) {
            int value = calculateUnaryExpression(node.getChild(1));
            return value - 1;
        }

        int value = calculateCastExpression(node.getChild(1));
        Node unaryOp = node.getChild(0);
        if (Production.fromNode(unaryOp) == Production.UNARY_OPERATOR_1) {
            return value;
        }

        if (Production.fromNode(unaryOp) == Production.UNARY_OPERATOR_2) {
            return -value;
        }

        if (Production.fromNode(unaryOp) == Production.UNARY_OPERATOR_3) {
            return ~value;
        }

        if (value == 0) {
            return 1;
        }
        return 0;
    }

    private static int calculatePostfixExpression(Node node) {
        if (Production.fromNode(node) == Production.POSTFIX_EXPRESSION_1) {
            return calculatePrimaryExpression(node.getChild(0));
        }

        System.err.println("Not implemented who cares!");
        return 0;
    }

    private static int calculatePrimaryExpression(Node node) {
        if (Production.fromNode(node) == Production.PRIMARY_EXPRESSION_2) {
            int value = (Integer) node.getChild(0).getAttribute(Attribute.VALUE);
            return value;
        }

        if (Production.fromNode(node) == Production.PRIMARY_EXPRESSION_3) {
            char value = (Character) node.getChild(0).getAttribute(Attribute.VALUE);
            return value;
        }

        System.err.println("Not implemented who cares!");
        return 0;
    }
}
