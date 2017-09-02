package com.tezewike.sianalysis.io;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tezewike.sianalysis.calculation.DimensionObject;

public class OutputParser {

	private JSONObject data;
	private JSONObject input, numerator, denominator, output;
	
	public OutputParser(String jsonString) {
		try {
			this.data = (JSONObject) new JSONParser().parse(jsonString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.parse();
	}
	
	public OutputParser(JSONObject object) {
		this.data = object;
		this.parse();
	}
	
	private void parse() {
		if (data == null)
			throw new NullPointerException();
		
		input = (JSONObject) data.get(DimensionObject.INPUT_KEY);
		output = (JSONObject) data.get(DimensionObject.OUTPUT_KEY);
		
		numerator = (JSONObject) input.get(DimensionObject.NUMERATOR_KEY);
		denominator = (JSONObject) input.get(DimensionObject.DENOMINATOR_KEY);
		
	}
	
	public double getNumeratorMagnitude() {
		return (double) numerator.get(DimensionObject.MAGNITUDE_KEY);
	}
	
	public double getDenominatorMagnitude() {
		return (double) denominator.get(DimensionObject.MAGNITUDE_KEY);
	}
	
	public double getOutputMagnitude() {
		return (double) output.get(DimensionObject.MAGNITUDE_KEY);
	}
	
	public List<DataUnitEntry> getNumeratorUnits() {
		List<DataUnitEntry> units = new ArrayList<>();
		
		JSONArray array = (JSONArray) numerator.get(DimensionObject.UNIT_ENTRY_KEY);
		
		for (int i = 0; i < array.size(); i++) {
			units.add(new DataUnitEntry((JSONObject) array.get(i)));
		}
		
		return units;
	}
	
}

class DataUnitEntry {
	
	private String name;
	private String symbol;
	private int exponent;
	
	protected DataUnitEntry(JSONObject object) {
		this.name = (String) object.get(DimensionObject.ENTRY_NAME_KEY);
		this.symbol = (String) object.get(DimensionObject.ENTRY_SYMBOL_KEY);
		this.exponent = (int) object.get(DimensionObject.ENTRY_EXPONENT_KEY);
	}
	
	public String getName() {
		return name;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public int getExponent() {
		return exponent;
	}
	
}