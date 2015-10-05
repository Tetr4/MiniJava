class ThisTest {
	public static void main(String[] a){
            A x;
            x = x.m();
	}
}

class A {
    public A m () {
        return this;
    }
}
