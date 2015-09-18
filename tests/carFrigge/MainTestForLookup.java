class MainTest{
    public static void main(String[] arg0){
    }
}


class Fehlerfrei{
	Integer a;
	binteger b;
	int c;
	int[] d;
}

class FehlerDoppelteVariable{
	Integer a;
	Integer a;
}


class FehlerDoppelteKlasse{
}
class FehlerDoppelteKlasse{
}


class FehlerDoppelteMethode{
	public int a(){
		return 0;
	}
	public int a(){
    	return 0;
	}
}


class KeinFehlerDoppelteMethode{
	public int a(int a){
		return 0;
	}
	public int a(int[] a){
    	return 0;
	}
	public int a(){
    	return 0;
	}
}
