import java.util.ArrayList;
import java.util.List;

public class CalculationObject {

	private static final int DEFAULT_EXPONENT = 1; 
	private static final Unit.System  DEFAULT_SYSTEM = Unit.System.INTERNATIONAL_SYSTEM;
	
    private final List<CalculationUnitObject> numeratorObjects;
    private final List<CalculationUnitObject> denominatorObjects;
    private double magnitude = 1.0;
    private DimensionArray dimensions;
    
    public CalculationObject() {
        this.numeratorObjects = new ArrayList<CalculationUnitObject>();
        this.denominatorObjects = new ArrayList<CalculationUnitObject>();
        this.dimensions = new DimensionArray();
    }
    
    public CalculationObject(double magnitude) {
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
    	CalculationUnitObject object = new CalculationUnitObject(prefix, unit, exponent);
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
    	CalculationUnitObject object = new CalculationUnitObject(prefix, unit, exponent);
    	denominatorObjects.add(object);
        dimensions.divide(object.getDimensions());
        magnitude /= object.getMagnitude();
    }

    public void divide(CalculationObject calculator) {
    	this.numeratorObjects.addAll(calculator.getDenominatorObjects());
    	this.denominatorObjects.addAll(calculator.getNumeratorObjects());
    	this.magnitude /= calculator.getMagnitude();
    	this.dimensions.divide(calculator.getDimensions());	
    }
    
    public List<CalculationUnitObject> getNumeratorObjects() {
    	return numeratorObjects;
    }
    
    public List<CalculationUnitObject> getDenominatorObjects() {
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
        for (CalculationUnitObject object : numeratorObjects)
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
        for (CalculationUnitObject object : numeratorObjects)
            str += object.toExtendedString() + " ";
        return str.substring(0, str.length() - 1);
    }
    
}
