package cn.pasteme.algorithm.nlp.impl;

import cn.pasteme.algorithm.nlp.NLP;
import cn.pasteme.algorithm.pair.Pair;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author Lucien
 * @version 1.1.0
 */
@Slf4j
@Component
public class NormalNLP implements NLP {

    private List<String> stoppedWords = new ArrayList<>();

    public NormalNLP() {
        try {
            File file = new File("dictionary/stopwords.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String buffer;
            while (null != (buffer = bufferedReader.readLine())) {
                stoppedWords.add(buffer);
            }
        } catch (FileNotFoundException e) {
            log.error("File \"dictionary/stopwords.txt\" is not exist");
            stoppedWords = Arrays.asList("的", "，", "。");
        } catch (IOException e) {
            log.error("Read file failed, error = ", e);
            stoppedWords = Arrays.asList("的", "，", "。");
        }
    }

    @Override
    public List<String> tokenize(String content) {
        return HanLP.segment(content).stream()
                .map(item -> item.word).filter(item -> !item.isBlank() && !stoppedWords.contains(item.trim())).collect(Collectors.toList());
    }

    @Override
    public List<Pair<String, Long>> countToken(String content) {
        List<String> tokenList = tokenize(content);
        Map<String, Long> count = new TreeMap<>();

        for (String token : tokenList) {
            count.put(token, count.getOrDefault(token, 0L) + 1L);
        }

        return count.entrySet().stream().map(each -> new Pair<>(each.getKey(), each.getValue())).sorted((left, right) -> Long.valueOf(right.getValue() - left.getValue()).intValue()).collect(Collectors.toList());
    }
}
