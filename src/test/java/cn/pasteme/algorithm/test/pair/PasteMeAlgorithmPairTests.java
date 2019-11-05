package cn.pasteme.algorithm.test.pair;

import cn.pasteme.algorithm.pair.Pair;

import static org.junit.Assert.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author Lucien
 * @version 1.0.0
 */
@Slf4j
public class PasteMeAlgorithmPairTests {

    @Test
    public void test() {
        Pair<String, Long> pair = new Pair<>("Hello World!", 1008611L);
        Pair<String, Long> anotherPair = new Pair<>("Hello World!", 1008611L);
        assertEquals(pair, anotherPair);
        log.info("{}", pair);
    }
}
