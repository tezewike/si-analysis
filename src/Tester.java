
public class Tester {

    public Tester() {
        // TODO Auto-generated constructor stub
    }
    
    public static void main(String[] args) {
        for (DimensionArray.Measures measure : DimensionArray.Measures.values())
            System.out.println(measure);
        for (DimensionArray.DerivedMeasures measure : DimensionArray.DerivedMeasures.values())
            System.out.println(measure);
    
    }

}
