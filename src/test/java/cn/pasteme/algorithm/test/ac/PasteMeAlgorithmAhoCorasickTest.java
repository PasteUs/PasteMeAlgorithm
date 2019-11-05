package cn.pasteme.algorithm.test.ac;

import cn.pasteme.algorithm.ac.AhoCorasick;
import cn.pasteme.algorithm.pair.Pair;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Lucien
 * @version 1.1.0
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PasteMeAlgorithmAhoCorasickTest {

    /**
     * 字典
     */
    private List<String> dictionary = Arrays.asList("你好", "世界！", "你好，世界！", "Hello", "World!", "Hello World!", "Do not exist", "不存在", "HH");

    /**
     * 匹配的文本
     */
    private String text = "你好，世界！Hello World!\n多行文本测试\nMultiline test\n你好！\nHHH";

    private List<String> matchedList = Arrays.asList("你好", "世界！", "你好，世界！", "Hello", "World!", "Hello World!", "HH");

    private List<String> twiceList = Arrays.asList("你好", "HH");

    private <T> boolean equals(List<T> left, List<T> right) {
        return new TreeSet<>(left).equals(new TreeSet<>(right));
    }

    /**
     * AC 自动机
     */
    @Autowired
    private Map<String, AhoCorasick> ahoCorasickMap;

    @Test
    public void normalAhoCorasickTest() {
        for (Map.Entry<String, AhoCorasick> pair : ahoCorasickMap.entrySet()) {
            log.info("Start test {}", pair.getKey());
            run(pair.getValue());
        }
    }

    public void run(AhoCorasick ac) {
        assertTrue(ac.build(dictionary));

        test(ac);

        String filePath = UUID.randomUUID().toString() + ".ahoCorasick";

        log.warn("filePath = {}", filePath);

        assertTrue(ac.save(filePath));

        ac.load(filePath);

        if (!deleteFile(filePath)) {
            log.error("Delete file false, file path = {}", filePath);
        }
    }

    private boolean deleteFile(String filePath) {
        return new File(filePath).delete();
    }

    private void test(AhoCorasick ac) {
        List<String> result = ac.match(text);
        log.info("match result = {}", result);

        assertTrue(equals(matchedList, result));

        List<Pair<String, Long>> countResult = ac.countMatch(text);
        log.info("countResult = {}", countResult);
        assertEquals(matchedList.size(), countResult.size());
        for (Pair<String, Long> pair : countResult) {
            assertTrue(matchedList.contains(pair.getKey()));
            assertEquals(Long.valueOf(1L + (twiceList.contains(pair.getKey()) ? 1L : 0L)), pair.getValue());
        }
    }
}
