class Sum {
    public static void main(String[] args) {
        int[] array;
        int x;
        
        array = new int[9];
        array[8] = 2;
        
        // HLOAD TEMP 1 (base+8*4) 4
        // HLOAD TEMP 1 PLUS TEMP 0 TIMES 8 4 4
        x = array[8];
        System.out.println(x);
    }
}
