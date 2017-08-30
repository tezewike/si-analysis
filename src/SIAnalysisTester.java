
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

    	DataLoader.reload();
    	
        DimensionObject obj = new UnitParser().parse("l", "L", "1", "cm^3");
        OutputData data = new OutputData(obj.toJSONObject());
        
        System.out.println(obj.toJSONObject().toJSONString());
        System.out.println(data.getNumeratorMagnitude());
        
    }
    
}
