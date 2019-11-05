package cn.pasteme.algorithm.ac.impl;

import cn.pasteme.algorithm.ac.AhoCorasick;
import cn.pasteme.algorithm.pair.Pair;
import cn.pasteme.algorithm.trie.AbstractTrie.AbstractNode;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 朴素 AhoCorasick
 *
 * @author Lucien
 * @version 1.0.2
 */
@Slf4j
@Component
@Scope("prototype")
@Qualifier("normalAhoCorasick")
public class NormalAhoCorasick implements AhoCorasick {

    /**
     * 序列化 UID，新增字段请在最后面增加
     */
    private static final long serialVersionUID = -1349524541271539944L;

    /**
     * 整棵树的节点个数
     */
    private int size;
    /**
     * 字典树根节点
     */
    private Node root;
    /**
     * 字典
     */
    private String[] dictionary;

    public NormalAhoCorasick() {
        this.root = new Node(0, size++);
    }

    /**
     * 将词汇插入字典树
     *
     * @param word  词
     * @param index 词在字典中的下标
     */
    private void add(String word, int index) {
        Node buffer = this.root;
        for (Character character : word.toCharArray()) {
            buffer = buffer.add(character, size++);
        }
        buffer.setWordIndex(index);
    }

    /**
     * 将字典插入字典树
     *
     * @param dictionary 字典
     */
    private void addAll(List<String> dictionary) {
        for (int i = 0; i < dictionary.size(); i++) {
            add(dictionary.get(i), i);
        }
    }

    /**
     * 建立 fail 树
     */
    private void buildFailPointer() {
        Queue<Node> queue = new ArrayDeque<>();

        for (Node node : this.root.getNext().values()) {
            queue.add(node);
            node.setFail(root);
        }

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (Map.Entry<Character, Node> pair : cur.getNext().entrySet()) {
                Node next = pair.getValue(), fail = cur.getFail();
                queue.add(next);
                while (null != fail) {
                    if (null != fail.get(pair.getKey())) {
                        next.setFail(fail.get(pair.getKey()));
                        break;
                    }
                    fail = fail.getFail();
                }
                if (null == fail) {
                    next.setFail(root);
                }
            }
        }
    }

    @Override
    public boolean build(List<String> dictionary) {
        this.dictionary = new String[dictionary.size()];
        dictionary.toArray(this.dictionary);
        this.addAll(dictionary);
        this.buildFailPointer();
        return true;
    }

    /**
     * match 基函数
     *
     * @param text       文本
     * @param matchWords 当到达某个节点的时候进行的动作
     */
    private void baseMatch(String text, MatchWords matchWords) {
        Node cur = this.root;
        int index = 0;
        while (index < text.length()) {
            if (cur.getNext().containsKey(text.charAt(index))) {
                Node buffer = cur.get(text.charAt(index));
                matchWords.match(buffer);
                cur = cur.get(text.charAt(index++));
            } else {
                if (cur != root) {
                    cur = cur.fail;
                } else {
                    index++;
                }
            }
        }
    }

    @Override
    public List<String> match(String text) {
        List<String> result = new ArrayList<>();
        boolean[] vis = new boolean[this.size];
        baseMatch(text, (buffer) -> {
            while (buffer != root && !vis[buffer.getNodeIndex()]) {
                if (buffer.isEnd()) {
                    result.add(this.dictionary[buffer.getWordIndex()]);
                }
                vis[buffer.getNodeIndex()] = true;
                buffer = buffer.getFail();
            }
        });
        return result;
    }

    @Override
    public List<Pair<String, Long>> countMatch(String text) {
        Map<String, Long> count = new TreeMap<>();
        baseMatch(text, (buffer) -> {
            while (buffer != root) {
                if (buffer.isEnd()) {
                    String word = this.dictionary[buffer.getWordIndex()];
                    count.put(word, 1L + count.getOrDefault(word, 0L));
                }
                buffer = buffer.getFail();
            }
        });

        return count.entrySet().stream().map(each -> new Pair<>(each.getKey(), each.getValue())).collect(Collectors.toList());
    }

    @Override
    public List<Pair<String, Long>> locationMatch(String text) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean save(String filePath) {
        return save(filePath, (objectOutputStream) -> {
            objectOutputStream.writeObject(this.root);
            objectOutputStream.writeObject(this.dictionary);
        });
    }

    @Override
    public boolean load(String filePath) {
        return load(filePath, (objectInputStream) -> {
            this.root = (Node) objectInputStream.readObject();
            this.dictionary = (String[]) objectInputStream.readObject();
        });
    }

    interface MatchWords {
        void match(Node buffer);
    }

    /**
     * @author Lucien
     * @version 1.0.0
     */
    @Getter
    static class Node extends AbstractNode {

        /**
         * 序列化 UID，新增字段请在最后面增加
         */
        private static final long serialVersionUID = 1821646667038760238L;

        /**
         * 转移状态
         */
        private Map<Character, Node> next;

        /**
         * 节点下标
         */
        @Setter
        private int nodeIndex;

        /**
         * 词在词典中的下标
         */
        @Setter
        private int wordIndex;

        /**
         * Fail 指针
         */
        @Setter
        @Getter
        private Node fail;

        Node(int depth, int nodeIndex) {
            super(depth);
            this.next = new TreeMap<>();
            this.fail = null;
            this.nodeIndex = nodeIndex;
            this.wordIndex = -1;
        }

        @Override
        public boolean isEnd() {
            return this.wordIndex != -1;
        }

        @Override
        public Node add(Character character, int index) {
            Node buffer = this.get(character);
            if (null != buffer) {
                return buffer;
            }

            buffer = new Node(this.getDepth() + 1, index);
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
