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
	public int a(){
		C1 t;
		t = this;
		return 0;
	}
}
class C1{}