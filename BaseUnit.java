public class BaseUnit {
    
    public static int ENUM_SIZE = LUMINOSITY.ordinal() + 1;
    
    public enum Measures {
        LENGTH("Length"),
        MASS("Mass"),
        TIME("Time"),
        CURRENT("Electric Current"),
        TEMPURATURE("Thermodynamic Temperature"),
        AMOUNT("Amount of Substance"),
        LUMINOSITY("Luminous Intensity");
        
        private final String name;
        
        private Masures(String name) {
            this.name = name;
        }
  
        public int getIndex() {
            return this.ordinal();
        }
        
        public int[] getBaseArray() {
            int[] array = new int[ENUM_SIZE];
            array[this.getIndex()] = 1;
            return array;
        }
        
        public int[] getBaseArray(int exp) {
            int[] array = new int[ENUM_SIZE];
            array[this.getIndex()] = exp;
            return array;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
    }
        
    public enum Dimensions {
        LENGTH("Length", ),
        MASS("Mass"),
        TIME("Time"),
        CURRENT("Electric Current"),
        TEMPURATURE("Thermodynamic Temperature"),
        AMOUNT("Amount of Substance"),
        LUMINOSITY("Luminous Intensity");
        
        private int[] assignArray(int[]... array) {
            final int ENUM_INDEX = 0;
            final int VALUE = 1;
            
            int[] dimensionArray = new int[ENUM_SIZE];
            
            for (int i = 0; i < array.length; i++) {
                final[array[i][ENUM_INDEX]] = array[i][VALUE];
            }
            
            return dimensionArray;
        }
        
    }
    
    
}
