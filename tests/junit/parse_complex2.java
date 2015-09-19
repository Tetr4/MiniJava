class Main {
    public static void main(String[] bla){ // bla 
        // VarDecl* 
        int[] a; /* muh */ 
        boolean b; 
        int c; 
        Muh d; 
         
        // Statement* 
        a = new int[1]; 
        b = true; 
        c = 1; 
        i = 1 + 2 - 3 * 4 ; 
        d = new Blubb(); 
    }
}

class Muh {
    int z;
}

class Blubb extends Muh {
    // VarDecl*
    Blubb blubb;
    float z;

    // Method*
    public Muh meh(int[] a, boolean b, int c, Blubb d) { 
        // VarDecl*      
        int x; 
        // Statement* 
        {} 
        f = e; // TESTING NOT DECLARED VARIABLE 
        { System.out.println(1); } 
        while(false) 
            x=5 + x; 
        //if ( a.length < x ) { 
        if ( a < x ) { 
            a[0] = 5; 
        } else { 
            a = new int[5]; 
            b = !b; 
            d = new Blubb(); 
            d = this.meh(a, b, c, d); 
            b = c + 1-1*1 < c1 && c < 10; 
        } 
        blubb = new Blubb(); 
         
        // Return 
        return blubb; 
    }
}