package com.tezewike.sianalysis;
import com.tezewike.sianalysis.calculation.DimensionObject;
import com.tezewike.sianalysis.data.DataLoader;
import com.tezewike.sianalysis.io.OutputParser;
import com.tezewike.sianalysis.io.UnitParser;

public class SIAnalysisTester {
    
    public static void main(String[] args) {
    /*
        for (DimensionArray.Measures measure : DimensionArray.Measures.values())
            System.out.println(measure);
        System.out.println();
        for (DimensionArray.DerivedMeasures measure : DimensionArray.DerivedMeasures.values())
            System.out.println(measure);
        System.out.println();
    */

    	DataLoader.load();
    	
        DimensionObject obj = new UnitParser().parse(null, "cm^3", null, "L");
        OutputParser data = new OutputParser(obj.toJSONObject());
        
        System.out.println(obj.toJSONObject().toJSONString());
        System.out.println(data.getNumeratorMagnitude());
        
    }
    
}
