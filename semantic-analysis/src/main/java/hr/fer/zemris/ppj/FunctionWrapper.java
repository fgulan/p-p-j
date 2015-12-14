package hr.fer.zemris.ppj;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>FunctionWrapper</code> contains type info for functions.
 *
 * @author Jan Kelemen
 *
 * @version alpha
 */
public class FunctionWrapper {

    private VariableType returnType;
    private List<VariableType> argumentList;

    /**
     * Class constructor, specifies the return type and the argument list of the function.
     * 
     * @param returnType
     *            the return type.
     * @param argumentList
     *            the argument list.
     * @since alpha
     */
    public FunctionWrapper(VariableType returnType, List<VariableType> argumentList) {
        this.returnType = returnType;
        this.argumentList = new ArrayList<>(argumentList);
    }

    /**
     * @return return type of the function.
     * @since alpha
     */
    public VariableType returnType() {
        return returnType;
    }

    /**
     * @return argument list of the function.
     * @since alpha.
     */
    public List<VariableType> argumentList() {
        return argumentList;
    }

}
