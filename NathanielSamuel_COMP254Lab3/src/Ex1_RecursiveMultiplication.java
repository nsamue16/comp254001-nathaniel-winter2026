import java.util.Scanner;

public class Ex1_RecursiveMultiplication {

    public static long multiply(long m, long n) {
        if (n == 0) return 0;   // base case
        return m + multiply(m, n - 1);  // recursive case
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Exercise 1: Recursive Multiplication ===");
        System.out.print("Enter positive integer m: ");
        long m = sc.nextLong();

        System.out.print("Enter positive integer n: ");
        long n = sc.nextLong();

        if (m < 0 || n < 0) {
            System.out.println("Please enter positive integers only.");
        } else {
            System.out.println("Result: " + multiply(m, n));
        }

        sc.close();
    }
}