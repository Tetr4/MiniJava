class VarTest {
	public static void main(String[] a){
    }
}

class A {
    int x;
}

class B extends A {
}

class C extends B {
    boolean x;
    public boolean m() {
        return x;
    }

}