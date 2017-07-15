import java.util.List;

public class Tester {
    
    public static void main(String[] args) {
        for (DimensionArray.Measures measure : DimensionArray.Measures.values())
            System.out.println(measure);
        for (DimensionArray.DerivedMeasures measure : DimensionArray.DerivedMeasures.values())
            System.out.println(measure);
    
        DataLoader.initializeUnits();
        
        List<Unit> unitsList = Unit.getAll();
        
        for (Unit unit : unitsList)
        	System.out.println(unit);
    }
    
}
