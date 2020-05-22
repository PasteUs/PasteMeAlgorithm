package cn.pasteme.algorithm.nlp.impl;

import cn.pasteme.algorithm.dictionary.Dictionary;
import cn.pasteme.algorithm.nlp.NaturalLanguageProcessing;
import cn.pasteme.algorithm.pair.Pair;
import com.hankcs.hanlp.HanLP;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author Lucien
 * @version 1.2.0
 */
@Component
@Qualifier("normalNlp")
public class NormalNaturalLanguageProcessing implements NaturalLanguageProcessing {

    private final Dictionary stopWords;

    public NormalNaturalLanguageProcessing(Dictionary stopWords) {
        this.stopWords = stopWords;
    }

    @Override
    public void addStopWords(List<String> stopWords) {
        this.stopWords.addAll(stopWords);
    }

    @Override
    public List<String> tokenize(@NotNull String content) {
        return HanLP.segment(content).stream()
                .map(item -> item.word).filter(item -> !item.isBlank() && !stopWords.contains(item.trim())).collect(Collectors.toList());
    }

    @Override
    public List<Pair<String, Long>> countToken(@NotNull String content) {
        List<String> tokenList = tokenize(content);
        Map<String, Long> count = new TreeMap<>();

        for (String token : tokenList) {
            count.put(token, count.getOrDefault(token, 0L) + 1L);
        }

        return count.entrySet().stream().map(each -> new Pair<>(each.getKey(), each.getValue())).sorted((left, right) -> Long.valueOf(right.getValue() - left.getValue()).intValue()).collect(Collectors.toList());
    }
}
