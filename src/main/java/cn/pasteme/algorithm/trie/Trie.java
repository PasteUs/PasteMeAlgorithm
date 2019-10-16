package cn.pasteme.algorithm.trie;

import cn.pasteme.algorithm.trie.node.Node;

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

/**
 * 简化版字典树，支持离线 build 成 model
 *
 * @author Lucien
 * @version 1.0.1
 */
@Data
public class Trie implements Serializable {

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
        buffer.setEnd(true);
    }

    /**
     * 前缀搜索，得到与当前 prefix match 的所有 string
     *
     * @param prefix 前缀
     * @return String list
     */
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
        return current.isEnd() && current.getDepth() == word.length();
    }

    /**
     * 将当前的状态持久化至存储
     *
     * @param path 文件路径
     */
    public boolean save(String path) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从存储加载对象
     *
     * @param path 文件路径
     * @return Trie
     */
    public static Trie load(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Trie) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Trie class not found");
            e.printStackTrace();
        }
        return null;
    }
}
