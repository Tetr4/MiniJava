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

	public int m1(){
		A2 a;
		a = new A1();
		return 0;
	}
}