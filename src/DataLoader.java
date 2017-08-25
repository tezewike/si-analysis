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
 */
public class DataLoader {

    private static InputStream resource = DataLoader.class.getResourceAsStream("unit.json");
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
    private static JSONObject data;
    
    static {
        
        try {
            Class.forName(DataLoader.class.getName());
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        JSONParser parser = new JSONParser();
        
        try {
            data = (JSONObject) parser.parse(reader);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /*
    public static void initializeDimensions() {
        JSONArray dimensions = (JSONArray) data.get("dimensions");
        
        Iterator<JSONObject> iterator = dimensions.iterator();
        JSONObject currentObject;
        
        String name;
        int mass, length, time, current, temp;
        
        while (iterator.hasNext()) {
            currentObject = iterator.next();
            
            name = (String) currentObject.get("dimen");
            
            mass = tryValue(currentObject, "mass");
            length = tryValue(currentObject, "length");
            time = tryValue(currentObject, "time");
            current = tryValue(currentObject, "current");
            temp = tryValue(currentObject, "temp");
            
            new Dimension(name, mass, length, time, current, temp);
        }
    }
    */
    
    public static void initializePrefixes() {
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
    
    public static void initializeUnits() {
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
        
    private static int tryValue(JSONObject object, String key) {
        try {
            return ((Long) object.get(key)).intValue();
        } catch (NullPointerException npe){
            return 0;
        }        
    }
    
    
}
