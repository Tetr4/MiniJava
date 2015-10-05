class ParamTest {
	public static void main(String[] a){
            A x;
            B y;
            x = new A();
            y = new B();
            x = x.m(y); 
	}
}

class A {
    public A m(A x)
    {    
        return new A();
    }

}

class B extends A {}
