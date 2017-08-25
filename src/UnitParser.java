import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnitParser {
	
	private static final double DEFAULT_MAGNITUDE = 1.0;
	// TODO ~ Throw exception if data isn't loaded
	
	public DimensionObject parse(String input) {
        DimensionObject object = new DimensionObject();
		return parseUnits(input, object, true);
	}
	
	public DimensionObject parse(String number, String input) {
        DimensionObject object = new DimensionObject(parseScalar(number));
		return parseUnits(input, object, true);
	}
	
	public DimensionObject parse(String number1, String input1, String number2, String input2) {
        DimensionObject object = new DimensionObject(parseScalar(number1), parseScalar(number2));
		object = parseUnits(input1, object, true);
		return parseUnits(input2, object, false);
	}
	
    private DimensionObject parseUnits(String input, DimensionObject object, boolean numerator) {
        Scanner scanner = new Scanner(input);
        String regex = "[\\w_]+(\\^-?\\d+)?";
        String text;
        
        while (scanner.hasNext()) {
            text = scanner.next();
            Matcher matcher = Pattern.compile(regex).matcher(text);
            
            if (matcher.find())
                text = matcher.group();
            
            findUnit(text, object, numerator);
        }
        
        scanner.close();
        
        return object;
    }
    
    public double parseScalar(String input) {
    	try {
    		return Double.parseDouble(input);
    	} catch (Exception e) {
    		return DEFAULT_MAGNITUDE;
    	}
    }
        
    private void findUnit(String text, DimensionObject object, boolean numerator) {
    	if (object == null)
    		return;
    	
        String[] split = text.split("\\^");
        String unitInput = split[0];
        Integer exponentInput = 1;
        
        int len = unitInput.length();
        
        if (split.length > 1) {
            exponentInput = new Integer(split[1]);
        }
        
        Unit unit = Unit.get(unitInput);
        Prefix prefix = null;
        
        if (unit != null) {
        	if (numerator)
        		object.addNumeratorUnit(unit, exponentInput);
        	else 
        		object.addDenominatorUnit(unit, exponentInput);
        } else {
            if (len > 1) {
                prefix = Prefix.get(unitInput.substring(0, 1));
                unit = Unit.get(unitInput.substring(1));
                if (prefix != null && unit != null) {
                	if (numerator)
                		object.addNumeratorUnit(prefix, unit, exponentInput); 
                	else 
                		object.addDenominatorUnit(prefix, unit, exponentInput);
                }
            }
        }

    }
    
}
