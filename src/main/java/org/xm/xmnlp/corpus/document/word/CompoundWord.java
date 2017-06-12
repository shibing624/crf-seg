package org.xm.xmnlp.corpus.document.word;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;


/**
 * 复合词，由两个或以上的word构成
 */
public class CompoundWord implements IWord {
    private static Logger logger = LogManager.getLogger();
    /**
     * 由这些词复合而来
     */
    public List<Word> innerList;

    public String label;

    @Override
    public String getValue() {
        StringBuilder sb = new StringBuilder();
        for (Word word : innerList) {
            sb.append(word.value);
        }
        return sb.toString();
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void setValue(String value) {
        innerList.clear();
        innerList.add(new Word(value, label));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int i = 1;
        for (Word word : innerList) {
            sb.append(word.toString());
            if (i != innerList.size()) {
                sb.append(' ');
            }
            ++i;
        }
        sb.append("]/");
        sb.append(label);
        return sb.toString();
    }

    /**
     * 转换为一个简单词
     *
     * @return
     */
    public Word toWord() {
        return new Word(getValue(), getLabel());
    }

    public CompoundWord(List<Word> innerList, String label) {
        this.innerList = innerList;
        this.label = label;
    }

    public static CompoundWord create(String param) {
        if (param == null) return null;
        int cutIndex = param.lastIndexOf('/');
        if (cutIndex <= 2 || cutIndex == param.length() - 1) return null;
        String wordParam = param.substring(1, cutIndex - 1);
        List<Word> wordList = new LinkedList<Word>();
        for (String single : wordParam.split(" ")) {
            if (single.length() == 0) continue;
            Word word = Word.create(single);
            if (word == null) {
                logger.warn("使用参数" + single + "构造单词时发生错误");
                return null;
            }
            wordList.add(word);
        }
        String labelParam = param.substring(cutIndex + 1);
        return new CompoundWord(wordList, labelParam);
    }
}
