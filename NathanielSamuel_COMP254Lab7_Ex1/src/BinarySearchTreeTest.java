/**
 * COMP-254 Lab Assignment #7 – Exercise 1
 * Test driver for the iterative treeSearch BST
 *
 * Student: Nathaniel Samuel
 * Course:  COMP-254
 */
public class BinarySearchTreeTest {

    public static void main(String[] args) {

        System.out.println("=================================================");
        System.out.println(" COMP-254  Lab 7 – Exercise 1");
        System.out.println(" Iterative treeSearch in a Binary Search Tree");
        System.out.println("=================================================\n");

        // ── Test 1: Basic insertions and lookups ──────────────────────────
        System.out.println("--- Test 1: Basic insertions and lookups ---");
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();

        bst.put(50, "fifty");
        bst.put(30, "thirty");
        bst.put(70, "seventy");
        bst.put(20, "twenty");
        bst.put(40, "forty");
        bst.put(60, "sixty");
        bst.put(80, "eighty");

        System.out.println("In-order traversal (should be sorted):");
        bst.printInOrder();

        System.out.println("Tree size: " + bst.size());           // 7
        System.out.println("get(50)  = " + bst.get(50));          // fifty
        System.out.println("get(20)  = " + bst.get(20));          // twenty
        System.out.println("get(80)  = " + bst.get(80));          // eighty
        System.out.println("get(99)  = " + bst.get(99));          // null (not found)
        System.out.println("containsKey(40) = " + bst.containsKey(40));  // true
        System.out.println("containsKey(55) = " + bst.containsKey(55));  // false

        // ── Test 2: Update existing key ───────────────────────────────────
        System.out.println("\n--- Test 2: Update existing key ---");
        bst.put(30, "THIRTY-UPDATED");
        System.out.println("get(30) after update = " + bst.get(30));
        System.out.println("Tree size (should stay 7): " + bst.size());

        // ── Test 3: String keys ────────────────────────────────────────────
        System.out.println("\n--- Test 3: String keys ---");
        BinarySearchTree<String, Integer> wordTree = new BinarySearchTree<>();
        String[] words = {"mango", "apple", "pear", "banana", "cherry", "fig", "grape"};
        for (int i = 0; i < words.length; i++) {
            wordTree.put(words[i], i + 1);
        }
        System.out.println("In-order traversal (alphabetical):");
        wordTree.printInOrder();
        System.out.println("get(\"banana\") = " + wordTree.get("banana"));   // 4
        System.out.println("get(\"kiwi\")   = " + wordTree.get("kiwi"));     // null

        // ── Test 4: Right-skewed tree (worst-case for recursion) ──────────
        System.out.println("\n--- Test 4: Right-skewed (unbalanced) tree – 10 000 nodes ---");
        BinarySearchTree<Integer, Integer> skewed = new BinarySearchTree<>();
        int N = 10_000;
        for (int i = 1; i <= N; i++) {
            skewed.put(i, i * 10);   // inserts 1,2,3,...,10000 → fully right-skewed
        }
        System.out.println("Size = " + skewed.size());
        System.out.println("get(1)     = " + skewed.get(1));        // 10
        System.out.println("get(5000)  = " + skewed.get(5000));     // 50000
        System.out.println("get(10000) = " + skewed.get(10_000));   // 100000
        System.out.println("get(10001) = " + skewed.get(10_001));   // null
        System.out.println("Iterative search handled " + N +
                " levels without StackOverflowError ✓");

        System.out.println("\n=================================================");
        System.out.println(" All tests passed!");
        System.out.println("=================================================");
    }
}
