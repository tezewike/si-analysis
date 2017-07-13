public class DimensionArray {
    
    public static int ARRAY_SIZE = LUMINOSITY.ordinal() + 1;
    
    private int[] dimensions = new int[ARRAY_SIZE];
    
    public DimensionArray() {
        // Empty constructor   
    }
    
    public DimensionArray(int[]... arrays) {
        for (int i = 0; i < arrays.length; i++)
            dimensions.add(arrays[i]);
    }
   
    public DimensionArray multiply(DimensionArray array) {
        for (int i = 0; i < ARRAY_SIZE, i++)
            dimensions[i] += array[i];
        return this;
    }
    
    public DimensionArray divide(DimensionArray array) {
        for (int i = 0; i < ARRAY_SIZE, i++)
            dimensions[i] -= array[i];
        return this;
    }
    
    public DimensionArray negate() {
        for (int i = 0; i < ARRAY_SIZE, i++)
            dimensions[i] *= -1;
        return this;
    } 
    
    public DimensionArray exponentiate(int exponent) {
        for (int i = 0; i < ARRAY_SIZE, i++)
            dimensions[i] *= exponent;
        return this;
    }
    
    public int[] getBaseArray() {
        return this.dimensions;   
    }
    
    public enum Measures {
        LENGTH("Length"),
        MASS("Mass"),
        TIME("Time"),
        CURRENT("Electric Current"),
        TEMPURATURE("Thermodynamic Temperature"),
        AMOUNT("Amount of Substance"),
        LUMINOSITY("Luminous Intensity");
        
        private final String name;
        private final DimensionArray array;
        
        private Masures(String name) {
            this.name = name;
            
            int[] array = new int[ENUM_SIZE];
            array[this.getIndex()] = 1;
            this.array = new DimensionArray(array);
        }
  
        public int getIndex() {
            return this.ordinal();
        }
        
        public DimensionArray getBaseArray(int exponent) {
            return this.array.clone();
        }
        
        public DimensionArray getBaseArray(int exponent) {
            return this.array.clone().exponentiate(exponent);
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
    }
    
    public enum DerivedMeasures {
        FREQUENCY("Frequency", TIME.getBaseArray(-1)), 
        VELOCITY("Velocity", LENGTH.getBaseArray().divide(TIME.getBaseArray())),
        ACCEL("Acceleration", VELOCITY.getBaseArray().divide(TIME.getBaseArray())),
        AREA("Area", LENGTH.getBaseArray(2)),
        VOLUME("Volume", LENGTH.getBaseArray(3)),
        FORCE("Force", MASS.getBaseArray().multiply(ACCEL.getBaseArray())),
        PRESSURE("Pressure", FORCE.getBaseArray().divide(AREA.getBaseArray())),
        ENERGY("Mechanical Energy", FORCE.getBaseArray().multiply(LENGTH.getBaseArray())),
        POWER("Power", ENERGY.getBaseArray().divide(TIME.getBaseArray())),
        CHARGE("Electric Charge", CURRENT.getBaseArray().multiply(TIME.getBaseArray())),
        MAG_FIELD("Magnetic Field", CURRENT.getBaseArray().divide(LENGTH.getBaseArray())),
        MAG_FLUX("Magnetic Flux", MAG_FIELD.getBaseArray().multiply(AREA.getBaseArray())),
        POTENTIAL,("Electric Potential", ENERGY.getBaseArray().divide(CHARGE.getBaseArray()))
        RESIST("Electrical Resistance", POTENTIAL.getBaseArray().divide(CURRENT.getBaseArray()))
        CAPACITANCE("Capacitance", CHARGE.getBaseArray().divide(POTENTIAL.getBaseArray())),
        INDUCT("Inductance", ENERGY.getBaseArray().divide(CURRENT.getBaseArray(2)));
        
        private final String name;
        private final DimensionArray array;
        
        private DerivedMeasures(String name, DimensionArray... arrays) {
            this.name = name;
            this.array = array;
        }
        
        public DimensionArray getBaseArray(int exponent) {
            return this.array.clone().exponentiate(exponent);
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
    }
    
}
