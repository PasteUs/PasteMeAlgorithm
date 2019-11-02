package cn.pasteme.algorithm.test.ac.common;

import cn.pasteme.algorithm.ac.AhoCorasick;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;

/**
 * @author Lucien
 * @version 1.0.0
 */
@Slf4j
public class Common {

    /**
     * AC 自动机
     */
    private AhoCorasick ac;

    /**
     * 字典
     */
    private List<String> dictionary = Arrays.asList("你好", "世界！", "你好，世界！", "Hello", "World!", "Hello World!", "Do not exist", "不存在");

    /**
     * 匹配的文本
     */
    private String text = "你好，世界！Hello World!\n多行文本测试\nMultiline test\n你好！";

    private boolean equals(List<String> left, List<String> right) {
        return new TreeSet<>(left).equals(new TreeSet<>(right));
    }

    public Common(AhoCorasick ahoCorasick) {
        this.ac = ahoCorasick;
    }

    public Common(AhoCorasick ahoCorasick, List<String> dictionary, String text) {
        this.ac = ahoCorasick;
        this.dictionary = dictionary;
        this.text = text;
    }

    public void run() {
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
        log.warn("match result = {}", result);
        assertTrue(equals(Arrays.asList("你好", "世界！", "你好，世界！", "Hello", "World!", "Hello World!"), result));
    }
}
