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
	int c;
	int d;
	
	public int arrayZugriff(){
		d[c] = c;
		return 0;
	}
}