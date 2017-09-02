package com.tezewike.sianalysis.data;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

/**
 * This class consists of methods used to obtain information of a specified
 * prefix. All instances of prefixes are prepopulated from the database, and
 * their values are final. If you wish to change the values, keys, names, etc.
 * of the unit, it must be done from the database itself.
 * 
 * @see DataLoader
 */
public class Prefix {

	/**
	 * This is a map that stores every prefix in the database for quick access via
	 * key
	 */
	private static final Hashtable<String, Prefix> prefixTable = new Hashtable<String, Prefix>();

	/**
	 * The name of the prefix.
	 */
	private final String name;

	/**
	 * The symbol of the prefix. This value serves as the key for the
	 * {@link #get(String)} method.
	 */
	private final String symbol;

	/**
	 * The conversion factor of this prefix
	 */
	private final double magnitude;

	/**
	 * Creates an instance of the prefix and adds it to the map of all prefixes.
	 * 
	 * @param name the name of the prefix
	 * @param symbol the symbol of the prefix
	 * @param magnitude the conversion factor of the prefix
	 */
	protected Prefix(String name, String symbol, double magnitude) {
		this.name = name;
		this.symbol = symbol;
		this.magnitude = magnitude;

		if (!prefixTable.containsKey(symbol))
			prefixTable.put(symbol, this);
	}

	/**
	 * Returns the name of the prefix.
	 * 
	 * @return the name of the prefix
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the symbol of the prefix.
	 * 
	 * @return the symbol of the prefix.
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Returns the conversion factor of the prefix.
	 * 
	 * @return the conversion factor of the prefix.
	 */
	public double getMagnitude() {
		return magnitude;
	}

	@Override
	public String toString() {
		return getName() + ": " + magnitude;
	}

	/**
	 * Returns the prefix corresponding to the specified key. The key for a prefix
	 * is the prefix's designated symbols.
	 * 
	 * @param key the symbol of the prefix
	 * @return the unit corresponding to the key; null otherwise
	 */
	public static Prefix get(String key) {
		return prefixTable.get(key);
	}

	/**
	 * Returns a list of all prefixes in the prefix map.
	 * 
	 * @return a list of all prefixes
	 */
	public static List<Prefix> getAll() {
		return new ArrayList<Prefix>(prefixTable.values());
	}

	/**
	 * Clears the prefix map. Should not be used unless reloading the unit data from
	 * the {@link DataLoader} class.
	 * 
	 * @see DataLoader#load()
	 */
	protected static void clear() {
		prefixTable.clear();
	}

	/**
	 * A comparator class used for sorting Prefixes by the value of their factor.
	 */
	static final class PrefixComparator implements Comparator<Prefix> {

		@Override
		public int compare(Prefix lhs, Prefix rhs) {
			return Double.compare(rhs.getMagnitude(), lhs.getMagnitude());
		}

	}

}
