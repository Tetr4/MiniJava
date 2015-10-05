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

class B {
    public A m (int x) {
        return new A();
    }
}
