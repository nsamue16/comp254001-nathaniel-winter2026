import java.util.ArrayDeque;
import java.util.Queue;

/**
 * COMP-254 Lab Assignment #7 – Exercise 2
 * Bottom-Up Merge Sort using a Queue of Queues
 *
 * Algorithm:
 *   1. Place each item in its own single-element queue.
 *   2. Put all those queues into an outer "queue of queues".
 *   3. Repeatedly dequeue two queues from the outer queue,
 *      merge them into one sorted queue, and enqueue the result.
 *   4. Stop when the outer queue contains exactly one queue —
 *      that queue holds all items in sorted order.
 *
 * Student: Nathaniel Samuel
 * Course:  COMP-254
 */
public class BottomUpMergeSort {

    // ── Merge two sorted queues into one sorted queue ────────────────────────
    /**
     * Merges two sorted queues (S1, S2) into a single sorted queue (result).
     * Each element from S1 / S2 is consumed exactly once → O(n1 + n2).
     */
    private static <E extends Comparable<E>>
    Queue<E> merge(Queue<E> s1, Queue<E> s2) {

        Queue<E> result = new ArrayDeque<>();

        while (!s1.isEmpty() && !s2.isEmpty()) {
            // take the smaller front element
            if (s1.peek().compareTo(s2.peek()) <= 0) {
                result.add(s1.poll());
            } else {
                result.add(s2.poll());
            }
        }

        // drain whatever remains
        while (!s1.isEmpty()) result.add(s1.poll());
        while (!s2.isEmpty()) result.add(s2.poll());

        return result;
    }

    // ── Bottom-up merge sort ─────────────────────────────────────────────────
    /**
     * Sorts the given array in ascending order using bottom-up merge sort.
     *
     * @param data   array of comparable items to be sorted (modified in place)
     */
    public static <E extends Comparable<E>> void sort(E[] data) {

        if (data == null || data.length <= 1) return;

        // Step 1 – create a queue of single-element queues
        Queue<Queue<E>> outerQueue = new ArrayDeque<>();

        for (E item : data) {
            Queue<E> singleQueue = new ArrayDeque<>();
            singleQueue.add(item);
            outerQueue.add(singleQueue);
        }

        // Step 2 – repeatedly merge pairs until one queue remains
        while (outerQueue.size() > 1) {
            Queue<E> s1 = outerQueue.poll();   // dequeue first
            Queue<E> s2 = outerQueue.poll();   // dequeue second
            outerQueue.add(merge(s1, s2));     // re-enqueue merged result
        }

        // Step 3 – write the sorted elements back into the original array
        Queue<E> sorted = outerQueue.poll();
        int index = 0;
        while (sorted != null && !sorted.isEmpty()) {
            data[index++] = sorted.poll();
        }
    }

    // ── Helper: print an array ───────────────────────────────────────────────
    public static <E> void printArray(String label, E[] arr) {
        System.out.print(label + ": [");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}
