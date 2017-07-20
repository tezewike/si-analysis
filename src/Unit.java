import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class Unit {

    private static final Hashtable<String, Unit> unitTable = new Hashtable<String, Unit>();

    private final String[] names;
    private final String[] symbols;
    private final double magnitude;
    private final DimensionArray.DerivedMeasures unitArray;

    protected Unit(String[] names, String[] symbols, double magnitude, DimensionArray.DerivedMeasures unitArray) {
        this.names = names;
        this.symbols = symbols;
        this.magnitude = magnitude;
        this.unitArray = unitArray;

        for (String symbol : symbols) {
            if (!unitTable.containsKey(symbol))
                unitTable.put(symbol, this);
        }

    }

    public String getName() {
        return names[0];
    }

    public List<String> getNames() {
        return (List<String>) Arrays.asList(names);
    }

    public String getSymbol() {
        return symbols[0];
    }

    public List<String> getSymbols() {
        return (List<String>) Arrays.asList(symbols);
    }

    public double getMagnitude() {
        return magnitude;
    }

    public int[] getBaseArray() {
        return unitArray.getBaseArray();
    }

    @Override
    public String toString() {
        return getName() + ": " + magnitude + " | " + unitArray;
    }

    public static Unit get(String key) {
        return unitTable.get(key);
    }

    public static List<Unit> getAll() {
        return new ArrayList<Unit>(unitTable.values());
    }
    
    public enum System {
        INTERNATIONAL_SYSTEM("International System of Units", "m", "kg", "s", "A", "K", "mol", "cd"),
        CENTIMETER_GRAM_SECOND("Centimeter-Gram-Second", "cm", "g", "s", "A", "K", "mol", "cd");
        
        private final String name;
        private final Unit[] units;
        
        private System(String name, String... unitKeys) {
           // if (unitKeys.length != DimensionArray.ARRAY_SIZE)
           //     throw new Exception("invalid arguments for unit system");
            
            Unit unit = null;
            this.units = new Unit[DimensionArray.ARRAY_SIZE];
            for (int i = 0; i < unitKeys.length; i++) {
                unit = Unit.get(unitKeys[i]);
               // if (unit == null)
               //     throw new Exception("invalid key, \"" + unitKeys[i] + "\", for unit system");
               // else 
                    this.units[i] = unit;
            }
            
            this.name = name;
        }
        
        public Unit[] getUnits() {
            return units;
        }
        
        public static int size() {
            return System.values().length;
        }
        
        @Override
        public String toString() {
        	return name;
        }
        
    }
    
    static final class UnitComparator implements Comparator<Unit> {

        @Override
        public int compare(Unit lhs, Unit rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }

    }

}
