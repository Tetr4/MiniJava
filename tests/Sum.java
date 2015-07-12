class Sum {
    public static void main(String[] args) {
        int n;
        int sum;
        n = 5;
        sum = 0;
        while (0 < n) {
            sum = sum + n;
            n = n - 1;
        }
        System.out.println(sum);
    }
}
