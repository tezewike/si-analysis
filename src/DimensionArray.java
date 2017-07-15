public class DimensionArray implements Cloneable {

    public static int ARRAY_SIZE = 7;

    private int[] dimensions = new int[ARRAY_SIZE];

    public DimensionArray() {
        // Empty constructor
    }

    public DimensionArray(int[]... arrays) {
        for (int i = 0; i < arrays.length; i++)
            this.multiply(arrays[i]);
    }

    public DimensionArray multiply(int[] array) {
        for (int i = 0; i < ARRAY_SIZE; i++)
            dimensions[i] += array[i];
        return this;
    }

    public DimensionArray multiply(DimensionArray dimensionArray) {
        return this.multiply(dimensionArray.getBaseArray());
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
        for (int i = 0; i < ARRAY_SIZE; i++)
            dimensions[i] *= exponent;
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

    /**
     * The seven base masures.
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
        /** Tempurature */
        TEMPURATURE("Thermodynamic Temperature"),
        /** AMount of substance. Example: mol */
        AMOUNT("Amount of Substance"),
        /** Luminous intensity */
        LUMINOSITY("Luminous Intensity");

        private final String name;
        private final int[] array;

        private Measures(String name) {
            this.name = name;

            this.array = new int[ARRAY_SIZE];
            this.array[this.getIndex()] = 1;
        }

        public int getIndex() {
            return this.ordinal();
        }

        public int[] getBaseArray() {
            return this.array.clone();
        }

        public int[] getBaseArray(int exponent) {
            return new DimensionArray(this.array.clone()).exponentiate(exponent).getBaseArray();
        }

        @Override
        public String toString() {
            String str = name + ": {";
            for (int i = 0; i < array.length; i++)
                str += array[i] + ",";
            str = str.substring(0, str.length() - 1) + "}";
            return str;
        }

    }

    /**
     * An Enumeration containing measures that can be derived using the basic
     * masures
     */
    public enum DerivedMeasures {
        /** fre */
        FREQUENCY("Frequency", Measures.TIME.getBaseArray(-1)),
        /** veloci */
        VELOCITY("Velocity", Measures.LENGTH.getBaseArray(), Measures.TIME.getBaseArray(-1)),
        /** accl */
        ACCEL("Acceleration", VELOCITY.getBaseArray(), Measures.TIME.getBaseArray(-1)),
        /** area */
        AREA("Area", Measures.LENGTH.getBaseArray(2)),
        /** vol */
        VOLUME("Volume", Measures.LENGTH.getBaseArray(3)),
        /** force */
        FORCE("Force", Measures.MASS.getBaseArray(), ACCEL.getBaseArray()),
        /** press */
        PRESSURE("Pressure", FORCE.getBaseArray(), AREA.getBaseArray(-1)),
        /** energ */
        ENERGY("Mechanical Energy", FORCE.getBaseArray(), Measures.LENGTH.getBaseArray()),
        /** power */
        POWER("Power", ENERGY.getBaseArray(), Measures.TIME.getBaseArray(-1)),
        /** charge */
        CHARGE("Electric Charge", Measures.CURRENT.getBaseArray(), Measures.TIME.getBaseArray(-1)),
        /** mag fied */
        MAG_FIELD("Magnetic Field", Measures.CURRENT.getBaseArray(), Measures.LENGTH.getBaseArray(-1)),
        /** flux */
        MAG_FLUX("Magnetic Flux", MAG_FIELD.getBaseArray(), AREA.getBaseArray(-1)),
        /** potential */
        POTENTIAL("Electric Potential", ENERGY.getBaseArray(), CHARGE.getBaseArray(-1)),
        /** resist */
        RESIST("Electrical Resistance", POTENTIAL.getBaseArray(), Measures.CURRENT.getBaseArray(-1)),
        /** capacitance */
        CAPACITANCE("Capacitance", CHARGE.getBaseArray(), POTENTIAL.getBaseArray(-1)),
        /** induct */
        INDUCT("Inductance", ENERGY.getBaseArray(), Measures.CURRENT.getBaseArray(-2));

        private final String name;
        private final int[] array;

        private DerivedMeasures(String name, int[]... arrays) {
            this.name = name;
            this.array = new DimensionArray(arrays).getBaseArray();
        }

        public int[] getBaseArray() {
            return this.array.clone();
        }
        
        public int[] getBaseArray(int exponent) {
            return new DimensionArray(this.array.clone()).exponentiate(exponent).getBaseArray();
        }

        @Override
        public String toString() {
            String str = name + ": {";
            for (int i = 0; i < array.length; i++)
                str += array[i] + ",";
            str = str.substring(0, str.length() - 1) + "}";
            return str;
        }

    }

}
