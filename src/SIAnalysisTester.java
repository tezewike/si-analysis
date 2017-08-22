
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
        
        DimensionObject obj = new UnitParser().parse("cm^3 kg ms");
        DimensionObject obj2 = new UnitParser().parse("L m^2");
        
  //      obj.divide(obj2);
        
        System.out.println(obj.toJSONObject().toJSONString());
        
    }
    
}
