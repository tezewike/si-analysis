import org.json.simple.JSONObject;

public class UnitObject {

	private static final int DEFAULT_EXPONENT = 1; 
	
    private Unit unit;
    private Prefix prefix;
    private int exponent;

    public UnitObject(Unit unit) {
        this(null, unit, DEFAULT_EXPONENT);
    }

    public UnitObject(Unit unit, int exponent) {
        this(null, unit, exponent);
    }

    public UnitObject(Prefix prefix, Unit unit) {
        this(prefix, unit, DEFAULT_EXPONENT);
    }

    public UnitObject(Prefix prefix, Unit unit, int exponent) {
        this.prefix = prefix;
        this.unit = unit;
        this.exponent = exponent;
    }

    public boolean isSameUnit(UnitObject calcObj) {
        return (this.prefix == calcObj.prefix && this.unit == calcObj.unit);
    }
    
    public double getMagnitude() {
        double value = 1.0;
        
        if (prefix != null)
            value *= Math.pow(prefix.getMagnitude(), exponent);
        if (unit != null)
            value *= Math.pow(unit.getMagnitude(), exponent);
        
        return value;
    }
    
    public DimensionArray getDimensions() {  
        return unit.getDimensions().exponentiate(exponent);
    }
    
    @Override
    public String toString() {
        String str = "";
        
        if (prefix != null)
            str += prefix.getSymbol();
        if (unit != null)
            str += unit.getSymbol();
        if (exponent != 1)
            str += Utils.toSuperscript(exponent);
        
        return str;
    }
    
    public JSONObject toJSONObject() {
        String name = "";
        String symbol = "";
        
        if (prefix != null) {
            name += prefix.getName();
            symbol += prefix.getSymbol();
        }
        
        if (unit != null) {
            name += unit.getName();
            symbol += unit.getSymbol();
        }
        
        JSONObject object = new JSONObject();
        
        object.put(DimensionObject.ENTRY_NAME_KEY, name);
        object.put(DimensionObject.ENTRY_SYMBOL_KEY, symbol);
        object.put(DimensionObject.ENTRY_EXPONENT_KEY, exponent);
        
		return object;
    }

    public String toExtendedString() {
        String str = "(";
        
        if (prefix != null)
            str += prefix.getName();
        if (unit != null)
            str += unit.getName();
        str += ")";
        if (exponent != 1)
            str += Utils.toSuperscript(exponent);
        
        return str;
    }
    
}
