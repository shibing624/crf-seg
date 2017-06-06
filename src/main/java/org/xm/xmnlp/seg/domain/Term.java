package org.xm.xmnlp.seg.domain;


import org.xm.xmnlp.Xmnlp;

/**
 * Created by xuming on 2016/7/22.
 */
public class Term {
    /**
     * 词语
     */
    public String word;

    /**
     * 词性
     */
    public Nature nature;

    /**
     * 在文本中的起始位置（需开启分词器的offset选项）
     */
    public int offset;

    /**
     * 长度
     */
    public int length() {
        return word.length();
    }

    /**
     * 词性
     */
    public Nature getNature() {
        return nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
    }

    /**
     * 构造一个单词
     *
     * @param word   词语
     * @param nature 词性
     */
    public Term(String word, Nature nature) {
        this.word = word;
        this.setNature(nature);
    }

    @Override
    public String toString() {
        if (Xmnlp.Config.ShowTermNature)
            return word + "/" + getNature();
        return word;
    }

    public String toString(String split) {
        if (Xmnlp.Config.ShowTermNature)
            return word + split + getNature();
        return word;
    }

}
