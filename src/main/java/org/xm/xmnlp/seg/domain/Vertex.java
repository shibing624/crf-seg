package org.xm.xmnlp.seg.domain;


/**
 * 顶点
 * Created by xuming on 2016/7/23.
 */
public class Vertex {
    /**
     * 节点对应的词或等效词（如未##数）
     */
    public String word;
    /**
     * 节点对应的真实词，绝对不含##
     */
    public String realWord;
    /**
     * 等效词ID,也是Attribute的下标
     */
    public int wordID;
    /**
     * 在一维顶点数组中的下标，可以视作这个顶点的id
     */
    public int index;
    /**
     * 到该节点的最短路径的前驱节点
     */
    public Vertex from;
    /**
     * 最短路径对应的权重
     */
    public double weight;

    /**
     * 获取真实词
     *
     * @return
     */
    public String getRealWord() {
        return realWord;
    }


    public Vertex setWord(String word) {
        this.word = word;
        return this;
    }

    public Vertex setRealWord(String realWord) {
        this.realWord = realWord;
        return this;
    }

    @Override
    public String toString() {
        return realWord;
    }
}
