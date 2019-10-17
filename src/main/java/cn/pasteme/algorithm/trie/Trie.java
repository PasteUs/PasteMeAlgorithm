package cn.pasteme.algorithm.trie;

import java.io.Serializable;
import java.util.List;

/**
 * @author Lucien
 * @version 1.0.0
 */
public interface Trie extends Serializable {

    /**
     * 向字典中添加词汇
     *
     * @param word 词汇
     */
    void add(String word);

    /**
     * 向字典中添加词汇
     *
     * @param dictionary 词汇
     */
    void addAll(List<String> dictionary);

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
