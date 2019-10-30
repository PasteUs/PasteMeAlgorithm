package cn.pasteme.algorithm.test.nlp;

import cn.pasteme.algorithm.nlp.NLP;
import cn.pasteme.algorithm.nlp.impl.NLPImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Lucien
 * @version 1.0.0
 */
public class PasteMeAlgorithmNLPTest {

    private NLP nlp = new NLPImpl();

    @Test
    public void NLPTokenizerTest() {
        Assert.assertEquals(Arrays.asList("NLP", "中文", "分词", "测试", "English", "tokenizer", "test"), nlp.tokenize("NLP 中文分词测试，English tokenizer test"));
    }
}
