import java.util.ArrayList;
import java.util.List;

public class CalculationObject {

    private List<CalculationUnitObject> unitObjects = new ArrayList<CalculationUnitObject>();
    
    public void addObject(CalculationUnitObject object) {
        unitObjects.add(object);
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
            array.multiple(object.getBaseArray());
        return array.getBaseArray();
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
