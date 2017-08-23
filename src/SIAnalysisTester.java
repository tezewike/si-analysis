
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
        
        DimensionObject obj = new UnitParser().parse("1", "L", "1", "cm^3");
        
        System.out.println(obj.toJSONObject().toJSONString());
        System.out.println(obj.toString());
        
    }
    
}
