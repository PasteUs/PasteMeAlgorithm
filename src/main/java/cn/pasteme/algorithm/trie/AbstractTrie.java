package cn.pasteme.algorithm.trie;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Lucien
 * @version 1.0.0
 */
public abstract class AbstractTrie implements Trie {

    private static final long serialVersionUID = -1248675089180379915L;

    @Getter
    public static abstract class AbstractNode implements Trie.Node {

        private static final long serialVersionUID = 8159169371096749942L;

        /**
         * 节点的深度就是当前匹配串的长度
         */
        private int depth;

        /**
         * 当前节点是否为某个单词的结束
         */
        @Setter
        private boolean end;

        /**
         * 新建一个深度为 depth 的节点
         *
         * @param depth 深度
         */
        public AbstractNode(int depth) {
            this.depth = depth;
            this.end = false;
        }
    }
}
