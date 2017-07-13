public class UnitBuilder {
    private String[] names = null;
    private String[] symbols = null;
    private double magnitude = 1.0;
    private baseUnitArray = null;
    
    public UnitBuilder withNames(String[] names) {
        this.names = names;
        return this;
    }
    
    public UnitBuilder withSymbols(String[] symbols) {
        this.symbols = symbols;
        return this;
    }
    
    public UnitBuilder withMagnitude(String[] magnitude) {
        this.magnitude = magnitude;
        return this;
    }
    
    public UnitBuilder withUnitArray(String[] unitArray) {
        this.names = unitArray;
        return this;
    }

    // TODO
    public Unit build() {
        return null;
    }
    
}
