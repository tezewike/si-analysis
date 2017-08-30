/**
 * This classes serves as a "wrapper" class for integer arrays that represent the base physical
 * quantities of measurement in most unit systems. An instance of this class is both mutable 
 * and cloneable, similar to a standard array object in Java.
 */
public class DimensionArray implements Cloneable {

	/**
	 * The length of the array of any DimensionArray instance. This quantity is determined by the
	 * number of base physical quantities found in {@link Measures}.
	 * 
	 * @see Measures
	 */
    public static int ARRAY_LENGTH = Measures.size();

    /**
     * Holds the exponent values of each base physical quantity.
     */
    private int[] dimensions = new int[ARRAY_LENGTH];
    
    private IllegalArgumentException arrayException = 
    		new IllegalArgumentException("Array must be of size " + ARRAY_LENGTH);
    
    /**
     * The empty public constructor. An instance created from this constructor
     * defaults to an array of size {@link #ARRAY_LENGTH}, with all values in the index
     * set to a zero value. 
     */
    public DimensionArray() {}

    /**
     * The constructor used to clone a DimensionArray instance.
     * 
     * @param array the integer array to clone
     */
    private DimensionArray(int[] array) {
    	this.dimensions = array.clone();
    }

    /**
     * Multiples this instance's base physical quantities with the specified array. In practice,
     * this is merely an addition of the representation arrays (their exponent values).
     * 
     * @param array the physical quantity representation to be multiplied
     * @return this DimensionArray instance
     * @throws IllegalArgumentException if array is not of the right length
     */
    public DimensionArray multiply(int[] array) throws IllegalArgumentException {
    	if (array.length != ARRAY_LENGTH)
    		throw arrayException;
        return this.multiply(array, 1);
    }

    /**
     * Multiples this instance's base physical quantities with the specified array. In practice,
     * this is merely an addition of the representational arrays.
     * 
     * @param array the physical quantity representation to be multiplied
     * @param exponent the number of times the array is to be multiplied
     * @return this DimensionArray instance
     * @throws IllegalArgumentException if array is not of the right length
     */
    public DimensionArray multiply(int[] array, int exponent) throws IllegalArgumentException {
    	if (array.length != ARRAY_LENGTH)
    		throw arrayException;
    	
        for (int i = 0; i < ARRAY_LENGTH; i++)
            dimensions[i] += array[i] * exponent;
        return this;
    }

    /**
     * Multiples this instance's base physical quantities with the specified array.
     * 
     * @param dimensionArray the physical quantity representation to be multiplied
     * @return this DimensionArray instance
     */
    public DimensionArray multiply(DimensionArray dimensionArray) {
    	return this.multiply(dimensionArray, 1);
    }

    /**
     * Multiples this instance's base physical quantities with the specified array.
     * 
     * @param dimensionArray the physical quantity representation to be multiplied
     * @param exponent the number of times the array is to be multiplied
     * @return this DimensionArray instance
     */
    public DimensionArray multiply(DimensionArray dimensionArray, int exponent) {
        try {
			return this.multiply(dimensionArray.toIntegerArray(), exponent);
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		}
        
        return this;
    }

    /**
     * Divides this instance's base physical quantities with the specified array. In practice,
     * this is merely an subtraction of the representational arrays.
     * 
     * @param array the physical quantity representation to be multiplied
     * @return this DimensionArray instance
     * @throws IllegalArgumentException if array is not of the right length
     */
    public DimensionArray divide(int[] array) throws IllegalArgumentException {
    	if (array.length != ARRAY_LENGTH)
    		throw arrayException;
        return this.multiply(new DimensionArray(array).reciprocate());
    }

    /**
     * Divides this instance's base physical quantities with the specified array.
     * 
     * @param dimensionArray the physical quantity representation to be divided
     * @return this DimensionArray instance
     */
    public DimensionArray divide(DimensionArray dimensionArray) {
        return this.multiply(dimensionArray.reciprocate());
    }

    /**
     * Applies a negation to this instance's physical quantity representational array. In practice, this is 
     * merely a multiplication of the entire representational array by the number -1.
     * 
     * @return this DimensionArray instance
     */
    public DimensionArray reciprocate() {
        return this.exponentiate(-1);
    }

    /**
     * Applies an exponentiation to this instance's physical quantity representational array. In practice, this is 
     * merely a multiplication of the entire representational array by the number specified.
     * 
     * @param exponent the number to exponentiate by
     * @return this DimensionArray object
     */
    public DimensionArray exponentiate(int exponent) {
        if (exponent != 1) {
            for (int i = 0; i < ARRAY_LENGTH; i++)
                dimensions[i] *= exponent;
        }
        
        return this;
    }

    /**
     * Returns the integer array of this instance's physical quantity representational array. Each value represents
     * the exponent of a physical quantity, who's index is specified as an enum in {@link Measures}.
     * 
     * @return the representation integer array of this instance's physical quantities
     * @see Measures
     */
    public int[] toIntegerArray() {
        return this.dimensions.clone();
    }

    /**
     * Returns true if the two array objects are considered equal. The two array objects are considered
     * equal if their associated integer arrays hold the same values.
     * 
     * @param obj the integer array or DimensionArray to test for equality with this instance
     * @return true if the two arrays are equal; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DimensionArray)
            return dimensions.equals(((DimensionArray) obj).toIntegerArray());
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

        public DimensionArray getDimensions() {
            return (DimensionArray) dimenArray.clone();
        }

        @Override
        public String toString() {
            String str = name + ": {";
            int[] array = dimenArray.toIntegerArray();

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

    /*
     * DimensionArray derivation methods:
     * The following few private methods are used to create a DimensionArray for a {@link DerivedMeasures}. The methods utilize
     * a builder-like design for easier derivations in the code.
     */

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

}
