import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class Unit {
    
    private static final Hashtable<String, Unit> unitTable = new Hashtable<String, Unit>();
    
    private final String[] names;
    private final String[] symbols;
    private final double magnitude;
    private final int[] unitArray;
    
    protected Unit(String[] names, String[] symbols, double magnitude, int[] unitArray) {
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
        return unitArray;
    }
    
    public static Unit get(String key) {
        return unitTable.get(key);
    }
    
    static final class UnitComparator implements Comparator<Unit> {

        @Override
        public int compare(Unit lhs, Unit rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }

    }
    
}