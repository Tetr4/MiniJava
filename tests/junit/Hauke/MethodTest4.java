class MethodTest {
	public static void main(String[] a){
            A x;
            x = new A();
            x = x.m();
	}
}

class A {
    public A m()
    {    
        return new B();
    }

}

class B extends A {}