public class UnitSystem {

    public enum System {
        INTERNATIONAL_SYSTEM("International System of Units", "m", "kg", "s", "A", "K", "mol", "cd");
        
        private final String name;
        private final Unit[] units;
        
        private System(String name; String... unitKeys) {
            if (unitKeys.length != DimensionArray.ARRAY_SIZE)
                throw new Exception("invalid arguments for unit system");
            
            Unit unit = null;
            for (int i = 0; i < unitKeys.length) {
                unit = Unit.get(unitKeys[i]);
                if (unit == null)
                    throw new Exception("invalid key, \"" + unitKeys[i] + "\", for unit system");
                else 
                    this.units[i] = unit;
            }
            
            this.name = name;
        }
        
        public static int[] convertTo(System system, int[] array) {
            
        }
        
        public static int[] converTo(System system, douvble magnitude) {
            
        }
        
    }
  
}
