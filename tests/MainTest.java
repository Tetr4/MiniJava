class Main {
    public static void main(String[] bla){ // bla
        // VarDecl*
        int[] a; /* muh */
        boolean b;
        int c;
        Bla d;
        
        // Statement*
        a = new int[1];
        b = true;
        c = 1;
        d = new Blubb();
    }
}

class Bla {}

class Blubb extends Bla {
    /*a*/
    // VarDecl*
    Blubb blubb;
    
    // Method*
    public Bla meh(int[] a, boolean b, int c, Bla d) {
        // VarDecl*     
        int x;
        // Statement*
        {}
        { System.out.println(1); }
        while(false)
            x=5+x;
        //if ( a.length < x ) {
        if ( a < x ) {
            a[0] = 5;
        } else {
            a = new int[5];
            b = !b;
            d = new Blubb();
            d = this.meh(a, b, c, d);
            b = c+1-1*1 < c+1 && c < 10;
        }
        blubb = new Blubb();
        
        // Return
        return blubb;
    }
}