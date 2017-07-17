
public class CalculationUnitObject {

    private Unit unit = null;
    private Prefix prefix = null;
    private int exponent = 1;

    public CalculationUnitObject(Unit unit) {
        this.unit = unit;
    }

    public CalculationUnitObject(Unit unit, int exponent) {
        this.unit = unit;
        this.exponent = exponent;
    }

    public CalculationUnitObject(Prefix prefix, Unit unit) {
        this.prefix = prefix;
        this.unit = unit;
    }

    public CalculationUnitObject(Prefix prefix, Unit unit, int exponent) {
        this.prefix = prefix;
        this.unit = unit;
        this.exponent = exponent;
    }

    public boolean isSameUnit(CalculationUnitObject calcObj) {
        if (this.prefix == calcObj.prefix && this.unit == calcObj.unit)
            return true;
        return false;
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
