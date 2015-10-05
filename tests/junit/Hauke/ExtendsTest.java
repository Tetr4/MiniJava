class ExtendsTest {
	public static void main(String[] a){
            A x;
            B y;
            x = new D();
            y= new B();
            System.out.println(x.f());
            System.out.println(y.f());
    }
}


class B extends A {}

class C extends B {}

class D extends C {}

class A {
	
	public int f() {
		return 23;
	}
}
