package cn.pasteme.algorithm.dictionary;

import cn.pasteme.algorithm.trie.Trie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Lucien
 * @version 1.0.0
 */
@Component
@Scope("prototype")
public class Dictionary {

    private final Trie trie;

    public Dictionary(Trie trie) {
        this.trie = trie;
    }

    public void addAll(@NotNull List<String> dictionary) {
        this.trie.addAll(dictionary);
    }

    public boolean contains(@NotNull String word) {
        return trie.exist(word);
    }
}
