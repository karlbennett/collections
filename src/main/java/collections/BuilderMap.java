package collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BuilderMap<K, V> implements Map<K, V> {

    protected static interface EntryBuilder<K, V> {

        public Entry<K, V> buildEntry();
    }


    private final Map<K, V> map;


    public BuilderMap(EntryBuilder<K, V> entryBuilder) {

        this(new HashMap<K, V>(), entryBuilder);
    }

    public BuilderMap(Map<K, V> map, EntryBuilder<K, V> entryBuilder) {

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


    @Override
    public int size() {

        return map.size();
    }

    @Override
    public boolean isEmpty() {

        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {

        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {

        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {

        return map.get(key);
    }

    @Override
    public V put(K key, V value) {

        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {

        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {

        this.map.putAll(map);
    }

    @Override
    public void clear() {

        map.clear();
    }

    @Override
    public Set<K> keySet() {

        return map.keySet();
    }

    @Override
    public Collection<V> values() {

        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {

        return map.entrySet();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BuilderMap that = (BuilderMap) o;

        return map.equals(that.map);
    }

    @Override
    public int hashCode() {

        return map.hashCode();
    }

    @Override
    public String toString() {

        return map.toString();
    }
}
