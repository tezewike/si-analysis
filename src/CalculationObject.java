import java.util.ArrayList;
import java.util.List;

public class CalculationObject {

    private final List<CalculationUnitObject> unitObjects;
    private double magnitude = 1.0;
    private DimensionArray dimensions;
    
    public CalculationObject() {
        this.unitObjects = new ArrayList<CalculationUnitObject>();
        this.dimensions = new DimensionArray();
    }
    
    public void addObject(CalculationUnitObject object) {
        unitObjects.add(object);
        dimensions.multiply(object.getDimensions());
        magnitude *= object.getMagnitude();
    }
    
    public List<CalculationUnitObject> getAllObjects() {
    	return unitObjects;
    }
    
    public double getMagnitude() {
        return magnitude;
    }
    
    public double getMagnitude(Unit.System system) {
        double value = magnitude;
        int[] array = dimensions.getBaseArray();
        Unit[] units = system.getUnits();
        
        for (int i = 0; i < DimensionArray.ARRAY_SIZE; i++) {
            value /= Math.pow(units[i].getMagnitude(), array[i]);
        }
        
        return value;
    }
    
    public DimensionArray getDimensions() {
        return (DimensionArray) dimensions.clone();
    }
    
    @Override
    public String toString() {
        String str = "";
        for (CalculationUnitObject object : unitObjects)
            str += object.toString() + " ";
        return str.substring(0, str.length() - 1);
    }
    
    public String output() {
        Unit[] units = Unit.System.INTERNATIONAL_SYSTEM.getUnits();
        int[] array = dimensions.getBaseArray();
        
        String str = "";
        for (int i = 0; i < units.length; i++) {
            if (array[i] != 0)
                str += units[i].getName() + Utils.toSuperscript(array[i]) + " ";
        }
        return str.substring(0, str.length() - 1);
    }
    
    public String toExtendedString() {
        String str = "";
        for (CalculationUnitObject object : unitObjects)
            str += object.toExtendedString() + " ";
        return str.substring(0, str.length() - 1);
    }
    
}
