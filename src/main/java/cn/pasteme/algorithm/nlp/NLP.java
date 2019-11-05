package cn.pasteme.algorithm.nlp;

import java.util.List;

/**
 * @author Lucien
 * @version 1.0.0
 */
public interface NLP {

    /**
     * 分词
     *
     * @param content 文本
     * @return 分出的词
     */
    List<String> tokenize(String content);
}
