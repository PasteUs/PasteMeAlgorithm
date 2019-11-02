package cn.pasteme.algorithm.ac.impl;

import cn.pasteme.algorithm.trie.AbstractTrie;
import cn.pasteme.algorithm.ac.AhoCorasick;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * @author Lucien
 * @version 1.0.1
 */
@Slf4j
@Component
@Qualifier("normalAhoCorasick")
public class NormalAhoCorasick implements AhoCorasick {

    /**
     * 序列化 UID，新增字段请在最后面增加
     */
    private static final long serialVersionUID = -1349524541271539944L;

    /**
     * @author Lucien
     * @version 1.0.0
     */
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
         * 对应词在字典中的下标
         */
        @Setter
        private int index;

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
     * 字典
     */
    private String[] dictionary;

    public NormalAhoCorasick() {
        this.root = new Node(0);
    }

    /**
     * 将词汇插入字典树
     *
     * @param word 词
     */
    private void add(String word, int index) {
        Node buffer = this.root;
        for (Character character : word.toCharArray()) {
            buffer = buffer.add(character);
        }
        buffer.setEnd(true);
        buffer.setIndex(index);
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

    @Override
    public List<String> match(String text) {
        List<String> result = new ArrayList<>();
        Node cur = this.root;
        int index = 0;
        while (index < text.length()) {
            if (cur.getNext().containsKey(text.charAt(index))) {
                Node buffer = cur.get(text.charAt(index));
                while (buffer != root) {
                    if (buffer.isEnd()) {
                        result.add(this.dictionary[buffer.index]);
                    }
                    buffer = buffer.getFail();
                }
                cur = cur.get(text.charAt(index++));
            } else {
                if (cur != root) {
                    cur = cur.fail;
                } else {
                    index++;
                }
            }
        }
        return result;
    }

    @Override
    public boolean save(String filePath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.root);
            objectOutputStream.writeObject(this.dictionary);
            objectOutputStream.close();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean load(String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.root = (Node) objectInputStream.readObject();
            this.dictionary = (String[]) objectInputStream.readObject();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            log.error("Trie class not found");
            e.printStackTrace();
        }
        return false;
    }
}
