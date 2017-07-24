
public class SIAnalysisTester {
    
    public static void main(String[] args) {
    /*
        for (DimensionArray.Measures measure : DimensionArray.Measures.values())
            System.out.println(measure);
        System.out.println();
        for (DimensionArray.DerivedMeasures measure : DimensionArray.DerivedMeasures.values())
            System.out.println(measure);
        System.out.println();
    */
        DataLoader.initializeUnits();
        DataLoader.initializePrefixes();
        
        CalculationObject obj = new InputParser().parseUnits("ft");
        
        System.out.println(obj.toExtendedString() + " = " + obj.getMagnitude() + " " + obj.output());
        
    }
    
}
