package org.xm.xmnlp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xm.xmnlp.seg.CRFSegment;
import org.xm.xmnlp.seg.domain.Term;
import org.xm.xmnlp.util.Static;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

/**
 * Xmnlp:自然语言处理工具包
 *
 * @author XuMing
 */
public class Xmnlp {
    /**
     * 日志组件
     */
    private static Logger logger = LogManager.getLogger();

    public static final class Config {
        /**
         * 默认关闭调试
         */
        public static boolean DEBUG = false;
        /**
         * 字符类型对应表
         */
        public static String CharTypePath = "data/dictionary/other/CharType.dat.yes";

        /**
         * 字符正规化表（全角转半角，繁体转简体）
         */
        public static String CharTablePath = "data/dictionary/other/CharTable.bin.yes";

        /**
         * 词频统计输出路径
         */
        public static String StatisticsResultPath = "data/result/WordFrequencyStatistics-Result.txt";
        /**
         * 最大熵-依存关系模型
         */
        public static String MaxEntModelPath = "data/model/dependency/MaxEntModel.txt";
        /**
         * 神经网络依存模型路径
         */
        public static String NNParserModelPath = "data/model/dependency/NNParserModel.txt";
        /**
         * CRF分词模型
         */
        public static String CRFSegmentModelPath = "data/model/seg/CRFSegmentModel.txt";
        /**
         * HMM分词模型
         */
        public static String HMMSegmentModelPath = "data/model/seg/HMMSegmentModel.bin";
        /**
         * CRF依存模型
         */
        public static String CRFDependencyModelPath = "data/model/dependency/CRFDependencyModelMini.txt";
        /**
         * 分词结果是否展示词性
         */
        public static boolean ShowTermNature = true;
        /**
         * 是否执行字符正规化（繁体->简体，全角->半角，大写->小写），切换配置后必须删CustomDictionary.txt.bin缓存
         */
        public static boolean Normalization = false;

        static {
            Properties p = new Properties();
            try {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                if (loader == null) {
                    loader = Xmnlp.Config.class.getClassLoader();
                }
                p.load(new InputStreamReader(Static.PROPERTIES_PATH == null ?
                        loader.getResourceAsStream("xmnlp.properties")
                        : new FileInputStream(Static.PROPERTIES_PATH), "UTF-8"));
                String root = p.getProperty("root", "").replaceAll("\\\\", "/");
                if (!root.endsWith("/")) root += "/";
                String prePath = root;
                MaxEntModelPath = root + p.getProperty("MaxEntModelPath", MaxEntModelPath);
                NNParserModelPath = root + p.getProperty("NNParserModelPath", NNParserModelPath);
                CRFSegmentModelPath = root + p.getProperty("CRFSegmentModelPath", CRFSegmentModelPath);
                CRFDependencyModelPath = root + p.getProperty("CRFDependencyModelPath", CRFDependencyModelPath);
                HMMSegmentModelPath = root + p.getProperty("HMMSegmentModelPath", HMMSegmentModelPath);
                ShowTermNature = "true".equals(p.getProperty("ShowTermNature", "true"));
                Normalization = "true".equals(p.getProperty("Normalization", "false"));
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("make sure the xmnlp.properties is exist.");
                String classPath = (String) System.getProperties().get("java.class.PATHS");
                if (classPath != null) {
                    for (String path : classPath.split(File.pathSeparator)) {
                        if (new File(path).isDirectory()) {
                            sb.append(path).append('\n');
                        }
                    }
                }
                sb.append("并且编辑root=PARENT/PATHS/to/your/data\n");
                sb.append("现在Xmnlp将尝试从").append(System.getProperties().get("user.dir")).append("读取data……");
                logger.warn("没有找到xmnlp.properties，可能会导致找不到data\n" + sb);
            }
        }

    }

    private Xmnlp() {
    }


    /**
     * CRF分词
     *
     * @param text 文本
     * @return 切分后的单词
     */
    public static List<Term> crfSegment(String text) {
        return new CRFSegment().seg(text);
    }

}
