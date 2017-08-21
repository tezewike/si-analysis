import java.util.ArrayList;
import java.util.List;

public class DimensionObject {

	private static final int DEFAULT_EXPONENT = 1; 
	private static final Unit.System  DEFAULT_SYSTEM = Unit.System.INTERNATIONAL_SYSTEM;
	
    private final List<UnitObject> numeratorObjects;
    private final List<UnitObject> denominatorObjects;
    private double magnitude = 1.0;
    private DimensionArray dimensions;
    
    public DimensionObject() {
        this.numeratorObjects = new ArrayList<UnitObject>();
        this.denominatorObjects = new ArrayList<UnitObject>();
        this.dimensions = new DimensionArray();
    }
    
    public DimensionObject(double magnitude) {
    	this();
    	this.magnitude = magnitude;
    }
    
    public void addNumeratorUnit(Unit unit) {
    	this.addNumeratorUnit(null, unit, DEFAULT_EXPONENT);
    }
    
    public void addNumeratorUnit(Prefix prefix, Unit unit) {
    	this.addNumeratorUnit(prefix, unit, DEFAULT_EXPONENT);
    }
    
    public void addNumeratorUnit(Unit unit, int exponent) {
    	this.addNumeratorUnit(null, unit, exponent);
    }
    
    public void addNumeratorUnit(Prefix prefix, Unit unit, int exponent) {
    	UnitObject object = new UnitObject(prefix, unit, exponent);
    	numeratorObjects.add(object);
        dimensions.multiply(object.getDimensions());
        magnitude *= object.getMagnitude();
    }
    
    public void addDenominatorUnit(Unit unit) {
    	this.addNumeratorUnit(null, unit, DEFAULT_EXPONENT);
    }
    
    public void addDenominatorUnit(Prefix prefix, Unit unit) {
    	this.addNumeratorUnit(prefix, unit, DEFAULT_EXPONENT);
    }
    
    public void addDenominatorUnit(Unit unit, int exponent) {
    	this.addNumeratorUnit(null, unit, exponent);
    }
    
    public void addDenominatorUnit(Prefix prefix, Unit unit, int exponent) {
    	UnitObject object = new UnitObject(prefix, unit, exponent);
    	denominatorObjects.add(object);
        dimensions.divide(object.getDimensions());
        magnitude /= object.getMagnitude();
    }

    public void divide(DimensionObject calculator) {
    	this.numeratorObjects.addAll(calculator.getDenominatorObjects());
    	this.denominatorObjects.addAll(calculator.getNumeratorObjects());
    	this.magnitude /= calculator.getMagnitude();
    	this.dimensions.divide(calculator.getDimensions());	
    }
    
    public List<UnitObject> getNumeratorObjects() {
    	return numeratorObjects;
    }
    
    public List<UnitObject> getDenominatorObjects() {
    	return denominatorObjects;
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
        for (UnitObject object : numeratorObjects)
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
        
        if (str.isEmpty())
        	return str;
        return str.substring(0, str.length() - 1);
    }
    
    public String toExtendedString() {
        String str = "";
        for (UnitObject object : numeratorObjects)
            str += object.toExtendedString() + " ";
        if (!denominatorObjects.isEmpty()) {
        	str += "/ ";
            for (UnitObject object : denominatorObjects)
                str += object.toExtendedString() + " ";
        }
        
        if (str.isEmpty())
        	return str;
        return str.substring(0, str.length() - 1);
    }
    
}
