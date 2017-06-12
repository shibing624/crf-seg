package org.xm.xmnlp.corpus.document.word;

/**
 * 一个很方便的工厂类，能够自动生成不同类型的词语
 */
public class WordFactory {
    /**
     * 根据参数字符串产生对应的词语
     *
     * @param param
     * @return
     */
    public static IWord create(String param) {
        if (param == null) return null;
        if (param.startsWith("[") && !param.startsWith("[/")) {
            return CompoundWord.create(param);
        } else {
            return Word.create(param);
        }
    }
}
