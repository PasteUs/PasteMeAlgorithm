package cn.pasteme.algorithm.test.ac;

import cn.pasteme.algorithm.ac.impl.NormalAhoCorasick;
import cn.pasteme.algorithm.test.ac.common.Common;

import org.junit.Test;

/**
 * @author Lucien
 * @version 1.0.0
 */
public class PasteMeAlgorithmAhoCorasickTest {

    @Test
    public void normalAhoCorasickTest() {
        new Common(new NormalAhoCorasick()).run();
    }
}
