package hr.fer.zemris.ppj;


public class Utils {

    private Utils(){}
    
    public static boolean badNode(Node node){
        SemanticErrorReporter.report(node);
        return false;
    }
}
