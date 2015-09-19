class MainTest{
    public static void main(String[] arg0){
    	A a;
    	A2 a2;
    	A wirklichA2;
    	
    	a = new A();
    	a2 = new A2();
    	wirklichA2 = new A2();

    	System.out.println(a.getZahl());
    	System.out.println(a.getNochNeZahl());
    	System.out.println(a2.getZahl());
    	System.out.println(a2.getNochNeZahl());
    	System.out.println(wirklichA2.getZahl());
    	System.out.println(wirklichA2.getNochNeZahl());
    }
}

class A{
	public int getZahl(){
		return 1;
	}
	
	public int getNochNeZahl(){
		return 3;
	}
}
class A2 extends A{
	public int getZahl(){
		return 2;
	}
}