package hr.fer.zemris.ppj;


public class Utils {

    private Utils(){}
    
    // naughty nodes go here
    public static boolean badNode(Node node){
        SemanticErrorReporter.report(node);
        return false;
    }
}
