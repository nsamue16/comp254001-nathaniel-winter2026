package maps;

/**
 * Lab 6, Exercise 2 -- Test Driver
 *
 * Demonstrates the containKey(k) method on SortedTableMap.
 *
 * Tests:
 *   1. containKey on a key that exists with a normal (non-null) value
 *   2. containKey on a key that exists with a null value  <-- the core ambiguity case
 *   3. containKey on a key that is absent
 *   4. containKey on an empty map
 *   5. containKey after remove (key no longer present)
 *   6. Range of integer keys to confirm binary-search correctness across the table
 */
public class TestSortedTableMap {

    public static void main(String[] args) {

        System.out.println("==========================================================");
        System.out.println(" Lab 6, Exercise 2 -- SortedTableMap.containKey() Demo");
        System.out.println("==========================================================\n");

        SortedTableMap<Integer, String> map = new SortedTableMap<>();

        // ----------------------------------------------------------------
        // Test 1: containKey on empty map
        // ----------------------------------------------------------------
        System.out.println("--- Test 1: Empty map ---");
        System.out.println("containKey(5) on empty map -> " + map.containKey(5));
        System.out.println("Expected: false\n");

        // ----------------------------------------------------------------
        // Test 2: Insert entries and check present keys
        // ----------------------------------------------------------------
        System.out.println("--- Test 2: Keys that exist with normal values ---");
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");
        map.put(40, "forty");
        map.put(50, "fifty");

        System.out.println("Inserted keys: 10, 20, 30, 40, 50");
        System.out.println("containKey(10) -> " + map.containKey(10) + "  [expected: true]");
        System.out.println("containKey(30) -> " + map.containKey(30) + "  [expected: true]");
        System.out.println("containKey(50) -> " + map.containKey(50) + "  [expected: true]");
        System.out.println();

        // ----------------------------------------------------------------
        // Test 3: Keys that are absent
        // ----------------------------------------------------------------
        System.out.println("--- Test 3: Keys that do NOT exist ---");
        System.out.println("containKey(15) -> " + map.containKey(15) + "  [expected: false]");
        System.out.println("containKey(0)  -> " + map.containKey(0)  + "  [expected: false]");
        System.out.println("containKey(99) -> " + map.containKey(99) + "  [expected: false]");
        System.out.println();

        // ----------------------------------------------------------------
        // Test 4: THE KEY AMBIGUITY CASE -- null value stored legitimately
        // ----------------------------------------------------------------
        System.out.println("--- Test 4: Key exists but its VALUE is null ---");
        System.out.println("(This is the case get(k) cannot distinguish from 'key not found')");
        map.put(25, null);   // deliberately store null as the value
        System.out.println("put(25, null) -- key 25 is now in the map with value null");
        System.out.println();
        System.out.println("get(25)        -> " + map.get(25)        + "  (null -- ambiguous!)");
        System.out.println("containKey(25) -> " + map.containKey(25) + "  [expected: true]");
        System.out.println("containKey(26) -> " + map.containKey(26) + "  [expected: false]");
        System.out.println("get(26)        -> " + map.get(26)        + "  (null -- same result as above!)");
        System.out.println();
        System.out.println("==> containKey correctly distinguishes a stored null value");
        System.out.println("    from a missing key, which get() alone cannot do.\n");

        // ----------------------------------------------------------------
        // Test 5: containKey after remove
        // ----------------------------------------------------------------
        System.out.println("--- Test 5: containKey after remove ---");
        System.out.println("Before remove: containKey(20) -> " + map.containKey(20));
        map.remove(20);
        System.out.println("After  remove: containKey(20) -> " + map.containKey(20) + "  [expected: false]");
        System.out.println("After  remove: containKey(30) -> " + map.containKey(30) + "  [expected: true  (unaffected)]");
        System.out.println();

        // ----------------------------------------------------------------
        // Test 6: Boundary keys (first and last)
        // ----------------------------------------------------------------
        System.out.println("--- Test 6: Boundary keys ---");
        System.out.println("Smallest key in map: " + map.firstEntry().getKey());
        System.out.println("Largest  key in map: " + map.lastEntry().getKey());
        System.out.println("containKey(" + map.firstEntry().getKey() + ") -> "
                + map.containKey(map.firstEntry().getKey()) + "  [expected: true]");
        System.out.println("containKey(" + map.lastEntry().getKey()  + ") -> "
                + map.containKey(map.lastEntry().getKey())  + "  [expected: true]");
        System.out.println("containKey(9)  -> " + map.containKey(9)  + "  [expected: false]");
        System.out.println("containKey(51) -> " + map.containKey(51) + "  [expected: false]");
        System.out.println();

        // ----------------------------------------------------------------
        // Test 7: All entries in the map
        // ----------------------------------------------------------------
        System.out.println("--- Test 7: Print all remaining entries ---");
        System.out.println("(Demonstrates sorted order is maintained)");
        for (Entry<Integer, String> e : map.entrySet()) {
            System.out.println("  key=" + e.getKey() + "  value=" + e.getValue()
                    + "  containKey=" + map.containKey(e.getKey()));
        }

        System.out.println("\nAll tests complete.");
    }
}
