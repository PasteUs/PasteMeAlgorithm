package cn.pasteme.algorithm.nlp;

import cn.pasteme.algorithm.pair.Pair;

import java.util.List;

/**
 * @author Lucien
 * @version 1.2.0
 */
public interface NLP {

    /**
     * 分词
     *
     * @param content 文本
     * @return 分词列表
     */
    List<String> tokenize(String content);

    /**
     * 分词计数，出现次数越多的词下标越小
     *
     * @param content 文本
     * @return Pair<分词, 次数>
     */
    List<Pair<String, Long>> countToken(String content);

    /**
     * 添加停用词
     *
     * @param stopWords 停用词表
     */
    void addStopWords(List<String> stopWords);
}
