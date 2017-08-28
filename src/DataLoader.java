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

	// Prevents default public constructor so that an instance cannot be created 
	private DataLoader() {}
	
	// Data is read as an inputstream so that the code still works as a .jar
    private static InputStream resource = DataLoader.class.getResourceAsStream("unit.json");
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
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
    
    public static boolean isLoaded() {
    	return data != null;
    }
    
    public static void reload() {
    	Unit.clearUnits();
    	Prefix.clear();
    	
    	initializePrefixes();
    	initializeUnits();
    }

    private static void initializePrefixes() {
        JSONArray prefixes = (JSONArray) data.get("prefixes");
        
        Iterator<JSONObject> iterator = prefixes.iterator();
        JSONObject currentObject;

        String name;
        String symbol;
        double magnitude;
        
        while (iterator.hasNext()) {
            currentObject = iterator.next();
            
            name = (String) currentObject.get("name");
            symbol = ((String) currentObject.get("symbol"));
            magnitude = (double) currentObject.get("magnitude");
            
            new Prefix(name, symbol, magnitude);
        }
    }
    
    private static void initializeUnits() {
        JSONArray units = (JSONArray) data.get("units");
        
        Iterator<JSONObject> iterator = units.iterator();
        JSONObject currentObject;
        
        String[] names, symbols;
//        String system;
        DimensionArray.DerivedMeasures dimension;
        double magnitude;
        
        while (iterator.hasNext()) {
            currentObject = iterator.next();
            
            names = toStringArray((JSONArray) currentObject.get("names"));
            symbols = toStringArray((JSONArray) currentObject.get("symbols"));
            dimension = DimensionArray.DerivedMeasures.getEnum((String) currentObject.get("dimension"));
 //           system = (String) currentObject.get("system");
            magnitude = (double) currentObject.get("magnitude");
            
            new Unit(names, symbols, magnitude, dimension);
        }
        
    }
    
    private static String[] toStringArray(JSONArray array) {
        int size = array.size();
        String[] arr = new String[size];
        
        for (int i = 0; i < size; i++) {
            arr[i] = (String) array.get(i);
        }
        
        return arr;
    }           
        
}
