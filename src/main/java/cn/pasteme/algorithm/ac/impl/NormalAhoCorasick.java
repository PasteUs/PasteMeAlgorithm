package cn.pasteme.algorithm.ac.impl;

import cn.pasteme.algorithm.ac.AhoCorasick;
import cn.pasteme.algorithm.trie.AbstractTrie;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * @author Lucien
 * @version 1.0.0
 */
@Slf4j
public class NormalAhoCorasick implements AhoCorasick {

    /**
     * 序列化 UID，新增字段请在最后面增加
     */
    private static final long serialVersionUID = -1349524541271539944L;

    @Getter
    static class Node extends AbstractTrie.AbstractNode {

        /**
         * 序列化 UID，新增字段请在最后面增加
         */
        private static final long serialVersionUID = 1821646667038760238L;

        /**
         * 转移状态
         */
        private Map<Character, Node> next;

        /**
         * Fail 指针
         */
        @Setter
        private Node fail;

        Node(int depth) {
            super(depth);
            this.next = new TreeMap<>();
            this.fail = null;
        }

        @Override
        public Node add(Character character) {
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

    /**
     * 字典树根节点
     */
    private Node root;

    /**
     * 将词汇插入字典树
     *
     * @param word 词
     */
    private void add(String word) {
        Node buffer = this.root;
        for (Character character : word.toCharArray()) {
            buffer = buffer.add(character);
        }
        buffer.setEnd(true);
    }

    /**
     * 将字典插入字典树
     *
     * @param dictionary 字典
     */
    private void addAll(List<String> dictionary) {
        for (String each : dictionary) {
            add(each);
        }
    }

    @Override
    public boolean build(List<String> dictionary) {
        return true;
    }

    @Override
    public List<String> match(String text) {
        return null;
    }

    @Override
    public boolean save(String filePath) {
        return false;
    }

    @Override
    public boolean load(String filePath) {
        return false;
    }
}
