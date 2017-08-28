import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class Prefix {

    private static final Hashtable<String, Prefix> prefixTable = new Hashtable<String, Prefix>();

    private final String name;
    private final String symbol;
    private final double magnitude;

    protected Prefix(String name, String symbol, double magnitude) {
        this.name = name;
        this.symbol = symbol;
        this.magnitude = magnitude;

        if (!prefixTable.containsKey(symbol))
            prefixTable.put(symbol, this);

    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getMagnitude() {
        return magnitude;
    }

    @Override
    public String toString() {
        return getName() + ": " + magnitude;
    }
    
    public static Prefix get(String key) {
        return prefixTable.get(key);
    }
    
    public static List<Prefix> getAll() {
        return new ArrayList<Prefix>(prefixTable.values());
    }
    
    protected static void clear() {
    	prefixTable.clear();
    }

    static final class PrefixComparator implements Comparator<Prefix> {

        @Override
        public int compare(Prefix lhs, Prefix rhs) {
            return Double.compare(rhs.getMagnitude(), lhs.getMagnitude());
        }

    }

}
