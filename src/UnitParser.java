import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnitParser {
	
	// TODO ~ Throw exception if data isn't loaded
	
	public DimensionObject parse(String input) {
        DimensionObject object = new DimensionObject();
		return parseUnits(input, object);
	}
	
	public DimensionObject parse(String number, String input) {
        DimensionObject object = new DimensionObject(parseScalar(number));
		return parseUnits(input, object);
	}
	
    private DimensionObject parseUnits(String input, DimensionObject object) {
        Scanner scanner = new Scanner(input);
        String regex = "[\\w_]+(\\^-?\\d+)?";
        String text;
        
        while (scanner.hasNext()) {
            text = scanner.next();
            Matcher matcher = Pattern.compile(regex).matcher(text);
            
            if (matcher.find())
                text = matcher.group();
            
            findUnit(text, object);
        }
        
        scanner.close();
        
        return object;
    }
    
    public double parseScalar(String input) {
    	return Double.parseDouble(input);
    }
        
    private void findUnit(String text, DimensionObject object) {
    	if (object == null)
    		return;
    	
        String[] split = text.split("\\^");
        String unitInput = split[0];
        Integer exponentInput = 1;
        
        int len = unitInput.length();
        
        if (split.length > 1 ) {
            exponentInput = new Integer(split[1]);
        }
        
        Unit unit = Unit.get(unitInput);
        Prefix prefix = null;
        
        if (unit != null) {
            object.addNumeratorUnit(unit, exponentInput);
        } else {
            if (len > 1) {
                prefix = Prefix.get(unitInput.substring(0, 1));
                unit = Unit.get(unitInput.substring(1));
                if (prefix != null && unit != null)
                	object.addNumeratorUnit(prefix, unit, exponentInput); 
            }
        }

    }
    
}
