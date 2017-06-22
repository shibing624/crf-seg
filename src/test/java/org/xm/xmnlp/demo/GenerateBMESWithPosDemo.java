package org.xm.xmnlp.demo;

import org.xm.xmnlp.corpus.document.CorpusLoader;
import org.xm.xmnlp.corpus.document.word.Word;
import org.xm.xmnlp.util.IOUtil;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @author XuMing
 */
public class GenerateBMESWithPosDemo {
    public static final String CORPUS_FOLDER_PATH = "D:\\NLP\\data\\people2014\\2014\\0101";
    public static final String CORPUS_FILE_PATH = "D:\\NLP\\data\\people2014\\corpus-2014-segmented-with-pos.txt";
    public static final String BMES_FILE_PATH = "D:\\NLP\\data\\people2014\\corpus-2014-segmented-with-pos.bmes.txt";

    public static void main(String[] args) throws Exception {
        GenerateBMESWithPosDemo demo = new GenerateBMESWithPosDemo();
        demo.dumpCorpusFolderToFile();
        demo.generateBMES();
    }

    /**
     * 多语料文件合并功能：文件夹的分词标注数据合并为一个文件
     *
     * @throws Exception
     */
    public void dumpCorpusFolderToFile() throws Exception {
        final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(CORPUS_FILE_PATH)));
        CorpusLoader.walk(CORPUS_FOLDER_PATH, document -> {
            List<List<Word>> simpleSentenceList = document.getSimpleSentenceList();
            for (List<Word> wordList : simpleSentenceList) {
                try {
                    for (Word word : wordList) {
                        bw.write(word.toString());
                        bw.write(' ');
                    }
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        bw.close();
    }

    /**
     * 生成字标注集，便于用crf_learn训练
     *
     * @throws Exception
     */
    public void generateBMES() throws Exception {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(BMES_FILE_PATH)));
        for (String line : IOUtil.readLineListWithLessMemory(CORPUS_FILE_PATH)) {
            String[] wordArray = line.split(" ");
            for (String word : wordArray) {
                if (word.split("/").length < 2) continue;
                String name = word.split("/")[0];
                String pos = word.split("/")[1];
                int len = name.length();
                if (len == 1) {
                    bw.write(name + "\t" + pos + "\tS\n");
                } else if (len > 1) {
                    bw.write(name.charAt(0) + "\t" + pos + "\tB\n");
                    for (int i = 1; i < len - 1; ++i) {
                        bw.write(name.charAt(i) + "\t" + pos + "\tM\n");
                    }
                    bw.write(name.charAt(len - 1) + "\t" + pos + "\tE\n");
                }
            }
            bw.newLine();
        }
        bw.close();
    }
}
