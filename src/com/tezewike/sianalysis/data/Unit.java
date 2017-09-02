package com.tezewike.sianalysis.data;

// TODO ~ License Statement

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

/**
 * This class consists of methods used to obtain information (such as the name
 * or symbol) of a specified unit. All instances of units are prepopulated from
 * the database, and their values are final. If you wish to change the values,
 * keys, names, etc. of the unit, it must be done from the database itself.
 * 
 * @see DataLoader
 */
public class Unit {

	/**
	 * This is a map that stores every unit in the database for quick access via key
	 */
	private static final Hashtable<String, Unit> unitTable = new Hashtable<String, Unit>();

	/**
	 * An array of the names possessed by the unit. If the array's size is greater
	 * than one, the name at the zero-index is the main name.
	 */
	private final String[] names;

	/**
	 * An array of the symbols possessed by the unit. If the array's size is greater
	 * than one, the symbol at the zero-index is the main symbol. Each symbol
	 * doubles as a key for the {@link #get(String)} method, and therefore are all
	 * unique.
	 */
	private final String[] symbols;

	/**
	 * The conversion factor from this unit to the appropriate SI unit.
	 */
	private final double magnitude;

	/**
	 * A DimensionArray representing the base physical quantities of the unit.
	 * 
	 * @see DimensionArray
	 * @see DimensionArray.DerivedMeasures
	 */
	private final DimensionArray.DerivedMeasures unitArray;

	/**
	 * Creates an instance of the unit and adds it to the map of all units.
	 * 
	 * @param names the various names of the unit
	 * @param symbols the various symbols of the unit
	 * @param magnitude the value of the unit when converted to SI
	 * @param unitArray the DimensionArray representation of the base units
	 */
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

	/**
	 * Returns the main name of the unit.
	 * 
	 * @return the name of the unit
	 */
	public String getName() {
		return names[0];
	}

	/**
	 * Returns a list of all the names assigned to the unit.
	 * 
	 * @return a list of names of the unit
	 */
	public List<String> getNames() {
		return (List<String>) Arrays.asList(names);
	}

	/**
	 * Returns the main symbol of the unit.
	 * 
	 * @return the symbol of the unit
	 */
	public String getSymbol() {
		return symbols[0];
	}

	/**
	 * Returns a list of all the symbols assigned to the unit.
	 * 
	 * @return a list of symbols of the unit
	 */
	public List<String> getSymbols() {
		return (List<String>) Arrays.asList(symbols);
	}

	/**
	 * Returns the conversion factor of the unit to SI units.
	 * 
	 * @return the conversion factor of the unit
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * Returns the DimensionArray representation of the unit.
	 * 
	 * @return the DimensionArray representation of the units
	 */
	public DimensionArray getDimensions() {
		return unitArray.getDimensions();
	}

	@Override
	public String toString() {
		return getName() + ": " + magnitude + " | " + unitArray;
	}

	/**
	 * Returns the unit corresponding to the specified key. The key for a unit is
	 * one of the unit's designated symbols. As such, a unit may have multiple valid
	 * keys.
	 * 
	 * @param key the symbol of the unit
	 * @return the unit corresponding to the key; null otherwise
	 */
	public static Unit get(String key) {
		return unitTable.get(key);
	}

	/**
	 * Returns a list of all units in the unit map.
	 * 
	 * @return a list of all units
	 */
	public static List<Unit> getAll() {
		return new ArrayList<Unit>(unitTable.values());
	}

	/**
	 * Clears the unit map. Should not be used unless reloading the unit data from
	 * the {@link DataLoader} class.
	 * 
	 * @see DataLoader#load()
	 */
	protected static void clear() {
		unitTable.clear();
	}

	/**
	 * An enumeration used to designate a unit system. This includes the
	 * International System of Units, the Centimeter-Gram-Second system, etc.
	 */
	public enum System {
		/**
		 * A modern form of the metric system and the most widely used worldwide and in
		 * scientific communities. This is the system of units for which the application
		 * derives all other units.
		 */
		INTERNATIONAL_SYSTEM("International System of Units", "SI", "m", "kg", "s", "A", "K", "mol", "cd"),

		/**
		 * A variant to the metric system.
		 */
		CENTIMETER_GRAM_SECOND("Centimeter-Gram-Second", "CGS", "cm", "g", "s", "A", "K", "mol", "cd");

		private final String name;
		private final String abbreviation;
		private final Unit[] units;

		/**
		 * Creates an enumeration element for the designated system of units.
		 * 
		 * @param name the name of the unit system
		 * @param abbreviation the abbreviation; null otherwise
		 * @param unitKeys the unit representing each base physical quantity of the unit
		 *            system
		 */
		private System(String name, String abbreviation, String... unitKeys) {
			this.name = name;
			this.abbreviation = abbreviation;

			Unit unit = null;
			this.units = new Unit[DimensionArray.ARRAY_LENGTH];
			for (int i = 0; i < unitKeys.length; i++) {
				unit = Unit.get(unitKeys[i]);
				this.units[i] = unit;
			}

		}

		/**
		 * Returns the units associated with the unit system in the same order as the
		 * {@link DimensionArray} class dictates.
		 * 
		 * @see DimensionArray.Measures
		 * @return the units associated with the unit system
		 */
		public Unit[] getUnits() {
			return units;
		}

		/**
		 * Returns the number of elements in this enumeration.
		 * 
		 * @return the number of elements
		 */
		public static int size() {
			return System.values().length;
		}

		@Override
		public String toString() {
			return abbreviation + ": " + name;
		}

	}

	/**
	 * A comparator class used for sorting Units by name.
	 */
	static final class UnitComparator implements Comparator<Unit> {

		@Override
		public int compare(Unit lhs, Unit rhs) {
			return lhs.getName().compareTo(rhs.getName());
		}

	}

}
