class OverrideTest {
	public static void main(String[] a){
    }
}

class A {
    public A m(boolean x)
    {    
        return new A();
    }
}

class B extends A{
    public B m (boolean x) {
        return new B();
    }
}
