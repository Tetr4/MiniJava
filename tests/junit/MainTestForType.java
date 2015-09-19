class MainTest{
    public static void main(String[] arg0){
    }
}


class FehlerSammlung{
	Integer a;
	MissingType b;
	int c;
	int[] d;
	int x;
	int y;

	Methodensammlung m;
	
	public int funktionierenderArrayZugriff(){
		d[c] = c;
		return 0;
	}
	
	public int falscherArrayZugriffPos(){
		d[a] = c;
		return 0;
	}
	
	public int falscherArrayZugriffValue(){
		d[c] = a;
		return 0;
	}
	
	public int funktionierendesIfThenElse(){
		if((!(x<y)) && (!(y<x)))
			System.out.println(x);
		else
			System.out.println(y);
		return 0;
	}
	
	public int fehlerhaftesIfThenElse(){
		if(x)
			System.out.println(x);
		else
			System.out.println(y);
		return 0;
	}
	
	public int funktionierendesWhile(){
		while((!(x<y)) && (!(y<x)))
			System.out.println(x);
		return 0;
	}
	
	public int fehlerhaftesWhile(){
		while(x)
			System.out.println(x);
		return 0;
	}

	public int executeFunktioniert(){
		y = m.m1(x);
		return 0;
	}
	
	public int executeFunktioniertNicht(){
		y = m.m1(a);
		return 0;
	}
}

class Integer{
}

class Methodensammlung{
	
	public int m1(int a){
		return a;
	}
	
}

class A1{
}
class A2 extends A1{
}

class B1{
	public int a(){
		return 0;
	}
}
class B2 extends B1{
	public int b(){
		return 0;
	}
}
