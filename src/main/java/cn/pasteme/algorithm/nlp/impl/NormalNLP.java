package cn.pasteme.algorithm.nlp.impl;

import cn.pasteme.algorithm.nlp.NLP;
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
import java.util.stream.Collectors;

/**
 * @author Lucien
 * @version 1.0.0
 */
@Slf4j
@Component
public class NormalNLP implements NLP {

    private List<String> stoppedWords = new ArrayList<>();

    public NormalNLP() {
        try {
            File file = new File("dictionary/stoppedWords.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String buffer;
            while (null != (buffer = bufferedReader.readLine())) {
                stoppedWords.add(buffer);
            }
        } catch (FileNotFoundException e) {
            log.error("File \"dictionary/stoppedWords.txt\" is not exist");
            stoppedWords = Arrays.asList("", "的", "，", "。");
        } catch (IOException e) {
            log.error("Read file failed, error = ", e);
            stoppedWords = Arrays.asList("", "的", "，", "。");
        }
    }

    @Override
    public List<String> tokenize(String content) {
        return HanLP.segment(content).stream()
                .map(item -> item.word).filter(item -> !stoppedWords.contains(item.trim())).collect(Collectors.toList());
    }
}
