/**
 * COMP-254 Lab Assignment #7 - Exercise 1
 * Iterative Binary Search Tree (treeSearch without recursion)
 *
 * Student: Nathaniel Samuel
 * Course:  COMP-254
 */
public class BinarySearchTree<K extends Comparable<K>, V> {

    // ── Node ────────────────────────────────────────────────────────────────
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left, right, parent;

        Node(K key, V value, Node<K, V> parent) {
            this.key    = key;
            this.value  = value;
            this.parent = parent;
        }

        boolean isExternal() { return left == null && right == null; }
    }

    // ── Fields ───────────────────────────────────────────────────────────────
    private Node<K, V> root = null;
    private int size = 0;

    // ── Iterative treeSearch ─────────────────────────────────────────────────
    /**
     * Iterative equivalent of Code Fragment 11.3's recursive treeSearch.
     *
     * Starting at 'node', walk down the tree comparing key against each
     * node's key:
     *   – if equal  → return this node (found)
     *   – if less   → move left
     *   – if greater→ move right
     *   – if we hit null → return the last visited node (insertion point)
     *
     * Using a loop instead of recursion avoids StackOverflowError on large,
     * unbalanced trees where the recursive depth could exceed Java's call-
     * stack limit.
     */
    private Node<K, V> treeSearch(Node<K, V> node, K key) {
        Node<K, V> current = node;
        Node<K, V> last    = null;

        while (current != null) {
            last = current;                          // remember last visited
            int cmp = key.compareTo(current.key);

            if (cmp == 0) {
                return current;                      // exact match
            } else if (cmp < 0) {
                current = current.left;              // go left
            } else {
                current = current.right;             // go right
            }
        }

        return last;   // null tree → null; otherwise the insertion-point node
    }

    // ── Public BST operations ────────────────────────────────────────────────

    /** Returns the value for the given key, or null if not found. */
    public V get(K key) {
        if (root == null) return null;
        Node<K, V> node = treeSearch(root, key);
        if (node != null && key.compareTo(node.key) == 0) {
            return node.value;
        }
        return null;
    }

    /** Inserts or updates a key-value pair. */
    public void put(K key, V value) {
        if (root == null) {
            root = new Node<>(key, value, null);
            size++;
            return;
        }

        Node<K, V> node = treeSearch(root, key);
        int cmp = key.compareTo(node.key);

        if (cmp == 0) {
            node.value = value;          // update existing
        } else if (cmp < 0) {
            node.left = new Node<>(key, value, node);
            size++;
        } else {
            node.right = new Node<>(key, value, node);
            size++;
        }
    }

    /** Returns true if the key exists in the tree. */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() { return size; }

    // ── In-order traversal (sorted output) ──────────────────────────────────
    public void printInOrder() {
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(Node<K, V> node) {
        if (node == null) return;
        printInOrder(node.left);
        System.out.print(node.key + "=" + node.value + "  ");
        printInOrder(node.right);
    }
}
