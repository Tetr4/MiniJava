class Main {
    public static void main(String[] bla){
        TestClass a;
        TestClass b;
        a = new TestClass();
        b = a.getValue();
        System.out.println(b.print());
    }
}
class TestClass { 
    TestClass x;
    public TestClass getValue() { 
        return x; 
    } 
    public int print() {
        return 1;
    } 
 
}