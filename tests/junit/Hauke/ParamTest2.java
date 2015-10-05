class ParamTest {
	public static void main(String[] a){
            A x;
            x = new A();
            x = x.m(0,false);
	}
}

class A {
    public A m(int i,boolean b)
    {    
        return new A();
    }

}
