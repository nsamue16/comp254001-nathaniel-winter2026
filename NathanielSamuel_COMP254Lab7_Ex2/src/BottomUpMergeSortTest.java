import java.util.Arrays;
import java.util.Random;

/**
 * COMP-254 Lab Assignment #7 – Exercise 2
 * Test driver for Bottom-Up Merge Sort (Queue of Queues)
 *
 * Student: Nathaniel Samuel
 * Course:  COMP-254
 */
public class BottomUpMergeSortTest {

    public static void main(String[] args) {

        System.out.println("=================================================");
        System.out.println(" COMP-254  Lab 7 – Exercise 2");
        System.out.println(" Bottom-Up Merge Sort (Queue of Queues)");
        System.out.println("=================================================\n");

        // ── Test 1: General unsorted integers ────────────────────────────
        System.out.println("--- Test 1: General unsorted integers ---");
        Integer[] arr1 = {38, 27, 43, 3, 9, 82, 10};
        BottomUpMergeSort.printArray("Before", arr1);
        BottomUpMergeSort.sort(arr1);
        BottomUpMergeSort.printArray("After ", arr1);
        System.out.println("Is sorted: " + isSorted(arr1));

        // ── Test 2: Already sorted ────────────────────────────────────────
        System.out.println("\n--- Test 2: Already sorted ---");
        Integer[] arr2 = {1, 2, 3, 4, 5, 6, 7, 8};
        BottomUpMergeSort.printArray("Before", arr2);
        BottomUpMergeSort.sort(arr2);
        BottomUpMergeSort.printArray("After ", arr2);
        System.out.println("Is sorted: " + isSorted(arr2));

        // ── Test 3: Reverse sorted ────────────────────────────────────────
        System.out.println("\n--- Test 3: Reverse sorted ---");
        Integer[] arr3 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        BottomUpMergeSort.printArray("Before", arr3);
        BottomUpMergeSort.sort(arr3);
        BottomUpMergeSort.printArray("After ", arr3);
        System.out.println("Is sorted: " + isSorted(arr3));

        // ── Test 4: Duplicate elements ────────────────────────────────────
        System.out.println("\n--- Test 4: Duplicate elements ---");
        Integer[] arr4 = {5, 3, 5, 1, 3, 2, 4, 1};
        BottomUpMergeSort.printArray("Before", arr4);
        BottomUpMergeSort.sort(arr4);
        BottomUpMergeSort.printArray("After ", arr4);
        System.out.println("Is sorted: " + isSorted(arr4));

        // ── Test 5: Single element ────────────────────────────────────────
        System.out.println("\n--- Test 5: Single element ---");
        Integer[] arr5 = {42};
        BottomUpMergeSort.printArray("Before", arr5);
        BottomUpMergeSort.sort(arr5);
        BottomUpMergeSort.printArray("After ", arr5);
        System.out.println("Is sorted: " + isSorted(arr5));

        // ── Test 6: Two elements ──────────────────────────────────────────
        System.out.println("\n--- Test 6: Two elements ---");
        Integer[] arr6 = {99, 1};
        BottomUpMergeSort.printArray("Before", arr6);
        BottomUpMergeSort.sort(arr6);
        BottomUpMergeSort.printArray("After ", arr6);
        System.out.println("Is sorted: " + isSorted(arr6));

        // ── Test 7: String keys ────────────────────────────────────────────
        System.out.println("\n--- Test 7: String array ---");
        String[] arr7 = {"pear", "apple", "mango", "banana", "cherry"};
        BottomUpMergeSort.printArray("Before", arr7);
        BottomUpMergeSort.sort(arr7);
        BottomUpMergeSort.printArray("After ", arr7);
        System.out.println("Is sorted: " + isSorted(arr7));

        // ── Test 8: Large random array – correctness vs Arrays.sort ───────
        System.out.println("\n--- Test 8: Large random array (n = 10 000) ---");
        int N = 10_000;
        Integer[] arr8      = new Integer[N];
        Integer[] reference = new Integer[N];
        Random rng = new Random(42);
        for (int i = 0; i < N; i++) {
            arr8[i] = reference[i] = rng.nextInt(100_000);
        }
        BottomUpMergeSort.sort(arr8);
        Arrays.sort(reference);
        boolean match = Arrays.equals(arr8, reference);
        System.out.println("Matches Arrays.sort reference: " + match);
        System.out.println("Is sorted: " + isSorted(arr8));
        System.out.println("First 10 elements: " + Arrays.toString(Arrays.copyOf(arr8, 10)));

        System.out.println("\n=================================================");
        System.out.println(" All tests passed!");
        System.out.println("=================================================");
    }

    // ── Utility: verify ascending order ─────────────────────────────────────
    private static <E extends Comparable<E>> boolean isSorted(E[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].compareTo(arr[i + 1]) > 0) return false;
        }
        return true;
    }
}
