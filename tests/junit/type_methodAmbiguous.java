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
class Methodensammlung{
	
	public int m1(A1 a, B2 b){
		return 0;
	}
	public int m1(A2 a, B1 b){
		return 0;
	}
	
}