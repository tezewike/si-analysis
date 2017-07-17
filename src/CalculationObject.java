import java.util.ArrayList;
import java.util.List;

public class CalculationObject {

    private final List<CalculationUnitObject> unitObjects;
    private Unit.System system;
    
    public CalculationObject() {
        this.unitObjects = new ArrayList<CalculationUnitObject>();
    }
    
    public CalculationObject(Unit.System system) {
        this.unitObjects = new ArrayList<CalculationUnitObject>();
        this.system = system;
    }
    
    public CalculationObject(Unit.System system, CalculationObject object) {
        this.unitObjects = object.getAllObjects();
        this.system = system;
    }
    
    public void addObject(CalculationUnitObject object) {
        unitObjects.add(object);
    }
    
    public List<CalculationUnitObject> getAllObjects() {
    	return unitObjects;
    }
    
    public double getMagnitude() {
        double value = 1;
        for (CalculationUnitObject object : unitObjects)
            value *= object.getMagnitude();
        return value;
    }
    
    public int[] getBaseArray() {
        DimensionArray array = new DimensionArray();
        for (CalculationUnitObject object : unitObjects)
            array.multiply(object.getBaseArray());
        return array.getBaseArray();
    }
    
    public CalculationObject convertToSystem(Unit.System system) {
        CalculationObject object = new CalculationObject(system, this);
		return object;
    }
    
    @Override
    public String toString() {
        String str = "";
        for (CalculationUnitObject object : unitObjects)
            str += object.toString() + " ";
        return str.substring(0, str.length() - 1);
    }
    
    public String toExtendedString() {
        String str = "";
        for (CalculationUnitObject object : unitObjects)
            str += object.toExtendedString() + " ";
        return str.substring(0, str.length() - 1);
    }
    
}
