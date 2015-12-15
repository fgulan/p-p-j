package hr.fer.zemris.ppj.semantic.rule.declarations;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.VariableType;
import hr.fer.zemris.ppj.semantic.exceptions.MysteriousBugException;
import hr.fer.zemris.ppj.semantic.rule.Checker;
import hr.fer.zemris.ppj.semantic.rule.expressions.AssignExpressionChecker;
import hr.fer.zemris.ppj.semantic.rule.expressions.AssignExpressionListChecker;

/**
 * <code>InitializatorChecker</code> is a checker for initializator.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class InitializatorChecker implements Checker {

    // <inicijalizator> ::= <izraz_pridruzivanja>
    // <inicijalizator> ::= L_VIT_ZAGRADA <lista_izraza_pridruzivanja> D_VIT_ZAGRADA

    /**
     * Name of the node.
     */
    public static final String NAME = "<Initializator>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<inicijalizator>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 71, 72.
     *
     * @since alpha
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean check(Node node) {
        Node child = node.getChild(0);
        
        int elemCount;
        List<VariableType> types = new ArrayList<>();
        VariableType type;
        
        if (child.name().equals(AssignExpressionChecker.HR_NAME)){
            Node assignExpr = child;
            if (!assignExpr.check()){
                return Utils.badNode(node);
            }
            
            if (assignExpr.getAttribute(Attribute.TYPE) == VariableType.CONST_CHAR_ARRAY){
//            if CELEM_COUNT shenanigans don't work, use the following code:        
//                elemCount = ((String)(assignExpr.getChild(0).getChild(0).getChild(0)
//                        .getAttribute(Attribute.VALUE))).length() - 1;
                elemCount = (int) assignExpr.getAttribute(Attribute.CELEM_COUNT) + 1;
                for (int i = 0; i < elemCount; i++){
                    types.add(VariableType.CHAR);
                }
                
                node.addAttribute(Attribute.ELEMENT_COUNT, elemCount);
                node.addAttribute(Attribute.TYPES, types);
            } else {
                type = (VariableType) assignExpr.getAttribute(Attribute.TYPE);
                node.addAttribute(Attribute.TYPE, type);
            }
            
            return true;
        }
        
        int size = node.childrenCount();
        for (int i = 0; i < size; i++){
            Node current = node.getChild(i);
            
            if (!current.check()){
                return Utils.badNode(node);
            }
            
            if (current.name().equals(AssignExpressionListChecker.HR_NAME)){
                types = (List<VariableType>) current.getAttribute(Attribute.TYPES);
                elemCount = types.size();
                
                node.addAttribute(Attribute.ELEMENT_COUNT, elemCount);
                node.addAttribute(Attribute.TYPES, types);
                
                return true;
            }
            
        }
        
        throw new MysteriousBugException("If this line ever executes, Parser has failed or an if statement"
                + " is missing a return statement. "
                + "Expected: " + AssignExpressionChecker.HR_NAME + " or " 
                + AssignExpressionListChecker.HR_NAME + ".");
//      Uncomment before deployment
//      return true;
    }

}
