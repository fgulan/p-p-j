package hr.fer.zemris.ppj.manipulators.declarations;

import java.util.Stack;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Production;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.code.command.CommandFactory;
import hr.fer.zemris.ppj.code.generator.FRISCGenerator;
import hr.fer.zemris.ppj.identifier.table.IdentifierTable;
import hr.fer.zemris.ppj.interfaces.Manipulator;
import hr.fer.zemris.ppj.manipulators.expressions.PrimaryExpressionManipulator;
import hr.fer.zemris.ppj.types.Type;

/**
 * <code>OuterDeclarationManipulator</code> is a manipulator for outer declaration.
 *
 * @author Domagoj Polancec
 *
 * @version 1.1
 */
public class OuterDeclarationManipulator implements Manipulator {

    private static final CommandFactory ch = new CommandFactory();

    /**
     * Name of the node.
     */
    public static final String NAME = "<OuterDeclaration>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<vanjska_deklaracija>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 65.
     *
     * @since 1.0
     */
    @Override
    public boolean check(final Node node) {

        if (!node.getChild(0).check()) {
            Utils.badNode(node);
        }

        return true;
    }

    @Override
    public void generate(Node node) {
        switch (Production.fromNode(node)) {
            case OUTER_DECLARATION_1: {
                // OUTER_DECLARATION_1("<vanjska_deklaracija> ::= <definicija_funkcije>"),
                node.getChild(0).generate();
                break;
            }

            case OUTER_DECLARATION_2: {
                // OUTER_DECLARATION_2("<vanjska_deklaracija> ::= <deklaracija>"),

                // <deklaracija> ::= <ime_tipa> <lista_init_deklaratora> TOCKAZAREZ
                Node declarationNode = node.getChild(0);

                // <lista_init_deklaratora> ::= <init_deklarator> | <lista_init_deklaratora> ZAREZ <init_deklarator>
                Node initDeclaratorsNode = declarationNode.getChild(1);

                // Resolve list of init declarators
                Stack<Node> initDeclaratorStack = new Stack<>();
                while (initDeclaratorsNode.childrenCount() == 3) {
                    initDeclaratorStack.push(initDeclaratorsNode.getChild(2));
                    initDeclaratorsNode = initDeclaratorsNode.getChild(0);
                }
                // This is the node with a single declarator
                initDeclaratorStack.push(initDeclaratorsNode.getChild(0));

                while (!initDeclaratorStack.isEmpty()) {
                    // <init_deklarator> ::= <izravni_deklarator> | <izravni_deklarator> OP_PRIDRUZI <inicijalizator>
                    Node initDeclarator = initDeclaratorStack.pop();

                    // <izravni_deklarator> ::= IDN | IDN L_UGL_ZAGRADA BROJ D_UGL_ZAGRADA
                    String name = (String) initDeclarator.getChild(0).getChild(0).getAttribute(Attribute.VALUE);
                    Type type = IdentifierTable.GLOBAL_SCOPE.identifierType(name);

                    if (type.isFunction()) {
                        continue;
                    }

                    String command = null;
                    // Variable with undefined value, just reserve the needed space...
                    if (initDeclarator.childrenCount() == 1) {
                        if (type.isArray()) {
                            int size = (Integer) initDeclarator.getChild(0).getChild(2).getAttribute(Attribute.VALUE);
                            command = ch.ds(size * type.size());
                        }
                        else {
                            command = ch.ds(type.size());
                        }
                    }
                    // Variable with a defined value
                    else {
                        // Calculate the value for each element, these are all constexpr
                        // Refactor to constexpr calculator
                        if (type.isArray()) {
                            // TODO: ubi me neda mi se sad.
                        }
                        else {
                            Node current = initDeclarator.getChild(2);
                            while (!(PrimaryExpressionManipulator.HR_NAME.equals(current.name()))) {
                                current = current.getChild(0);
                            }
                            if (type.size() == 1) {
                                char value = (Character) current.getChild(0).getAttribute(Attribute.VALUE);
                                command = ch.db(value);
                            }
                            else {
                                int value = (Integer) current.getChild(0).getAttribute(Attribute.VALUE);
                                command = ch.dw(value);
                            }
                        }
                    }

                    FRISCGenerator.defineClobal(name, command);
                }
                break;
            }

            default:
                System.err.println("Generation reached undefined production!");
                break;
        }
    }
}
