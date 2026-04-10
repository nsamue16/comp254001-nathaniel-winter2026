package maps;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * SortedTableMap -- sorted array-backed map.
 *
 * Modified for Lab 6, Exercise 2:
 *   - Added containKey(k) using the existing findIndex(k) helper.
 *
 * containKey(k) resolves the null-value ambiguity described in the assignment:
 * get(k) returns null BOTH when the key is absent AND when the stored value
 * is legitimately null.  containKey(k) returns true only if an entry with
 * exactly that key exists in the map, regardless of its value.
 */
public class SortedTableMap<K,V> extends AbstractSortedMap<K,V> {

    private ArrayList<MapEntry<K,V>> table = new ArrayList<>();

    public SortedTableMap() { super(); }
    public SortedTableMap(Comparator<K> comp) { super(comp); }

    // -------------------------------------------------------------------
    // Internal binary-search helper
    // -------------------------------------------------------------------
    private int findIndex(K key, int low, int high) {
        if (high < low) return high + 1;
        int mid  = (low + high) / 2;
        int comp = compare(key, table.get(mid));
        if (comp == 0)      return mid;
        else if (comp < 0)  return findIndex(key, low, mid - 1);
        else                return findIndex(key, mid + 1, high);
    }

    private int findIndex(K key) { return findIndex(key, 0, table.size() - 1); }

    // -------------------------------------------------------------------
    // Exercise 2: containKey(k)
    // -------------------------------------------------------------------
    /**
     * Returns true if the map contains an entry with the given key.
     *
     * Algorithm:
     *   1. Use findIndex(k) to locate the candidate position j via binary search.
     *   2. If j is within the table bounds AND the entry at j has a key equal
     *      to k, the key is present -> return true.
     *   3. Otherwise the key is absent -> return false.
     *
     * This handles the null-value ambiguity: even if the stored value is null,
     * containKey returns true because the key itself exists in the table.
     *
     * Time complexity: O(log n) -- inherits from findIndex's binary search.
     *
     * @param key  the key to search for
     * @return     true if an entry with key k exists, false otherwise
     */
    public boolean containKey(K key) {
        int j = findIndex(key);                    // binary search for candidate index
        // j is valid AND the entry at j actually matches key
        return j < table.size() && compare(key, table.get(j)) == 0;
    }

    // -------------------------------------------------------------------
    // Standard Map methods (unchanged from textbook)
    // -------------------------------------------------------------------

    @Override public int size() { return table.size(); }

    @Override
    public V get(K key) throws IllegalArgumentException {
        checkKey(key);
        int j = findIndex(key);
        if (j == size() || compare(key, table.get(j)) != 0) return null;
        return table.get(j).getValue();
    }

    @Override
    public V put(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        int j = findIndex(key);
        if (j < size() && compare(key, table.get(j)) == 0)
            return table.get(j).setValue(value);
        table.add(j, new MapEntry<K,V>(key, value));
        return null;
    }

    @Override
    public V remove(K key) throws IllegalArgumentException {
        checkKey(key);
        int j = findIndex(key);
        if (j == size() || compare(key, table.get(j)) != 0) return null;
        return table.remove(j).getValue();
    }

    private Entry<K,V> safeEntry(int j) {
        if (j < 0 || j >= table.size()) return null;
        return table.get(j);
    }

    @Override public Entry<K,V> firstEntry()  { return safeEntry(0); }
    @Override public Entry<K,V> lastEntry()   { return safeEntry(table.size() - 1); }

    @Override
    public Entry<K,V> ceilingEntry(K key) throws IllegalArgumentException {
        return safeEntry(findIndex(key));
    }

    @Override
    public Entry<K,V> floorEntry(K key) throws IllegalArgumentException {
        int j = findIndex(key);
        if (j == size() || !key.equals(table.get(j).getKey())) j--;
        return safeEntry(j);
    }

    @Override
    public Entry<K,V> lowerEntry(K key) throws IllegalArgumentException {
        return safeEntry(findIndex(key) - 1);
    }

    @Override
    public Entry<K,V> higherEntry(K key) throws IllegalArgumentException {
        int j = findIndex(key);
        if (j < size() && key.equals(table.get(j).getKey())) j++;
        return safeEntry(j);
    }

    private Iterable<Entry<K,V>> snapshot(int startIndex, K stop) {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>();
        int j = startIndex;
        while (j < table.size() && (stop == null || compare(stop, table.get(j)) > 0))
            buffer.add(table.get(j++));
        return buffer;
    }

    @Override public Iterable<Entry<K,V>> entrySet()                    { return snapshot(0, null); }
    @Override public Iterable<Entry<K,V>> subMap(K fromKey, K toKey)    { return snapshot(findIndex(fromKey), toKey); }
}
