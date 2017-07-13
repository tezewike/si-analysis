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
        
        @Override
        public String toString() {
            return thiis.name;
        }
        
}
