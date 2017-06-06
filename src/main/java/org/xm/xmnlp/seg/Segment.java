package org.xm.xmnlp.seg;


import org.xm.xmnlp.Xmnlp;
import org.xm.xmnlp.dictionary.other.CharTable;
import org.xm.xmnlp.seg.domain.Term;
import org.xm.xmnlp.util.SentencesUtil;
import org.xm.xmnlp.util.TextUtil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.xm.xmnlp.util.Static.logger;

/**
 * 分词器（分词服务）<br>
 * 是所有分词器的基类（Abstract）<br>
 * 分词器的分词方法是线程安全的，但配置方法则不保证
 * <p/>
 * Created by xuming on 2016/7/22.
 */
public abstract class Segment {
    /**
     * 分词器配置
     */
    protected TokenizerConfig tokenizerConfig;

    /**
     * 构造一个分词器
     */
    public Segment() {
        tokenizerConfig = new TokenizerConfig();
    }

    /**
     * 给一个句子分词
     *
     * @param sentence 待分词句子
     * @return 单词列表
     */
    protected abstract List<Term> segSentence(char[] sentence);

    /**
     * 分词
     *
     * @param text 待分词文本
     * @return 单词列表
     */
    public List<Term> seg(char[] text) {
        assert text != null;
        if (Xmnlp.Config.Normalization) {
            CharTable.normalization(text);
        }
        return segSentence(text);
    }

    /**
     * 分词<br>
     * 此方法是线程安全的
     *
     * @param text 待分词文本
     * @return 单词列表
     */
    public List<Term> seg(String text) {
        char[] charArray = text.toCharArray();
        if (Xmnlp.Config.Normalization) {
            CharTable.normalization(charArray);
        }
        if (tokenizerConfig.threadNumber > 1 && charArray.length > 10000) { // 小文本多线程没意义，反而变慢了
            List<String> sentenceList = SentencesUtil.toSentenceList(charArray);
            String[] sentenceArray = new String[sentenceList.size()];
            sentenceList.toArray(sentenceArray);
            //noinspection unchecked
            List<Term>[] termListArray = new List[sentenceArray.length];
            final int per = sentenceArray.length / tokenizerConfig.threadNumber;
            WorkThread[] threadArray = new WorkThread[tokenizerConfig.threadNumber];
            for (int i = 0; i < tokenizerConfig.threadNumber - 1; ++i) {
                int from = i * per;
                threadArray[i] = new WorkThread(sentenceArray, termListArray, from, from + per);
                threadArray[i].start();
            }
            threadArray[tokenizerConfig.threadNumber - 1] = new WorkThread(sentenceArray, termListArray, (tokenizerConfig.threadNumber - 1) * per, sentenceArray.length);
            threadArray[tokenizerConfig.threadNumber - 1].start();
            try {
                for (WorkThread thread : threadArray) {
                    thread.join();  // 主线程需要用到子线程的结果，主线程等子线程都执行完毕
                }
            } catch (InterruptedException e) {
                logger.error("线程同步异常：" + TextUtil.exceptionToString(e));
                return Collections.emptyList();
            }
            List<Term> termList = new LinkedList<Term>();
            if (tokenizerConfig.offset || tokenizerConfig.indexMode) { // 由于分割了句子，所以需要重新校正offset
                int sentenceOffset = 0;
                for (int i = 0; i < sentenceArray.length; ++i) {
                    for (Term term : termListArray[i]) {
                        term.offset += sentenceOffset;
                        termList.add(term);
                    }
                    sentenceOffset += sentenceArray[i].length();
                }
            } else {
                for (List<Term> list : termListArray) {
                    termList.addAll(list);
                }
            }

            return termList;
        }
        return segSentence(charArray);
    }

    private class WorkThread extends Thread {
        String[] sentenceArray;
        List<Term>[] termListArray;
        int from;
        int to;

        public WorkThread(String[] sentenceArray, List<Term>[] termListArray, int from, int to) {
            this.sentenceArray = sentenceArray;
            this.termListArray = termListArray;
            this.from = from;
            this.to = to;
        }

        @Override
        public void run() {
            for (int i = from; i < to; ++i) {
                termListArray[i] = segSentence(sentenceArray[i].toCharArray());
            }
        }
    }

    /**
     * 开启多线程
     *
     * @param enable true表示开启4个线程，false表示单线程
     * @return
     */
    public Segment enableMultithreading(boolean enable) {
        if (enable) tokenizerConfig.threadNumber = 4;
        else tokenizerConfig.threadNumber = 1;
        return this;
    }

    /**
     * 开启多线程
     *
     * @param threadNumber 线程数量
     * @return
     */
    public Segment enableMultithreading(int threadNumber) {
        tokenizerConfig.threadNumber = threadNumber;
        return this;
    }

    /**
     * 是否启用用户词典
     *
     * @param enable
     */
    public Segment enableCustomDictionary(boolean enable) {
        tokenizerConfig.useCustomDictionary = enable;
        return this;
    }

    /**
     * 开启词性标注
     *
     * @param enable
     * @return
     */
    public Segment enablePartOfSpeechTagging(boolean enable) {
        tokenizerConfig.speechTagging = enable;
        return this;
    }

}
