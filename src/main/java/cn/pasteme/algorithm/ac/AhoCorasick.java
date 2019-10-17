package cn.pasteme.algorithm.ac;

import java.io.Serializable;
import java.util.List;

/**
 * @author Lucien
 * @version 1.0.0
 */
public interface AhoCorasick extends Serializable {

    /**
     * 将词典构建为自动机
     *
     * @param dictionary 词典
     * @return 构建成功与否
     */
    boolean build(List<String> dictionary);

    /**
     * 文本匹配
     *
     * @param text 文本
     * @return 命中的词
     */
    List<String> match(String text);

    /**
     * 将对象持久化至磁盘
     *
     * @param filePath 文件路径
     * @return 持久化成功与否
     */
    boolean save(String filePath);

    /**
     * 从磁盘加载对象
     *
     * @param filePath 文件路径
     * @return 加载成功与否
     */
    boolean load(String filePath);
}
