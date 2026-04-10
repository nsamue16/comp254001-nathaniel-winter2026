package maps;

import java.util.Random;

/**
 * Lab 6, Exercise 1 -- Test Driver
 *
 * Experiments with ChainHashMap and ProbeHashMap using:
 *   - random key sets of varying size
 *   - different maximum load-factor limits (0.25, 0.5, 0.75)
 *
 * For each combination we insert N random keys, then retrieve them all
 * and record the wall-clock time so efficiency differences become visible.
 */
public class TestHashMaps {

    // Sizes of key sets to experiment with
    private static final int[] KEY_COUNTS  = {1_000, 10_000, 100_000};

    // Load-factor limits to compare
    private static final double[] LOAD_FACTORS = {0.25, 0.50, 0.75};

    public static void main(String[] args) {

        System.out.println("==========================================================");
        System.out.println(" Lab 6, Exercise 1 -- Hash Map Efficiency Experiment");
        System.out.println("==========================================================\n");

        System.out.printf("%-20s %-8s %-10s %-18s %-18s%n",
                "Map Type", "Load", "Keys (N)", "Insert (ms)", "Lookup (ms)");
        System.out.println("----------------------------------------------------------" +
                           "------------------");

        for (double load : LOAD_FACTORS) {
            for (int n : KEY_COUNTS) {

                // Generate random Integer keys once, shared by both map types
                int[] keys = randomKeys(n);

                // ---- ChainHashMap ----
                ChainHashMap<Integer,String> chain = new ChainHashMap<>(17, load);
                long t0 = System.currentTimeMillis();
                for (int k : keys) chain.put(k, "v" + k);
                long insertChain = System.currentTimeMillis() - t0;

                t0 = System.currentTimeMillis();
                for (int k : keys) chain.get(k);
                long lookupChain = System.currentTimeMillis() - t0;

                System.out.printf("%-20s %-8.2f %-10d %-18d %-18d%n",
                        "ChainHashMap", load, n, insertChain, lookupChain);

                // ---- ProbeHashMap ----
                ProbeHashMap<Integer,String> probe = new ProbeHashMap<>(17, load);
                t0 = System.currentTimeMillis();
                for (int k : keys) probe.put(k, "v" + k);
                long insertProbe = System.currentTimeMillis() - t0;

                t0 = System.currentTimeMillis();
                for (int k : keys) probe.get(k);
                long lookupProbe = System.currentTimeMillis() - t0;

                System.out.printf("%-20s %-8.2f %-10d %-18d %-18d%n",
                        "ProbeHashMap", load, n, insertProbe, lookupProbe);

                System.out.println();
            }
        }

        // ----------------------------------------------------------------
        // Correctness check: demonstrate setMaxLoadFactor()
        // ----------------------------------------------------------------
        System.out.println("==========================================================");
        System.out.println(" Correctness / setMaxLoadFactor() Demo");
        System.out.println("==========================================================");

        ChainHashMap<Integer,String> demo = new ChainHashMap<>(5);
        System.out.println("\nInitial ChainHashMap (default load ≤ 0.5):");
        demo.put(1, "one"); demo.put(2, "two"); demo.put(3, "three");
        System.out.println("  size=" + demo.size()
                + "  capacity=" + demo.capacity
                + "  load=" + String.format("%.2f", demo.getMaxLoadFactor()));

        System.out.println("\nChanging load factor to 0.25 (forces immediate resize if needed):");
        demo.setMaxLoadFactor(0.25);
        System.out.println("  size=" + demo.size()
                + "  capacity=" + demo.capacity
                + "  load=" + String.format("%.2f", demo.getMaxLoadFactor()));

        // Verify values survived the rehash
        System.out.println("  get(1)=" + demo.get(1)
                + "  get(2)=" + demo.get(2)
                + "  get(3)=" + demo.get(3));

        System.out.println("\nChainHashMap with high load (0.75) -- fewer resizes, more chaining:");
        ChainHashMap<Integer,String> high = new ChainHashMap<>(5, 0.75);
        for (int i = 1; i <= 10; i++) high.put(i, "val" + i);
        System.out.println("  Inserted 10 entries.  size=" + high.size()
                + "  capacity=" + high.capacity);
        for (int i = 1; i <= 10; i++)
            System.out.print("  " + i + "→" + high.get(i));
        System.out.println();

        System.out.println("\nProbeHashMap with low load (0.25) -- more resizes, fewer collisions:");
        ProbeHashMap<Integer,String> low = new ProbeHashMap<>(5, 0.25);
        for (int i = 1; i <= 10; i++) low.put(i, "val" + i);
        System.out.println("  Inserted 10 entries.  size=" + low.size()
                + "  capacity=" + low.capacity);
        for (int i = 1; i <= 10; i++)
            System.out.print("  " + i + "→" + low.get(i));
        System.out.println();

        System.out.println("\nDone.");
    }

    /** Returns an array of n distinct random Integer keys. */
    private static int[] randomKeys(int n) {
        Random rng = new Random(42);
        java.util.Set<Integer> seen = new java.util.HashSet<>();
        int[] keys = new int[n];
        int idx = 0;
        while (idx < n) {
            int k = rng.nextInt(Integer.MAX_VALUE);
            if (seen.add(k)) keys[idx++] = k;
        }
        return keys;
    }
}
