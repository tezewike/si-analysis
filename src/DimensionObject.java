import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DimensionObject {

	public static final String MAGNITUDE_KEY = "magnitude";
	public static final String NUMERATOR_KEY = "numerator";
	public static final String DENOMINATOR_KEY = "denominator";
	public static final String INPUT_KEY = "input";
	public static final String OUTPUT_KEY = "output";
	public static final String UNIT_ENTRY_KEY = "units";
	public static final String ENTRY_NAME_KEY = "name";
	public static final String ENTRY_SYMBOL_KEY = "symbol";
	public static final String ENTRY_EXPONENT_KEY = "exp";
	
	private static final int DEFAULT_EXPONENT = 1; 
	private static final Unit.System  DEFAULT_SYSTEM = Unit.System.INTERNATIONAL_SYSTEM;
	
    private final List<UnitObject> numeratorObjects;
    private final List<UnitObject> denominatorObjects;
    private DimensionArray dimensions;
    
    private double numeratorMagnitude = 1.0;
    private double denominatorMagnitude = 1.0;
    private double finalMagnitude = 1.0;
    
    public DimensionObject() {
        this.numeratorObjects = new ArrayList<UnitObject>();
        this.denominatorObjects = new ArrayList<UnitObject>();
        this.dimensions = new DimensionArray();
    }
    
    public DimensionObject(double magnitude) {
    	this();
    	this.numeratorMagnitude = magnitude;
    	this.finalMagnitude = numeratorMagnitude;
    }
    
    public DimensionObject(double numeratorMagnitude, double denominatorMagnitude) {
    	this();
    	this.numeratorMagnitude = numeratorMagnitude;
    	this.denominatorMagnitude = denominatorMagnitude;
    	this.finalMagnitude = numeratorMagnitude / denominatorMagnitude;
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
        finalMagnitude *= object.getMagnitude();
    }
    
    public void addDenominatorUnit(Unit unit) {
    	this.addDenominatorUnit(null, unit, DEFAULT_EXPONENT);
    }
    
    public void addDenominatorUnit(Prefix prefix, Unit unit) {
    	this.addDenominatorUnit(prefix, unit, DEFAULT_EXPONENT);
    }
    
    public void addDenominatorUnit(Unit unit, int exponent) {
    	this.addDenominatorUnit(null, unit, exponent);
    }
    
    public void addDenominatorUnit(Prefix prefix, Unit unit, int exponent) {
    	UnitObject object = new UnitObject(prefix, unit, exponent);
    	denominatorObjects.add(object);
        dimensions.divide(object.getDimensions());
        finalMagnitude /= object.getMagnitude();
    }
/*
    public void divide(DimensionObject calculator) {
    	this.numeratorObjects.addAll(calculator.getDenominatorObjects());
    	this.denominatorObjects.addAll(calculator.getNumeratorObjects());
    	this.finalMagnitude *= calculator.getMagnitude();
    	this.denominatorMagnitude *= calculator.getInitialMagnitude();
    	this.dimensions.divide(calculator.getDimensions());	
    }
 */   
    public List<UnitObject> getNumeratorObjects() {
    	return numeratorObjects;
    }
    
    public List<UnitObject> getDenominatorObjects() {
    	return denominatorObjects;
    }
    
    // TODO ~ Number rounding / formatting, possibly as separate method
    public double getMagnitude() {
        return finalMagnitude;
    }
    
    public double getMagnitude(Unit.System system) {
        double value = this.getMagnitude();
        int[] array = dimensions.toIntegerArray();
        Unit[] units = system.getUnits();
        
        for (int i = 0; i < DimensionArray.ARRAY_LENGTH; i++) {
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
        int[] array = dimensions.toIntegerArray();
        
        String str = "";
        for (int i = 0; i < units.length; i++) {
            if (array[i] != 0)
                str += units[i].getName() + Utils.toSuperscript(array[i]) + " ";
        }
        
        if (str.isEmpty())
        	return str;
        return str.substring(0, str.length() - 1);
    }
    
    public JSONObject toJSONObject() {
    	JSONObject object = new JSONObject();
    	JSONObject input = new JSONObject();
    	JSONObject output = new JSONObject();
    	
    	JSONObject numerator = new JSONObject();
    	JSONObject denominator = new JSONObject();
    	
    	numerator.put(MAGNITUDE_KEY, numeratorMagnitude);   		
    	numerator.put(UNIT_ENTRY_KEY, unitObjectsToJSON(numeratorObjects));
    	
    	denominator.put(MAGNITUDE_KEY, denominatorMagnitude); 		
   		denominator.put(UNIT_ENTRY_KEY, unitObjectsToJSON(denominatorObjects));
    	
    	input.put(NUMERATOR_KEY, numerator);
    	input.put(DENOMINATOR_KEY, denominator);
    	
    	output.put(MAGNITUDE_KEY, finalMagnitude);
    	output.put(UNIT_ENTRY_KEY, outputToJSON());
    	
    	object.put(INPUT_KEY, input);
    	object.put(OUTPUT_KEY, output);
    	
    	return object;
    }
    
    private JSONArray unitObjectsToJSON(List<UnitObject> list) {
    	JSONArray units = new JSONArray();
    	
		for (UnitObject unitObject : list) {
			units.add(unitObject.toJSONObject());
		}
    	
    	return units;
    }
    
    private JSONArray outputToJSON() {
        Unit[] units = Unit.System.INTERNATIONAL_SYSTEM.getUnits();
        int[] array = dimensions.toIntegerArray();
        
        JSONArray outputUnits = new JSONArray();
        JSONObject object;
        
        for (int i = 0; i < units.length; i++) {
            if (array[i] != 0) {
            	object = new JSONObject();
            	object.put(ENTRY_NAME_KEY, units[i].getName());
            	object.put(ENTRY_SYMBOL_KEY, units[i].getSymbol());
            	object.put(ENTRY_EXPONENT_KEY, array[i]);
            	outputUnits.add(object);
            }
            
        }
        
        return outputUnits; 
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
