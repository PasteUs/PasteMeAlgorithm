package cn.pasteme.algorithm.trie;

import java.io.Serializable;
import java.util.List;

/**
 * @author Lucien
 * @version 1.0.2
 */
public interface Trie extends Serializable {

    /**
     * @author Lucien
     * @version 1.0.0
     */
    interface Node extends Serializable {

        /**
         * 为当前节点添加一个由某字符转移的子节点
         *
         * @param character 字符
         * @return Node
         */
        Node add(Character character);

        /**
         * 当前节点沿着某字符会到达的节点
         *
         * @param character 字符
         * @return Node
         */
        Node get(Character character);

        /**
         * 返回当前节点有多少子节点
         *
         * @return 子节点数量
         */
        int size();

        /**
         * 当前节点是否没有子节点
         *
         * @return boolean
         */
        boolean isEmpty();
    }

    /**
     * 向字典中添加词汇
     *
     * @param word 词汇
     */
    void add(String word);

    /**
     * 向字典中添加词典
     *
     * @param dictionary 词汇
     */
    default void addAll(List<String> dictionary) {
        for (String each : dictionary) {
            add(each);
        }
    }

    /**
     * 前缀搜索，得到与当前 prefix match 的所有 string
     *
     * @param prefix 前缀
     * @return String list
     */
    List<String> getByPrefix(String prefix);

    /**
     * 判断词汇是否在字典树中
     *
     * @param word 词汇
     * @return boolean
     */
    boolean exist(String word);

    /**
     * 将当前的状态持久化至存储
     *
     * @param path 文件路径
     * @return 持久化是否成功
     */
    boolean save(String path);

    /**
     * 从存储加载对象
     *
     * @param path 文件路径
     * @return 加载是否成功
     */
    boolean load(String path);
}
