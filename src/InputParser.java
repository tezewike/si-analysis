import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser {
    
    private CalculationObject object = null;
    
    public InputParser(String input) {
        this.object = new CalculationObject();
        parse(input);
    }
    
    private void parse(String input) {
        Scanner scanner = new Scanner(input);
        String regex = "[\\w_]+(\\^-?\\d+)?";
        String text;
        
        CalculationUnitObject unitObject = null;
        
        while (scanner.hasNext()) {
            text = scanner.next();
            Matcher matcher = Pattern.compile(regex).matcher(text);
            
            if (matcher.find())
                text = matcher.group();
            
            unitObject = findUnit(text);
            
            if (unitObject != null)
                object.addObject(unitObject);
        }
        
        scanner.close();
    }
        
    private CalculationUnitObject findUnit(String text) {
        String[] split = text.split("\\^");
        String unitInput = split[0];
        Integer exponentInput = 1;
        
        int len = unitInput.length();
        
        if (split.length > 1 ) {
            exponentInput = new Integer(split[1]);
        }
        
        CalculationUnitObject unitObject = null;
        
        Unit unit = Unit.get(unitInput);
        Prefix prefix = null;
        
        if (unit != null) {
            unitObject = new CalculationUnitObject(unit, exponentInput);
        } else {
            if (len > 1) {
                prefix = Prefix.get(unitInput.substring(0, 1));
                unit = Unit.get(unitInput.substring(1));
                if (prefix != null && unit != null)
                    unitObject = new CalculationUnitObject(prefix, unit, exponentInput); 
            }
        }
        
        return unitObject;
    }

    public CalculationObject getCalculationObject() {
        return object;
    }
    
}
