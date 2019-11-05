package cn.pasteme.algorithm.pair;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Lucien Shui
 * @version 1.0.3
 */
@Data
public class Pair<K, V> implements Serializable {

    private static final long serialVersionUID = 4215719901707876545L;

    private K key;

    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
