package cn.pasteme.algorithm.trie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import cn.pasteme.algorithm.trie.node.Node;

/**
 * 简化版字典树，支持离线 build 成 model
 *
 * @author Lucien
 * @version 1.0.0
 */
@Data
public class Trie implements Serializable {

    /**
     * 字典树根节点
     */
    private Node root;

    /**
     * 新建时初始化根节点
     */
    public Trie() {
        this.root = new Node(0);
    }

    /**
     * 向字典中添加词汇
     *
     * @param word 词汇
     */
    public void insert(String word) {
        Node buffer = this.root;
        for (Character character : word.toCharArray()) {
            buffer = buffer.put(character);
        }
    }

    /**
     * 得到与当前 prefix match 的所有 string
     *
     * @param prefix 前缀
     * @return String list
     */
    public List<String> getSuffixByPrefix(String prefix) {
        return null;
    }

    /**
     * 得到以当前节点为起点的所有 suffix
     *
     * @param node Node
     * @return String list
     */
    private List<String> getSuffixByPrefix(String prefix, Node node) {
        List<String> result = new ArrayList<>();
        for (Character character : node.getNext().keySet()) {
            result.addAll(getSuffixByPrefix(prefix + character, node.getNext().get(character)));
        }
        return result;
    }

    /**
     * 前缀搜索子函数
     * 走完这个字符串可以到达的节点
     * 如果不能完全 match 的话返回 null
     *
     * @param prefix 前缀
     * @return 完全匹配 prefix 的 Node
     */
    private Node matchNode(String prefix) {
        Node current = this.root, buffer;
        for (int i = 0; i < prefix.length(); i++) {
            buffer = current.get(prefix.charAt(i));
            if (null == buffer) {
                return null;
            }
            current = buffer;
        }
        return current.getDepth() == prefix.length() ? current : null;
    }

    /**
     * 判断词汇是否在字典树中
     *
     * @param word 词汇
     * @return boolean
     */
    public boolean exist(String word) {
        Node current = this.root, buffer;
        for (int i = 0; i < word.length(); i++) {
            buffer = current.get(word.charAt(i));
            if (null == buffer) {
                return false;
            }
            current = buffer;
        }
        return current.getDepth() == word.length();
    }
}
