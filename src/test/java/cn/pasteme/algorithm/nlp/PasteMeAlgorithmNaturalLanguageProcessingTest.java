package cn.pasteme.algorithm.nlp;

import cn.pasteme.algorithm.pair.Pair;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Lucien
 * @version 1.0.2
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PasteMeAlgorithmNaturalLanguageProcessingTest {

    @Autowired
    private NaturalLanguageProcessing naturalLanguageProcessing;

    @Test
    public void NLPTokenizerTest() {
        assertNotEquals(Arrays.asList("NaturalLanguageProcessing", "中文", "分词", "测试", "English", "tokenizer", "test"), naturalLanguageProcessing.tokenize("NaturalLanguageProcessing 中文分词测试，English tokenizer test"));
        naturalLanguageProcessing.addStopWords(Arrays.asList("，", "。"));
        assertEquals(Arrays.asList("NaturalLanguageProcessing", "中文", "分词", "测试", "English", "tokenizer", "test"), naturalLanguageProcessing.tokenize("NaturalLanguageProcessing 中文分词测试，English tokenizer test"));
    }

    @Test
    public void NLPCountTokenTest() {
        List<Pair<String, Long>> result = naturalLanguageProcessing.countToken("NaturalLanguageProcessing 中文分词测试，English tokenizer test\n再次测试");
        assertEquals("测试", result.get(0).getKey());
        assertEquals(Long.valueOf(2), result.get(0).getValue());
    }
}
