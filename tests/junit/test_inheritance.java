class Main { 
    public static void main(String[] args) { 
        A a; 
        B b; 
        A ab; 
        a = new A(); 
        b = new B(); 
        ab = new B(); 
        System.out.println(a.getValue()); 
        System.out.println(b.getValue()); 
        System.out.println(ab.getValue()); 
    } 
} 
 
class A { 
    public int getValue() { 
        return 1; 
    } 
} 
 
class B extends A {}