class Main {
    public static void main(String[] bla){
        TestClass c;
        c = new TestClass();
        System.out.println(c.getValue(100, 200, 3, true, 20, 42));
    }
}
class TestClass {
    public int getValue(int a, int b, int c, boolean d, int e, int f) {
        int x;
        int y;
        int z;
        
        x = a + b;
        y = c * e;
        if (d)
            z = this.getStuff(x, y) * f;
        else
            z = this.getStuff(x, y);
        
        return z;
    }
    
    public int getStuff(int x, int y) {
        return x * 2 + y * y;
    }
}