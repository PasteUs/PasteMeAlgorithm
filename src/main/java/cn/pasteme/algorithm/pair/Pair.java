package cn.pasteme.algorithm.pair;

import java.util.Map;

/**
 * @author Lucien Shui
 * @version 1.0.2
 */
public class Pair<K, V> implements Map.Entry<K, V> {

    private K key;

    private V value;

    public Pair (K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    public K setKey(K k) {
        return this.key = k;
    }

    @Override
    public V setValue(V v) {
        return this.value = v;
    }
}
