package cn.pasteme.algorithm.test.trie.common;

import cn.pasteme.algorithm.trie.Trie;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Lucien
 * @version 1.0.1
 */
@Slf4j
public class Common {

    /**
     * 字典树
     */
    private Trie trie;

    /**
     * 字典
     */
    private List<String> dictionary = Arrays.asList("你好", "世界！", "你好，世界！", "Hello", "World!", "Hello World!");

    public Common(Trie trie) {
        this.trie = trie;
    }

    public Common(Trie trie, List<String> dictionary) {
        this.trie = trie;
        this.dictionary = dictionary;
    }

    public Common(Trie trie, List<String> dictionary, String filePath) {
        this.dictionary = dictionary;
        this.trie = trie;
    }

    public void run() {
        String filePath = UUID.randomUUID().toString() + ".trie";
        log.warn("file path = {}", filePath);

        trie.addAll(dictionary);

        assertTrue(testTrie(trie));

        assertTrue(trie.save(filePath));
        assertTrue(testTrie(loadTrie(filePath)));

        if (!deleteFile(filePath)) {
            log.error("Delete file false, file path = {}", filePath);
        }
    }

    private boolean deleteFile(String filePath) {
        return new File(filePath).delete();
    }

    private Trie loadTrie(String filePath) {
        log.warn("load from {}", filePath);
        assertTrue(trie.load(filePath));
        return trie;
    }

    private boolean testTrie(Trie trie) {
        for (String each : dictionary) {
            assertTrue(trie.exist(each));
        }

        assertFalse(trie.exist("Test"));

        assertEquals(
                new TreeSet<>(dictionary.stream().filter(each -> each.startsWith("你")).collect(Collectors.toList())),
                new TreeSet<>(trie.getByPrefix("你"))
        );

        assertEquals(
                new TreeSet<>(dictionary.stream().filter(each -> each.startsWith("H")).collect(Collectors.toList())),
                new TreeSet<>(trie.getByPrefix("H"))
        );

        List<String> result = trie.getByPrefix("");

        assertEquals(dictionary.size(), result.size());
        assertEquals(new TreeSet<>(dictionary), new TreeSet<>(result));

        return true;
    }
}
