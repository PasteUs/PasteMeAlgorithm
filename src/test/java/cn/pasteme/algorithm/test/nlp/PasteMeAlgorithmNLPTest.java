package cn.pasteme.algorithm.test.nlp;

import cn.pasteme.algorithm.nlp.NLP;
import cn.pasteme.algorithm.nlp.impl.NormalNLP;
import cn.pasteme.algorithm.pair.Pair;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Lucien
 * @version 1.0.1
 */
public class PasteMeAlgorithmNLPTest {

    private NLP nlp = new NormalNLP();

    @Test
    public void NLPTokenizerTest() {
        assertEquals(Arrays.asList("NLP", "中文", "分词", "测试", "English", "tokenizer", "test"), nlp.tokenize("NLP 中文分词测试，English tokenizer test"));
    }

    @Test
    public void NLPCountTokenTest() {
        List<Pair<String, Long>> result = nlp.countToken("NLP 中文分词测试，English tokenizer test\n再次测试");
        assertEquals("测试", result.get(0).getKey());
        assertEquals(Long.valueOf(2), result.get(0).getValue());
    }
}
