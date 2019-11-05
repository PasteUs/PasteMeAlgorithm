package cn.pasteme.algorithm.trie.impl;

import cn.pasteme.algorithm.trie.AbstractTrie;
import cn.pasteme.algorithm.trie.Trie;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 简化版字典树，支持离线 build 成 model
 *
 * @author Lucien
 * @version 1.0.5
 */
@Slf4j
@Component
@Scope("prototype")
@Qualifier("normalTrie")
public class NormalTrie implements Trie {

    /**
     * 序列化 UID，新增字段请在最后面增加
     */
    private static final long serialVersionUID = -2869176954782360946L;
    /**
     * 字典树根节点
     */
    private Node root;

    /**
     * 新建时初始化根节点
     */
    public NormalTrie() {
        this.root = new Node(0);
    }

    @Override
    public void add(String word, int index) {
        Node buffer = this.root;
        for (Character character : word.toCharArray()) {
            buffer = buffer.add(character, -1);
        }
        buffer.setEnd(true);
    }

    @Override
    public List<String> getByPrefix(String prefix) {
        Node node = matchNode(prefix);
        if (null != node) {
            return getByPrefix(prefix, node);
        }
        return new ArrayList<>();
    }

    /**
     * 前缀搜索子函数
     * 得到以当前节点为起点的所有 prefix + suffix
     *
     * @param node Node
     * @return String list
     */
    private List<String> getByPrefix(String prefix, Node node) {
        List<String> result = new ArrayList<>();
        if (node.isEmpty()) {
            result.add(prefix);
            return result;
        }

        if (node.isEnd()) {
            result.add(prefix);
        }

        for (Character character : node.getNext().keySet()) {
            result.addAll(getByPrefix(prefix + character, node.get(character)));
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

    @Override
    public boolean exist(String word) {
        Node current = this.root, buffer;
        for (int i = 0; i < word.length(); i++) {
            buffer = current.get(word.charAt(i));
            if (null == buffer) {
                return false;
            }
            current = buffer;
        }
        return current.isEnd() && current.getDepth() == word.length();
    }

    @Override
    public boolean save(String path) {
        return save(path, (objectOutputStream) -> {
            objectOutputStream.writeObject(this.root);
        });
    }

    @Override
    public boolean load(String path) {
        return load(path, (objectInputStream) -> {
            this.root = (Node) objectInputStream.readObject();
        });
    }

    /**
     * 字典树的节点
     *
     * @author Lucien
     * @version 1.0.2
     */
    @Getter
    public static class Node extends AbstractTrie.AbstractNode {

        /**
         * 序列化 UID，新增字段请在最后面增加
         */
        private static final long serialVersionUID = -1745293409138075988L;

        /**
         * 转移状态
         */
        private Map<Character, Node> next;

        Node(int depth) {
            super(depth);
            this.next = new TreeMap<>();
        }

        @Override
        public Node add(Character character, int index) {
            Node buffer = this.get(character);
            if (null != buffer) {
                return buffer;
            }

            buffer = new Node(this.getDepth() + 1);
            this.getNext().put(character, buffer);

            return buffer;
        }

        @Override
        public Node get(Character character) {
            if (this.getNext().containsKey(character)) {
                return this.getNext().get(character);
            }

            return null;
        }

        @Override
        public int size() {
            return this.getNext().size();
        }

        @Override
        public boolean isEmpty() {
            return this.getNext().isEmpty();
        }
    }
}
