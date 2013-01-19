package collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This {@code BuilderMap} can be used to quickly construct a map from another collection or as a base class to simplify
 * building a map that self populate from a custom type.
 * <p/>
 * It is constructed with an {@link EntryBuilder} and an optional backing {@link Map}.
 * <p/>
 * The {@link EntryBuilder#buildEntry()} method must be implemented to provide the logic that will be used to build the
 * map. The {@link EntryBuilder#buildEntry()} method will be repeatedly called until it returns {@code null}.
 * <p/>
 * The backing map is the actual collection that will hold the built keys and values.
 * <p/>
 * Example:
 * <code>
 * Map<Integer, String> map = new HashMap<>();
 * map.put(4, "four");
 * map.put(5, "five");
 * map.put(6, "six");
 * <p/>
 * final Iterator<String> numbers = Arrays.asList("one", "two", "three").iterator();
 * <p/>
 * Map<Integer, String> builderMap = new BuilderMap<>(new EntryBuilder<Integer, String>() {
 * <p/>
 * private int i = 0;
 *
 * @param <K> the generic type of the maps keys.
 * @param <V> the generic type of the maps values.
 * @Override public Entry<Integer, String> buildEntry() {
 * <p/>
 * if (numbers.hasNext()) return new SimpleEntry<Integer, String>(++i, numbers.next());
 * <p/>
 * return null;
 * }
 * }, map); // {1=one, 2=two, 3=three, 4=four, 5=five, 6=six}
 * </code>
 */
public class BuilderMap<K, V> implements Map<K, V> {

    /**
     * Interface that provides a method for building map entries.
     *
     * @param <K> the generic type of the entries keys.
     * @param <V> the generic type of the entries values.
     */
    protected static interface EntryBuilder<K, V> {

        /**
         * Build a map entry.
         *
         * @return the built entry.
         */
        public Entry<K, V> buildEntry();
    }


    private final Map<K, V> map;


    /**
     * Instantiate a new {@code BuilderMap} that will use the supplied {@link EntryBuilder} to build it's entries and
     * the supplied backing {@link Map} to hold it's keys and values.
     *
     * @param entryBuilder the builder used to build the entries for the new map.
     * @param map          the map that will be used to hold the built keys and value.
     */
    public BuilderMap(EntryBuilder<K, V> entryBuilder, Map<K, V> map) {

        if (null == map) {

            throw new IllegalArgumentException(getClass().getName() + "(Map,EntryBuilder) map must not be null.");
        }

        if (null == entryBuilder) {

            throw new IllegalArgumentException(getClass().getName() + "(Map,EntryBuilder) entryBuilder must not be null.");
        }

        this.map = map;

        for (Entry<K, V> entry = entryBuilder.buildEntry(); null != entry; entry = entryBuilder.buildEntry()) {

            this.map.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Instantiate a new {@code BuilderMap} that will use the supplied {@link EntryBuilder} to build it's entries.
     *
     * @param entryBuilder the builder used to build the entries for the new map.
     */
    public BuilderMap(EntryBuilder<K, V> entryBuilder) {

        this(entryBuilder, new HashMap<K, V>());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {

        return map.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {

        return map.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key) {

        return map.containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(Object value) {

        return map.containsValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(Object key) {

        return map.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {

        return map.put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(Object key) {

        return map.remove(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {

        this.map.putAll(map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {

        map.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<K> keySet() {

        return map.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<V> values() {

        return map.values();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Entry<K, V>> entrySet() {

        return map.entrySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BuilderMap that = (BuilderMap) o;

        return map.equals(that.map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return map.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        return map.toString();
    }
}
