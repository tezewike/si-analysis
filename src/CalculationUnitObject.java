
public class CalculationUnitObject {

	private static final int DEFAULT_EXPONENT = 1; 
	
    private Unit unit;
    private Prefix prefix;
    private int exponent;

    public CalculationUnitObject(Unit unit) {
        this(null, unit, DEFAULT_EXPONENT);
    }

    public CalculationUnitObject(Unit unit, int exponent) {
        this(null, unit, exponent);
    }

    public CalculationUnitObject(Prefix prefix, Unit unit) {
        this(prefix, unit, DEFAULT_EXPONENT);
    }

    public CalculationUnitObject(Prefix prefix, Unit unit, int exponent) {
        this.prefix = prefix;
        this.unit = unit;
        this.exponent = exponent;
    }

    public boolean isSameUnit(CalculationUnitObject calcObj) {
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
        return new DimensionArray(unit.getBaseArray()).exponentiate(exponent);
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
