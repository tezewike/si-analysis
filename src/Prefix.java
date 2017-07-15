import java.util.Comparator;
import java.util.Hashtable;

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

    public static Prefix get(String key) {
        return prefixTable.get(key);
    }

    static final class PrefixComparator implements Comparator<Prefix> {

        @Override
        public int compare(Prefix lhs, Prefix rhs) {
            return (int) (rhs.getMagnitude() - lhs.getMagnitude());
        }

    }

}
