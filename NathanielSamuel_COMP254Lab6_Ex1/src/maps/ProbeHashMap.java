package maps;

import java.util.ArrayList;

/**
 * ProbeHashMap -- open-addressing hash table with linear probing.
 * Written for Lab 6 Exercise 1 to demonstrate the configurable load-factor
 * feature alongside ChainHashMap.
 *
 * Uses a DEFUNCT sentinel to correctly handle deletions so that subsequent
 * probes are not terminated prematurely.
 */
public class ProbeHashMap<K,V> extends AbstractHashMap<K,V> {
    private MapEntry<K,V>[] table;                       // fixed array of entries
    private final MapEntry<K,V> DEFUNCT =
            new MapEntry<>(null, null);                  // sentinel for deleted slots

    // --- original-style constructors ---
    public ProbeHashMap()                     { super(); }
    public ProbeHashMap(int cap)              { super(cap); }
    public ProbeHashMap(int cap, int p)       { super(cap, p); }

    // --- NEW constructors accepting a load-factor limit ---
    public ProbeHashMap(int cap, double maxLoad)        { super(cap, maxLoad); }
    public ProbeHashMap(int cap, int p, double maxLoad) { super(cap, p, maxLoad); }

    @Override @SuppressWarnings("unchecked")
    protected void createTable() {
        table = (MapEntry<K,V>[]) new MapEntry[capacity];
    }

    /** True if the slot is empty or holds the DEFUNCT sentinel. */
    private boolean isAvailable(int j) {
        return (table[j] == null || table[j] == DEFUNCT);
    }

    /**
     * Returns the index where key k is stored, or -(a+1) where a is the
     * index of the first available slot (for use by put).
     */
    private int findSlot(int h, K k) {
        int avail = -1;
        int j = h;
        do {
            if (isAvailable(j)) {
                if (avail == -1) avail = j;          // first available slot
                if (table[j] == null) break;          // definite miss – stop
            } else if (table[j].getKey().equals(k)) {
                return j;                             // found it
            }
            j = (j + 1) % capacity;
        } while (j != h);
        return -(avail + 1);                          // not found; encode avail
    }

    @Override
    protected V bucketGet(int h, K k) {
        int j = findSlot(h, k);
        return (j < 0) ? null : table[j].getValue();
    }

    @Override
    protected V bucketPut(int h, K k, V v) {
        int j = findSlot(h, k);
        if (j >= 0) {                                 // key already exists
            return table[j].setValue(v);
        }
        table[-(j + 1)] = new MapEntry<>(k, v);      // place at first available
        n++;
        return null;
    }

    @Override
    protected V bucketRemove(int h, K k) {
        int j = findSlot(h, k);
        if (j < 0) return null;                       // not found
        V old = table[j].getValue();
        table[j] = DEFUNCT;                           // mark as deleted
        n--;
        return old;
    }

    @Override
    public Iterable<Entry<K,V>> entrySet() {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        for (int h = 0; h < capacity; h++)
            if (!isAvailable(h)) buffer.add(table[h]);
        return buffer;
    }
}
