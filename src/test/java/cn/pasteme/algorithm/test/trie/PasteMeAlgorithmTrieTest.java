package cn.pasteme.algorithm.test.trie;

import cn.pasteme.algorithm.test.trie.common.Common;
import cn.pasteme.algorithm.trie.impl.NormalTrie;

import org.junit.Test;

/**
 * @author Lucien
 * @version 1.0.2
 */
public class PasteMeAlgorithmTrieTest {

    @Test
    public void normalTrieTest() {
        new Common(new NormalTrie()).run();
    }
}
