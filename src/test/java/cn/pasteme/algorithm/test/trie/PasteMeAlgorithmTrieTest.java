package cn.pasteme.algorithm.test.trie;

import cn.pasteme.algorithm.trie.Trie;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Lucien
 * @version 1.0.0
 */
public class PasteMeAlgorithmTrieTest {

    private Trie trie = new Trie();

    private static List<String> dictionary;

    private static String filePath = "trie.txt";

    @BeforeClass
    public static void beforeClass() {
        dictionary = new ArrayList<>();
        dictionary.add("你好");
        dictionary.add("世界！");
        dictionary.add("你好，世界！");
        dictionary.add("Hello");
        dictionary.add("World!");
        dictionary.add("Hello World!");
    }

    @Test
    public void unitTest() {
        for (String each : dictionary) {
            trie.insert(each);
        }

        Assert.assertTrue(testTrie(trie));

        Assert.assertTrue(trie.save(filePath));
        Assert.assertTrue(testTrie(Trie.load(filePath)));
    }

    private boolean testTrie(Trie trie) {
        for (String each : dictionary) {
            Assert.assertTrue(trie.exist(each));
        }

        Assert.assertFalse(trie.exist("Test"));

        Assert.assertEquals(
                new TreeSet<>(dictionary.stream().filter(each -> each.startsWith("你")).collect(Collectors.toList())),
                new TreeSet<>(trie.getByPrefix("你"))
        );

        Assert.assertEquals(
                new TreeSet<>(dictionary.stream().filter(each -> each.startsWith("H")).collect(Collectors.toList())),
                new TreeSet<>(trie.getByPrefix("H"))
        );

        List<String> result = trie.getByPrefix("");

        Assert.assertEquals(dictionary.size(), result.size());
        Assert.assertEquals(new TreeSet<>(dictionary), new TreeSet<>(result));

        return true;
    }
}
