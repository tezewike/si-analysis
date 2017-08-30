/**
 * This classes serves as a "wrapper" class for integer arrays that represent the base physical
 * quantities of measurement in most unit systems. An instance of this class is both mutable 
 * and cloneable, similar to a standard array object in Java.
 */
public class DimensionArray implements Cloneable {

	/**
	 * The size of the array of any DimensionArray instance. This quantity is determined by the
	 * number of base physical quantities found in {@link Measures}.
	 * 
	 * @see Measures
	 */
    public static int ARRAY_SIZE = Measures.size();

    /**
     * Holds the exponent values of each base physical quantity.
     */
    private int[] dimensions = new int[ARRAY_SIZE];
    
    /**
     * The default empty public constructor. An instance created from this constructor
     * defaults to an array if size {@link #ARRAY_SIZE}, all with a zero value. 
     */
    public DimensionArray() {}

    /**
     * Creates an instance with values equal to the combined values of all the inputed
     * integer arrays.
     * 
     * @param arrays
     */
    public DimensionArray(int[]... arrays) {
        for (int i = 0; i < arrays.length; i++)
            this.multiply(arrays[i]);
    }

    private DimensionArray derive(Measures measure, int exponent) {
        dimensions[measure.index()] += exponent;
        return this;
    }

    private DimensionArray derive(DerivedMeasures measure) {
        return this.multiply(measure.getDimensions());
    }

    private DimensionArray derive(DerivedMeasures measure, int exponent) {
        return this.multiply(((DimensionArray) measure.getDimensions().clone()).exponentiate(exponent));
    }

    public DimensionArray multiply(int[] array) {
        for (int i = 0; i < ARRAY_SIZE; i++)
            dimensions[i] += array[i];
        return this;
    }

    public DimensionArray multiply(int[] array, int exponent) {
        for (int i = 0; i < ARRAY_SIZE; i++)
            dimensions[i] += array[i] * exponent;
        return this;
    }

    public DimensionArray multiply(DimensionArray dimensionArray) {
        return this.multiply(dimensionArray.getBaseArray());
    }

    public DimensionArray multiply(DimensionArray dimensionArray, int exponent) {
        return this.multiply(dimensionArray.getBaseArray(), exponent);
    }

    public DimensionArray divide(int[] array) {
        for (int i = 0; i < ARRAY_SIZE; i++)
            dimensions[i] -= array[i];
        return this;
    }

    public DimensionArray divide(DimensionArray dimensionArray) {
        return this.divide(dimensionArray.getBaseArray());
    }

    public DimensionArray negate() {
        for (int i = 0; i < ARRAY_SIZE; i++)
            dimensions[i] *= -1;
        return this;
    }

    public DimensionArray exponentiate(int exponent) {
        if (exponent != 1) {
            for (int i = 0; i < ARRAY_SIZE; i++)
                dimensions[i] *= exponent;
        }
        
        return this;
    }

    public int[] getBaseArray() {
        return this.dimensions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DimensionArray)
            return dimensions.equals(((DimensionArray) obj).getBaseArray());
        else if (obj instanceof int[])
            return dimensions.equals((int[]) obj);
        return super.equals(obj);
    }

    @Override
    public Object clone() {
        return new DimensionArray(dimensions.clone());
    }

    /**
     * The seven base measures.
     */
    public enum Measures {
        /** Used for distance and position */
        LENGTH("Length"),
        /** Used to get the weight */
        MASS("Mass"),
        /** Time */
        TIME("Time"),
        /** Electric current */
        CURRENT("Electric Current"),
        /** Temperature */
        TEMPERATURE("Thermodynamic Temperature"),
        /** AMount of substance. Example: mol */
        AMOUNT("Amount of Substance"),
        /** Luminous intensity */
        LUMINOSITY("Luminous Intensity");

        private final String name;

        private Measures(String name) {
            this.name = name;
        }

        public int index() {
            return this.ordinal();
        }

        public static int size() {
            return Measures.values().length;
        }

        @Override
        public String toString() {
            return this.name;
        }

    }

    /**
     * An Enumeration containing measures that can be derived using the basic
     * measures
     */
    public enum DerivedMeasures {
        LENGTH("Length", new DimensionArray().derive(Measures.LENGTH, 1)),
        /** Used to get the weight */
        MASS("Mass", new DimensionArray().derive(Measures.MASS, 1)),
        /** Time */
        TIME("Time", new DimensionArray().derive(Measures.TIME, 1)),
        /** Electric current */
        CURRENT("Electric Current", new DimensionArray().derive(Measures.CURRENT, 1)),
        /** Temperature */
        TEMPERATURE("Thermodynamic Temperature", new DimensionArray().derive(Measures.TEMPERATURE, 1)),
        /** AMount of substance. Example: mol */
        AMOUNT("Amount of Substance", new DimensionArray().derive(Measures.AMOUNT, 1)),
        /** Luminous intensity */
        LUMINOSITY("Luminous Intensity", new DimensionArray().derive(Measures.LUMINOSITY, 1)),
        /** fre */
        FREQUENCY("Frequency", new DimensionArray().derive(TIME, -1)),
        /** veloci */
        VELOCITY("Velocity", new DimensionArray().derive(LENGTH).derive(TIME, -1)),
        /** accl */
        ACCEL("Acceleration", new DimensionArray().derive(VELOCITY).derive(TIME, -1)),
        /** area */
        AREA("Area", new DimensionArray().derive(LENGTH, 2)),
        /** vol */
        VOLUME("Volume", new DimensionArray().derive(LENGTH, 3)),
        /** force */
        FORCE("Force", new DimensionArray().derive(MASS).derive(ACCEL)),
        /** press */
        PRESSURE("Pressure", new DimensionArray().derive(FORCE).derive(AREA, -1)),
        /** energ */
        ENERGY("Mechanical Energy", new DimensionArray().derive(FORCE).derive(LENGTH)),
        /** power */
        POWER("Power", new DimensionArray().derive(ENERGY).derive(TIME, -1)),
        /** charge */
        CHARGE("Electric Charge", new DimensionArray().derive(CURRENT).derive(TIME, -1)),
        /** mag fied */
        MAG_FIELD("Magnetic Field", new DimensionArray().derive(CURRENT).derive(LENGTH, -1)),
        /** flux */
        MAG_FLUX("Magnetic Flux", new DimensionArray().derive(MAG_FIELD).derive(AREA, -1)),
        /** potential */
        POTENTIAL("Electric Potential", new DimensionArray().derive(ENERGY).derive(CHARGE, -1)),
        /** resist */
        RESIST("Electrical Resistance", new DimensionArray().derive(POTENTIAL).derive(CURRENT, -1)),
        /** capacitance */
        CAPACITANCE("Capacitance", new DimensionArray().derive(CHARGE).derive(POTENTIAL, -1)),
        /** induct */
        INDUCT("Inductance", new DimensionArray().derive(ENERGY).derive(CURRENT, -2));

        private final String name;
        private final DimensionArray dimenArray;

        private DerivedMeasures(String name, DimensionArray array) {
            this.name = name;
            this.dimenArray = array;
        }

        public int[] getBaseArray() {
            return this.dimenArray.getBaseArray();
        }

        public DimensionArray getDimensions() {
            return (DimensionArray) dimenArray.clone();
        }

        @Override
        public String toString() {
            String str = name + ": {";
            int[] array = dimenArray.getBaseArray();

            for (int i = 0; i < array.length; i++)
                str += array[i] + ",";
            str = str.substring(0, str.length() - 1) + "}";
            return str;
        }

        public String getKey() {
            return super.toString();
        }

        public static DerivedMeasures getEnum(String key) {
            for (DerivedMeasures measure : DerivedMeasures.values()) {
                if (measure.getKey().equals(key))
                    return measure;
            }
            return null;
        }
        
        public static int size() {
            return DerivedMeasures.values().length;
        }

    }

}
