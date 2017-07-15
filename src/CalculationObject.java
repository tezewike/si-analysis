
public class CalculationObject {

    private Unit unit = null;
    private Prefix prefix = null;
    private int exponent = 1;
    
    public CalculationObject(Unit unit) {
        this.unit = unit;
    }
    
    public CalculationObject(Unit unit, int exponent) {
        this.unit = unit;
        this.exponent = exponent;
    }

    public CalculationObject(Prefix prefix, Unit unit) {
        this.prefix = prefix;
        this.unit = unit;
    }
    
    public CalculationObject(Prefix prefix, Unit unit, int exponent) {
        this.prefix = prefix;
        this.unit = unit;
        this.exponent = exponent;
    }
    
    public boolean isSameUnit(CalculationObject calcObj) {
        if (this.prefix == calcObj.prefix && this.unit == calcObj.unit)
            return true;
        return false;
    }
    
}
