class MainTest{
    public static void main(String[] arg0){
    }
}

class Integer{
}

class A1{
}
class A2 extends A1{
}

class B1{
}
class B2 extends B1{
}
class FehlerSammlung{
	int x;
	boolean b;
	Methodensammlung m;
	
	public int executeFunktioniertNicht(){
		b = m.m1(x);
		return 0;
	}
}


class Methodensammlung{
	
	public int m1(int a){
		return a;
	}
	
}