package cn.pasteme.algorithm.trie.node;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import lombok.Data;

/**
 * 字典树的节点
 *
 * @author Lucien
 * @version 1.0.0
 */
@Data
public class Node implements Serializable {

    /**
     * 节点的深度就是当前匹配串的长度
     */
    private int depth;

    /**
     * 记录转移状态
     */
    private Map<Character, Node> next;

    /**
     * 新建一个深度为 depth 的节点
     *
     * @param depth 深度
     */
    public Node(int depth) {
        this.depth = depth;
        this.next = new TreeMap<>();
    }

    /**
     * 当前节点沿着某字符会到达的节点
     *
     * @param character 字符
     * @return Node
     */
    public Node get(Character character) {
        if (null == this.next) {
            this.next = new TreeMap<>();
        }

        if (this.next.containsKey(character)) {
            return this.next.get(character);
        }

        return null;
    }

    /**
     * 为当前节点添加一个由某字符转移的子节点
     *
     * @param character 字符
     * @return Node
     */
    public Node put(Character character) {
        Node buffer = this.get(character);
        if (null != buffer) {
            return buffer;
        }

        buffer = new Node(this.depth + 1);
        this.next.put(character, buffer);

        return buffer;
    }

    /**
     * 返回当前节点有多少子节点
     *
     * @return 子节点数量
     */
    Integer size() {
        return this.next.keySet().size();
    }
}
