package hr.fer.zemris.ppj.semantic.rule.declarations;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.Attribute;
import hr.fer.zemris.ppj.Node;
import hr.fer.zemris.ppj.Utils;
import hr.fer.zemris.ppj.VariableType;
import hr.fer.zemris.ppj.semantic.exceptions.MysteriousBugException;
import hr.fer.zemris.ppj.semantic.rule.Checker;

/**
 * <code>InitializationDeclaratorChecker</code> is a checker for initialization declarator.
 *
 * @author Domagoj Polancec
 *
 * @version alpha
 */
public class InitializationDeclaratorChecker implements Checker {

    // <init_deklarator> ::= <izravni_deklarator>
    // <init_deklarator> ::= <izravni_deklarator> OP_PRIDRUZI <inicijalizator>

    /**
     * Name of the node.
     */
    public static final String NAME = "<InitializationDeclarator>";

    /**
     * Name of the node in Croatian.
     */
    public static final String HR_NAME = "<init_deklarator>";

    /**
     * {@inheritDoc} <br>
     *
     * Referring pages: 69.
     *
     * @since alpha
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean check(Node node) {
        
        int size = node.childrenCount();
        Node child = node.getChild(0);
        child.addAttribute(Attribute.ITYPE, node.getAttribute(Attribute.ITYPE));
        child.check();
        
        VariableType type = (VariableType) child.getAttribute(Attribute.TYPE);
        Integer elemCount = (Integer) node.getAttribute(Attribute.ELEMENT_COUNT);
        if (elemCount == null){
            elemCount = 1;
        }
        
        if (size == 1){
            if (VariableType.isConst(type)){
                return Utils.badNode(node);
            }
            
            return true;
        }
        
        for (int i = 1; i < size; i++){
            Node current = node.getChild(i);
            
            if (!current.check()){
                return Utils.badNode(node);
            }
            
            if (current.name().equals(InitializatorChecker.HR_NAME)){
                List<VariableType> initTypes;
                if (VariableType.isArrayType(type)){
                    initTypes = (List<VariableType>) current.getAttribute(Attribute.TYPES);
                } else {
                    initTypes = new ArrayList<>();
                    initTypes.add((VariableType) current.getAttribute(Attribute.TYPE));
                }
                
                if(handleInits(elemCount, type, initTypes)){
                    return true;
                } else {
                    return Utils.badNode(node);
                }
            }
        }
        
        throw new MysteriousBugException("If this line ever executes, Parser has failed or an if statement"
                + " is missing a return statement. "
                + "Expected: " + InitializatorChecker.HR_NAME + ".");
//        Uncomment before deployment
//        return true;
    }

    private static boolean handleInits(Integer elemCount, VariableType myType, List<VariableType> initTypes) {
        if (elemCount > initTypes.size()){
            return false;
        }
        
        for (VariableType type: initTypes){
            if (!VariableType.implicitConversion(type, myType)){
                return false;
            }
        }
        
        return true;
    }

}
