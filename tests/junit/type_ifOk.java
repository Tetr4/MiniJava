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
	int y;
	
	public int funktionierendesIfThenElse(){
		if((!(x<y)) && (!(y<x)))
			System.out.println(x);
		else
			System.out.println(y);
		return 0;
	}
}