package maps;

import java.util.ArrayList;
import java.util.Random;

/**
 * An abstract base class supporting Map implementations that use hash
 * tables with MAD compression.
 *
 * Modified for Lab 6, Exercise 1:
 *   - Added configurable maximum load factor (default 0.5)
 *   - Constructors and a setter let callers specify the limit
 *   - resize() is triggered whenever n > capacity * maxLoadFactor
 */
public abstract class AbstractHashMap<K,V> extends AbstractMap<K,V> {
    protected int n = 0;          // number of entries in the dictionary
    protected int capacity;       // length of the table
    private int prime;            // prime factor
    private long scale, shift;    // MAD compression coefficients
    private double maxLoadFactor; // *** NEW: user-configurable load-factor ceiling ***

    // -----------------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------------

    /** Full constructor: capacity, prime factor, and load-factor ceiling. */
    public AbstractHashMap(int cap, int p, double maxLoad) {
        prime        = p;
        capacity     = cap;
        maxLoadFactor = maxLoad;
        Random rand  = new Random();
        scale = rand.nextInt(prime - 1) + 1;
        shift = rand.nextInt(prime);
        createTable();
    }

    /** Capacity + load factor; uses default prime 109345121. */
    public AbstractHashMap(int cap, double maxLoad) { this(cap, 109345121, maxLoad); }

    /** Capacity + prime; uses default load factor 0.5. */
    public AbstractHashMap(int cap, int p) { this(cap, p, 0.5); }

    /** Capacity only; default prime and default load factor 0.5. */
    public AbstractHashMap(int cap) { this(cap, 109345121, 0.5); }

    /** No-arg: capacity 17, default prime, default load factor 0.5. */
    public AbstractHashMap() { this(17); }

    // -----------------------------------------------------------------------
    // Load-factor getter / setter
    // -----------------------------------------------------------------------

    /** Returns the maximum load factor currently in use. */
    public double getMaxLoadFactor() { return maxLoadFactor; }

    /**
     * Sets a new maximum load factor.  If the current load already exceeds
     * the new limit a resize is triggered immediately.
     */
    public void setMaxLoadFactor(double maxLoad) {
        this.maxLoadFactor = maxLoad;
        if (n > capacity * maxLoadFactor)
            resize(2 * capacity - 1);
    }

    // -----------------------------------------------------------------------
    // Public Map interface
    // -----------------------------------------------------------------------

    @Override public int size()          { return n; }
    @Override public V   get(K key)      { return bucketGet(hashValue(key), key); }
    @Override public V   remove(K key)   { return bucketRemove(hashValue(key), key); }

    @Override
    public V put(K key, V value) {
        V answer = bucketPut(hashValue(key), key, value);
        if (n > capacity * maxLoadFactor)   // respects the configurable ceiling
            resize(2 * capacity - 1);
        return answer;
    }

    // -----------------------------------------------------------------------
    // Private utilities
    // -----------------------------------------------------------------------

    private int hashValue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    private void resize(int newCap) {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(n);
        for (Entry<K,V> e : entrySet()) buffer.add(e);
        capacity = newCap;
        createTable();
        n = 0;
        for (Entry<K,V> e : buffer) put(e.getKey(), e.getValue());
    }

    // -----------------------------------------------------------------------
    // Abstract methods implemented by ChainHashMap / ProbeHashMap
    // -----------------------------------------------------------------------
    protected abstract void createTable();
    protected abstract V   bucketGet(int h, K k);
    protected abstract V   bucketPut(int h, K k, V v);
    protected abstract V   bucketRemove(int h, K k);
}
