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
class C2 extends C1{
}
class C1{
	public int a(){
		C2 t;
		t = this;
		return 0;
	}
}