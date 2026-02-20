import java.util.Scanner;

public class Ex2_Palindrome {

    public static boolean isPalindrome(String s) {
        if (s.length() <= 1) return true;

        if (s.charAt(0) != s.charAt(s.length() - 1))
            return false;

        return isPalindrome(s.substring(1, s.length() - 1));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Exercise 2: Recursive Palindrome ===");

        while (true) {
            System.out.print("Enter string (type EXIT to stop): ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("EXIT"))
                break;

            if (isPalindrome(input))
                System.out.println("Palindrome ✅");
            else
                System.out.println("Not a palindrome ❌");
        }

        sc.close();
    }
}