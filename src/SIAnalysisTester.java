
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
        
        DimensionObject obj = new UnitParser().parse("m");
        DimensionObject obj2 = new UnitParser().parse("ft");
        
        obj.divide(obj2);
        
        System.out.println(obj.toExtendedString() + " = " + obj.getMagnitude() + " " + obj.output());
        
    }
    
}
