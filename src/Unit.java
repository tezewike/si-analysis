// TODO ~ License Statement

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

/**
 * This class consists of methods used to obtain information (such as the name or symbol) of
 * a specified unit. All instances of units are prepopulated from the database, and their 
 * values are final. If you wish to change the values, keys, names, etc. of the unit, it must be
 * done from the databse itself.
 * 
 * @see DataLoader
 */
public class Unit {

	/**
	 *  This is a map that stores every unit in  the database for quick access via key 
	 */
    private static final Hashtable<String, Unit> unitTable = new Hashtable<String, Unit>();

    /**
     * An array of the names possessed by the unit. If the array's size is greater than one,
     * the name at the zero-index is the main name.
     */
    private final String[] names;
    
    /**
     * An array of the symbols possessed by the unit. If the array's size is greater than one, 
     * the symbol at the zero-index is the main symbol. Each symbol doubles as a key for the
     * {@code Unit.get(key)} method, and therefore are all unique.
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
     * Creates an instance of the unit and adds it to the map.
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
     * @return the name of the unit
     */
    public String getName() {
        return names[0];
    }

    /**
     * Returns a list of all the names assigned to the unit.
     * @return a list of names of the unit
     */
    public List<String> getNames() {
        return (List<String>) Arrays.asList(names);
    }
    
    /**
     * Returns the main symbol of the unit.
     * @return the symbol of the unit
     */
    public String getSymbol() {
        return symbols[0];
    }
    
    /**
     * Returns a list of all the symbols assigned to the unit.
     * @return a list of symbols of the unit
     */
    public List<String> getSymbols() {
        return (List<String>) Arrays.asList(symbols);
    }

    /**
     * Returns the conversion factor of the unit to SI units
     * @return the conversion factor of the unit
     */
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * Returns the array representation of the unit.
     * @return the array representation of the units
     */
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
    
    protected static void clearUnits() {
    	unitTable.clear();
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
