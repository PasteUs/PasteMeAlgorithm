package cn.pasteme.algorithm.ac;

import cn.pasteme.algorithm.common.Persistent;
import cn.pasteme.algorithm.pair.Pair;

import java.util.List;

/**
 * @author Lucien
 * @version 1.0.0
 */
public interface AhoCorasick extends Persistent {

    /**
     * 将词典构建为自动机
     *
     * @param dictionary 词典
     * @return 构建成功与否
     */
    boolean build(List<String> dictionary);

    /**
     * 朴素文本匹配，只返回出现过的词的集合
     *
     * @param text 文本
     * @return 命中的词
     */
    List<String> match(String text);

    /**
     * 计数文本匹配，返回出现的词以及它出现的次数
     *
     * @param text 文本
     * @return 命中的词以及它出现的次数
     */
    List<Pair<String, Long>> countMatch(String text);

    /**
     * 下标文本匹配，返回出现的词以及它在文本中的下标
     *
     * @param text 文本
     * @return 命中的词以及它出现的位置
     */
    List<Pair<String, Long>> locationMatch(String text);
}
