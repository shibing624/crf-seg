package org.xm.xmnlp.corpus.document;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xm.xmnlp.corpus.document.word.IWord;
import org.xm.xmnlp.corpus.document.word.WordFactory;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 句子，指的是以。，：！结尾的句子
 */
public class Sentence implements Serializable {
    private static Logger logger = LogManager.getLogger();
    public List<IWord> wordList;

    public Sentence(List<IWord> wordList) {
        this.wordList = wordList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (IWord word : wordList) {
            sb.append(word);
            if (i != wordList.size()) sb.append(' ');
            ++i;
        }
        return sb.toString();
    }

    public static Sentence create(String param) {
        Pattern pattern = Pattern.compile("(\\[(([^\\s]+/[0-9a-zA-Z]+)\\s+)+?([^\\s]+/[0-9a-zA-Z]+)]/[0-9a-zA-Z]+)|([^\\s]+/[0-9a-zA-Z]+)");
        Matcher matcher = pattern.matcher(param);
        List<IWord> wordList = new LinkedList<IWord>();
        while (matcher.find()) {
            String single = matcher.group();
            IWord word = WordFactory.create(single);
            if (word == null) {
                logger.warn("在用" + single + "构造单词时失败");
                return null;
            }
            wordList.add(word);
        }

        return new Sentence(wordList);
    }
}
