import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DimensionObject {

	private static final int DEFAULT_EXPONENT = 1; 
	private static final Unit.System  DEFAULT_SYSTEM = Unit.System.INTERNATIONAL_SYSTEM;
	
    private final List<UnitObject> numeratorObjects;
    private final List<UnitObject> denominatorObjects;
    private double numeratorMagnitude = 1.0;
    private double denominatorMagnitude = 1.0;
    private double finalMagnitude = 1.0;
    private DimensionArray dimensions;
    
    public DimensionObject() {
        this.numeratorObjects = new ArrayList<UnitObject>();
        this.denominatorObjects = new ArrayList<UnitObject>();
        this.dimensions = new DimensionArray();
    }
    
    public DimensionObject(double magnitude) {
    	this();
    	this.numeratorMagnitude = magnitude;
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
        finalMagnitude /= object.getMagnitude();
    }

    public void divide(DimensionObject calculator) {
    	this.numeratorObjects.addAll(calculator.getDenominatorObjects());
    	this.denominatorObjects.addAll(calculator.getNumeratorObjects());
    	this.finalMagnitude *= calculator.getMagnitude();
    	this.denominatorMagnitude *= calculator.getInitialMagnitude();
    	this.dimensions.divide(calculator.getDimensions());	
    }
    
    public List<UnitObject> getNumeratorObjects() {
    	return numeratorObjects;
    }
    
    public List<UnitObject> getDenominatorObjects() {
    	return denominatorObjects;
    }
    
    public double getMagnitude() {
        return finalMagnitude;
    }
    
    private double getInitialMagnitude() {
    	return numeratorMagnitude;
    }
    
    public double getMagnitude(Unit.System system) {
        double value = this.getMagnitude();
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
    
    public JSONObject toJSONObject() {
    	JSONObject object = new JSONObject();
    	JSONObject input = new JSONObject();
    	JSONObject output = new JSONObject();
    	
    	JSONObject numerator = new JSONObject();
    	JSONObject denominator = new JSONObject();
    	JSONObject results = new JSONObject();
    	
    	numerator.put("magnitude", numeratorMagnitude);
    	if (!numeratorObjects.isEmpty()) {    		
    		numerator.put("units", unitObjectsToJSON(numeratorObjects));
    	}
    	
    	denominator.put("magnitude", denominatorMagnitude);
    	if (!denominatorObjects.isEmpty()) {    		
    		denominator.put("units", unitObjectsToJSON(denominatorObjects));
    	}
    	
    	input.put("numerator", numerator);
    	input.put("denominator", denominator);
    	
    	results.put("magnitude", finalMagnitude);
    	results.put("units", outputToJSON());
    	
    	object.put("input", input);
    	object.put("output", results);
    	
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
        int[] array = dimensions.getBaseArray();
        
        JSONArray outputUnits = new JSONArray();
        JSONObject object;
        
        for (int i = 0; i < units.length; i++) {
            if (array[i] != 0) {
            	object = new JSONObject();
            	object.put("unit", units[i].getName());
            	object.put("symbol", units[i].getSymbol());
            	object.put("exp", array[i]);
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
