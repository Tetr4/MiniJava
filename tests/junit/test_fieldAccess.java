class Main {
    public static void main(String[] bla){
        TestClass c;
        int v;
        c = new TestClass();
        v = c.setValue(4);
        System.out.println(c.getValue());
    }
}
class TestClass { 
    int value;
    public int setValue(int newValue) {
        value = newValue;
        return value; 
    } 
    public int getValue() { 
        return value; 
    } 
 
}