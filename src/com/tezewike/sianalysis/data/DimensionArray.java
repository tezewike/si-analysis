package com.tezewike.sianalysis.data;
// TODO ~ License

/**
 * This classes serves as a "wrapper" class for integer arrays that represent
 * the base physical quantities of measurement in most unit systems. An instance
 * of this class is both mutable and cloneable, similar to a standard array
 * object in Java.
 */
public class DimensionArray implements Cloneable {

	/**
	 * The length of the array of any DimensionArray instance. This quantity is
	 * determined by the number of base physical quantities found in
	 * {@link Measures}.
	 * 
	 * @see Measures
	 */
	public static int ARRAY_LENGTH = Measures.size();

	/**
	 * Holds the exponent values of each base physical quantity.
	 */
	private int[] dimensions = new int[ARRAY_LENGTH];

	/**
	 * An exception that is thrown when a operation takes in an array that is not of
	 * the same length as the {@link #dimensions} variable.
	 */
	private IllegalArgumentException arrayException = new IllegalArgumentException(
			"Array must be of size " + ARRAY_LENGTH);

	/**
	 * The empty public constructor. An instance created from this constructor
	 * defaults to an array of size {@link #ARRAY_LENGTH}, with all values in the
	 * index set to a zero value.
	 */
	public DimensionArray() {
	}

	/**
	 * The constructor used to clone a DimensionArray instance.
	 * 
	 * @param array the integer array to clone
	 */
	private DimensionArray(int[] array) {
		this.dimensions = array.clone();
	}

	/**
	 * Multiples this instance's base physical quantities with the specified array.
	 * In practice, this is merely an addition of the representation arrays (their
	 * exponent values).
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
	 * Multiples this instance's base physical quantities with the specified array.
	 * In practice, this is merely an addition of the representational arrays.
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
	 * Divides this instance's base physical quantities with the specified array. In
	 * practice, this is merely an subtraction of the representational arrays.
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
	 * Applies a negation to this instance's physical quantity representational
	 * array. In practice, this is merely a multiplication of the entire
	 * representational array by the number -1.
	 * 
	 * @return this DimensionArray instance
	 */
	public DimensionArray reciprocate() {
		return this.exponentiate(-1);
	}

	/**
	 * Applies an exponentiation to this instance's physical quantity
	 * representational array. In practice, this is merely a multiplication of the
	 * entire representational array by the number specified.
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
	 * Returns the integer array of this instance's physical quantity
	 * representational array. Each value represents the exponent of a physical
	 * quantity, who's index is specified as an enum in {@link Measures}.
	 * 
	 * @return the representation integer array of this instance's physical
	 *         quantities
	 * @see Measures
	 */
	public int[] toIntegerArray() {
		return this.dimensions.clone();
	}

	/**
	 * Returns true if the two array objects are considered equal. The two array
	 * objects are considered equal if their associated integer arrays hold the same
	 * values.
	 * 
	 * @param obj the integer array or DimensionArray to test for equality with this
	 *            instance
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

	/**
	 * Creates and returns a copy of this DimensionArray object.
	 * 
	 * @return a copy of this object
	 */
	@Override
	public Object clone() {
		return new DimensionArray(dimensions.clone());
	}

	/**
	 * This enum holds the essential base physical quantities of the International
	 * System of Units.
	 */
	public enum Measures {
		/** A physical quantity used for distance and position. */
		LENGTH("Length"),
		/** A physical quantity representing an object's resistance to acceleration. */
		MASS("Mass"),
		/** A physical quantity used to measure the progression of events */
		TIME("Time"),
		/** A physical quantity used to measure the flow of electric charge. */
		CURRENT("Electric Current"),
		/** A physical quantity used to measure heat. */
		TEMPERATURE("Thermodynamic Temperature"),
		/** A physical quantity used to quantify a substance. */
		AMOUNT("Amount of Substance"),
		/** A physical quantity used to measure power emitted by a source of light. */
		LUMINOSITY("Luminous Intensity");

		private final String name;

		private Measures(String name) {
			this.name = name;
		}

		/**
		 * Returns the name of the physical quantity.
		 * 
		 * @return the name of this quantity
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * Returns the index of the physical quantity within this enum. This is the same
		 * method as {@link java.lang.Enum#ordinal()}.
		 * 
		 * @return the index of this quantity
		 */
		public int index() {
			return this.ordinal();
		}

		/**
		 * Returns the number of elements within this enum.
		 * 
		 * @return the number of elements
		 */
		public static int size() {
			return Measures.values().length;
		}

	}

	/**
	 * An enum containing the base physical quantities, as well as the derived
	 * quantities whose values are determined by any combination of the base
	 * quantities.
	 */
	public enum DerivedMeasures {
		/** Length */
		LENGTH("Length", new DimensionArray().derive(Measures.LENGTH, 1)),
		/** Mass */
		MASS("Mass", new DimensionArray().derive(Measures.MASS, 1)),
		/** Time */
		TIME("Time", new DimensionArray().derive(Measures.TIME, 1)),
		/** Current */
		CURRENT("Electric Current", new DimensionArray().derive(Measures.CURRENT, 1)),
		/** Temperature */
		TEMPERATURE("Thermodynamic Temperature", new DimensionArray().derive(Measures.TEMPERATURE, 1)),
		/** Amount */
		AMOUNT("Amount of Substance", new DimensionArray().derive(Measures.AMOUNT, 1)),
		/** Luminosity */
		LUMINOSITY("Luminous Intensity", new DimensionArray().derive(Measures.LUMINOSITY, 1)),
		/** Frequency: 1/Time */
		FREQUENCY("Frequency", new DimensionArray().derive(TIME, -1)),
		/** Velocity: Length/Time */
		VELOCITY("Velocity", new DimensionArray().derive(LENGTH).derive(TIME, -1)),
		/** Acceleration: Length/Time<sup>2</sup> or Velocity/Time */
		ACCEL("Acceleration", new DimensionArray().derive(VELOCITY).derive(TIME, -1)),
		/** Area: Length<sup>2</sup> */
		AREA("Area", new DimensionArray().derive(LENGTH, 2)),
		/** Volume: Length<sup>3</sup> */
		VOLUME("Volume", new DimensionArray().derive(LENGTH, 3)),
		/** Force: (Mass Length)/Time<sup>2</sup> or Mass/Acceleration */
		FORCE("Force", new DimensionArray().derive(MASS).derive(ACCEL)),
		/** Pressure: Mass/(Length Time<sup>2</sup>) or Force/Area */
		PRESSURE("Pressure", new DimensionArray().derive(FORCE).derive(AREA, -1)),
		/**
		 * Mechanical Energy: (Mass Length<sup>2</sup>)/Time<sup>2</sup> or Force *
		 * Length
		 */
		ENERGY("Mechanical Energy", new DimensionArray().derive(FORCE).derive(LENGTH)),
		/** Power: Energy/Time */
		POWER("Power", new DimensionArray().derive(ENERGY).derive(TIME, -1)),
		/** Electric CHarge: Current/Time */
		CHARGE("Electric Charge", new DimensionArray().derive(CURRENT).derive(TIME, -1)),
		/** Magnetic Field: Current/Length */
		MAG_FIELD("Magnetic Field", new DimensionArray().derive(CURRENT).derive(LENGTH, -1)),
		/** Magnetic Flux: MagneticField/Area */
		MAG_FLUX("Magnetic Flux", new DimensionArray().derive(MAG_FIELD).derive(AREA, -1)),
		/** Electric Potential: Energy/Charge */
		POTENTIAL("Electric Potential", new DimensionArray().derive(ENERGY).derive(CHARGE, -1)),
		/** Electric Resistance: Potential/Current */
		RESIST("Electrical Resistance", new DimensionArray().derive(POTENTIAL).derive(CURRENT, -1)),
		/** Capacitance: Charge/ElectricPotential */
		CAPACITANCE("Capacitance", new DimensionArray().derive(CHARGE).derive(POTENTIAL, -1)),
		/** Inductance: Energy/Current<sup>2</sup> */
		INDUCT("Inductance", new DimensionArray().derive(ENERGY).derive(CURRENT, -2));

		private final String name;
		private final DimensionArray dimenArray;

		private DerivedMeasures(String name, DimensionArray array) {
			this.name = name;
			this.dimenArray = array;
		}

		/**
		 * Returns a new {@link DimensionArray} object representing this physical
		 * quantity.
		 * 
		 * @return a DimensionArray object representing this physical quantity
		 */
		public DimensionArray getDimensions() {
			return (DimensionArray) dimenArray.clone();
		}

		/**
		 * Returns the name of this enum and the array representation of its associated
		 * {@link DimensionArray} value.
		 * 
		 * @return the String representation of this enum
		 */
		@Override
		public String toString() {
			String str = name + ": {";
			int[] array = dimenArray.toIntegerArray();

			for (int i = 0; i < array.length; i++)
				str += array[i] + ",";
			str = str.substring(0, str.length() - 1) + "}";
			return str;
		}

		/**
		 * Returns the key of this enum. This value is used in the database to determine
		 * its {@link DerivedMeasures}.
		 * 
		 * @return the key String of this enum
		 */
		public String getKey() {
			return super.toString();
		}

		/**
		 * Returns the enum associated with the specified key.
		 * 
		 * @param key the key of the enum
		 * @return the enum associated with the given key String
		 */
		public static DerivedMeasures getEnum(String key) {
			for (DerivedMeasures measure : DerivedMeasures.values()) {
				if (measure.getKey().equals(key))
					return measure;
			}
			return null;
		}

		/**
		 * Returns the number of elements within this enum.
		 * 
		 * @return the number of elements
		 */
		public static int size() {
			return DerivedMeasures.values().length;
		}

	}

	/*
	 * DimensionArray derivation methods: The following few private methods are used
	 * to create a DimensionArray for a {@link DerivedMeasures}. The methods utilize
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