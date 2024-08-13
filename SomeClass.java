//An example of a random class with fields of different types
public class SomeClass {
    private String first;
    private double second;
    private String third;
    private int fourth;

    public SomeClass(String first, double second, String third, int fourth){
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public String getFirst() {
        return first;
    }

    public double getSecond() {
        return second;
    }

    public String getThird() {
        return third;
    }

    public int getFourth(){
        return fourth;
    }
}