import java.util.List;

public class Tester {
    
    public static void main(String[] args) {
        for (DimensionArray.Measures measure : DimensionArray.Measures.values())
            System.out.println(measure);
        System.out.println();
        for (DimensionArray.DerivedMeasures measure : DimensionArray.DerivedMeasures.values())
            System.out.println(measure);
        System.out.println();
    
        DataLoader.initializeUnits();
        DataLoader.initializePrefixes();
        
        CalculationObject obj = new InputParser("m s^2 c kg g^3").getCalculationObject();
        
        System.out.println(obj.toExtendedString());
        
    }
    
}
