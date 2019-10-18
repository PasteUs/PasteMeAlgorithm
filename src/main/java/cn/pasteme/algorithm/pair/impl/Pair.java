package cn.pasteme.algorithm.pair.impl;

/**
 * @author Lucien Shui
 * @version 1.0.1
 */
public class Pair<K, V> implements cn.pasteme.algorithm.pair.Pair<K, V> {

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
