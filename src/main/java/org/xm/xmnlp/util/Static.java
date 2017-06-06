package org.xm.xmnlp.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 静态全局变量
 *
 * @author XuMing
 */
public class Static {
    public static String PROPERTIES_PATH;

    /**
     * 日志组件
     */
    public static Logger logger = LogManager.getLogger();

    /**
     * 二进制文件后缀
     */
    public final static String BIN_EXT = ".bin";

}
