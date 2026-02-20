import java.io.File;
import java.util.Scanner;

public class Ex3_FileFinder {

    public static void find(String path, String filename) {
        File root = new File(path);

        if (!root.exists()) {
            System.out.println("Path does not exist.");
            return;
        }

        findRecursive(root, filename);
    }

    private static void findRecursive(File current, String filename) {

        if (current.isFile()) {
            if (current.getName().equals(filename)) {
                System.out.println("FOUND: " + current.getAbsolutePath());
            }
            return;
        }

        if (current.isDirectory()) {
            File[] files = current.listFiles();
            if (files == null) return;

            for (File f : files) {
                findRecursive(f, filename);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Exercise 3: Recursive File Finder ===");

        System.out.print("Enter starting path: ");
        String path = sc.nextLine();

        System.out.print("Enter filename to search: ");
        String filename = sc.nextLine();

        find(path, filename);

        sc.close();
    }
}