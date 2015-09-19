class MainTest{
    public static void main(String[] arg0){
    	Integer n;
    	int sum;
    	int useless;

    	n = new Integer();
    	useless = n.set(5);
    	sum=0;
    	System.out.println(n.get());
    	while(0<n.get()){
    		sum = sum + n.get();
    		useless = n.set(n.get()-1);
    	}
    	System.out.println(sum);
    }
}

class Integer{
	int zahl;

	public int set(int i){
		zahl = i;
		return zahl;
	}
	public int get(){
		return zahl;
	}
}