package org.xm.xmnlp.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * @author XuMing
 */
public class SentencesUtilTest {
    /**
     * 日志组件
     */
    public static Logger logger = LogManager.getLogger();

    @Test
    public void toSentenceList() throws Exception {
        logger.error("err");
        logger.info(SentencesUtil.toSentenceList("你叫什么？我是架构师。你呢？"));
    }

}