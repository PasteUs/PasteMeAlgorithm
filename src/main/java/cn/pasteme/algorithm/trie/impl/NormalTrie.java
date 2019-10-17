package cn.pasteme.algorithm.trie.impl;

import cn.pasteme.algorithm.trie.Trie;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 简化版字典树，支持离线 build 成 model
 *
 * @author Lucien
 * @version 1.0.3
 */
@Data
@Slf4j
public class NormalTrie implements Trie {

    /**
     * 字典树的节点
     *
     * @author Lucien
     * @version 1.0.1
     */
    @Data
    static class Node implements Serializable {

        /**
         * 序列化 UID，新增字段请在最后面增加
         */
        private static final long serialVersionUID = -1745293409138075988L;

        /**
         * 节点的深度就是当前匹配串的长度
         */
        private int depth;

        /**
         * 当前节点是否为某个单词的结束
         */
        private boolean end;

        /**
         * 记录转移状态
         */
        private Map<Character, Node> next;

        /**
         * 新建一个深度为 depth 的节点
         *
         * @param depth 深度
         */
        Node(int depth) {
            this.depth = depth;
            this.next = new TreeMap<>();
        }

        /**
         * 当前节点沿着某字符会到达的节点
         *
         * @param character 字符
         * @return Node
         */
        Node get(Character character) {
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
        Node put(Character character) {
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
        public int size() {
            return this.next.size();
        }

        /**
         * 当前节点是否没有子节点
         *
         * @return boolean
         */
        boolean isEmpty() {
            return this.next.isEmpty();
        }
    }


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
    public void add(String word) {
        Node buffer = this.root;
        for (Character character : word.toCharArray()) {
            buffer = buffer.put(character);
        }
        buffer.setEnd(true);
    }

    @Override
    public void addAll(List<String> dictionary) {
        for (String each : dictionary) {
            add(each);
        }
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
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.root);
            objectOutputStream.close();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean load(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.root = (Node) objectInputStream.readObject();
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
