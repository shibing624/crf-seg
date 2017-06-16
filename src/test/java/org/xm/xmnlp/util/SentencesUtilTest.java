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
    private static Logger logger = LogManager.getLogger();

    @Test
    public void toSentenceList() throws Exception {
        logger.error("err");
        logger.info(SentencesUtil.toSentenceList("你在做什么？我是一只小小鸟，我会飞。你呢？"));
    }

}