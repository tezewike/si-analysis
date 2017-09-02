package com.tezewike.sianalysis.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class is used to populate data from a .json file for use of the
 * {@link Unit} and {@link Prefix} classes.
 * 
 * @see Unit
 * @see Prefix
 */
public class DataLoader {

	// The following are keys used to read in the JSON data
	private static final String DATA_FILENAME = "unit.json";

	private static final String PREFIX_ARRAY_KEY = "prefixes";
	private static final String PREFIX_NAME_KEY = "name";
	private static final String PREFIX_SYMBOL_KEY = "symbol";

	private static final String UNIT_NAME_KEY = "names";
	private static final String UNIT_ARRAY_KEY = "units";
	private static final String UNIT_SYMBOL_KEY = "symbols";
	private static final String DIMENSION_KEY = "dimension";
	private static final String MAGNITUDE_KEY = "magnitude";

	// Prevents default public constructor so that an instance cannot be created
	private DataLoader() {}

	// Data is read as an InputStream so that the code still works as a .jar
	private static InputStream stream = DataLoader.class.getResourceAsStream(DATA_FILENAME);
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
	private static JSONObject data = null;

	static {
		try {
			Class.forName(DataLoader.class.getName());
		} catch (ClassNotFoundException cnfe) {
			// TODO Auto-generated catch block
			cnfe.printStackTrace();
		}

		JSONParser parser = new JSONParser();

		try {
			data = (JSONObject) parser.parse(reader);
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		} catch (ParseException pe) {
			// TODO Auto-generated catch block
			pe.printStackTrace();
		}
	}

	/**
	 * Checks if the data from the JSON file has loaded.
	 * 
	 * @return true if the data has loaded; false otherwise
	 * @see #load()
	 */
	public static boolean isLoaded() {
		return data != null;
	}

	/**
	 * Clears the previously loaded data, if any, and loads it from the .json file.
	 */
	public static void load() {
		Unit.clear();
		Prefix.clear();

		loadPrefixes();
		loadUnits();
	}

	@SuppressWarnings("unchecked")
	private static void loadPrefixes() {
		JSONArray prefixes = (JSONArray) data.get(PREFIX_ARRAY_KEY);

		Iterator<JSONObject> iterator = prefixes.iterator();
		JSONObject currentObject;

		String name;
		String symbol;
		double magnitude;

		while (iterator.hasNext()) {
			currentObject = iterator.next();

			name = (String) currentObject.get(PREFIX_NAME_KEY);
			symbol = ((String) currentObject.get(PREFIX_SYMBOL_KEY));
			magnitude = (double) currentObject.get(MAGNITUDE_KEY);

			new Prefix(name, symbol, magnitude);
		}
		
	}

	@SuppressWarnings("unchecked")
	private static void loadUnits() {
		JSONArray units = (JSONArray) data.get(UNIT_ARRAY_KEY);

		Iterator<JSONObject> iterator = units.iterator();
		JSONObject currentObject;

		String[] names, symbols;
		// String system;
		DimensionArray.DerivedMeasures dimension;
		double magnitude;

		while (iterator.hasNext()) {
			currentObject = iterator.next();

			names = toStringArray((JSONArray) currentObject.get(UNIT_NAME_KEY));
			symbols = toStringArray((JSONArray) currentObject.get(UNIT_SYMBOL_KEY));
			dimension = DimensionArray.DerivedMeasures.getEnum((String) currentObject.get(DIMENSION_KEY));
			// system = (String) currentObject.get("system");
			magnitude = (double) currentObject.get(MAGNITUDE_KEY);

			new Unit(names, symbols, magnitude, dimension);
		}

	}

	/**
	 * Creates a String array from a JSONArray.
	 * 
	 * @param array the JSONArray to convert
	 * @return a String array
	 */
	private static String[] toStringArray(JSONArray array) {
		int size = array.size();
		String[] arr = new String[size];

		for (int i = 0; i < size; i++) {
			arr[i] = (String) array.get(i);
		}

		return arr;
	}

}
