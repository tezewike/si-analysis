public class UnitBuilder {
    private String[] names = null;
    private String[] symbols = null;
    private double magnitude = 1.0;
    private DimensionArray.DerivedMeasures baseUnitArray = null;
    
    public UnitBuilder withNames(String[] names) {
        this.names = names;
        return this;
    }
    
    public UnitBuilder withSymbols(String[] symbols) {
        this.symbols = symbols;
        return this;
    }
    
    public UnitBuilder withMagnitude(double magnitude) {
        this.magnitude = magnitude;
        return this;
    }
    
    public UnitBuilder withUnitArray(DimensionArray.DerivedMeasures unitArray) {
        this.baseUnitArray = unitArray;
        return this;
    }

    // TODO
    public Unit build() {
        return new Unit(names, symbols, magnitude, baseUnitArray);        
    }
    
}
