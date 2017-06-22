package org.xm.xmnlp.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xm.xmnlp.Xmnlp;
import org.xm.xmnlp.seg.domain.Term;

import java.util.List;

/**
 * @author XuMing
 */
public class CRFDemo {
    /**
     * 日志组件
     */
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Xmnlp.Config.ShowTermNature = true;    // 关闭词性显示
        String[] sentenceArray = new String[]
                {
                        "nlp是由一系列模型与算法组成的Java工具包，目标是普及自然语言处理在生产环境中的应用。",
                        "鐵桿部隊憤怒情緒集結 馬英九腹背受敵",           // 繁体无压力
                        "馬英九回應連勝文“丐幫說”：稱黨內同志談話應謹慎",
                        "高锰酸钾，强氧化剂，紫红色晶体，可溶于水，遇乙醇即被还原。常用作消毒剂、水净化剂、氧化剂、漂白剂、毒气吸收剂、二氧化碳精制剂等。", // 专业名词有一定辨识能力
                        "《夜晚的骰子》通过描述浅草的舞女在暗夜中扔骰子的情景,寄托了作者对庶民生活区的情感",    // 非新闻语料
                        "这个像是真的[委屈]前面那个打扮太江户了，一点不上品..",                       // 微博
                        "鼎泰丰的小笼一点味道也没有...每样都淡淡的...淡淡的，哪有食堂2A的好次",
                        "克里斯蒂娜·克罗尔说：不，我不是虎妈。我全家都热爱音乐，我也鼓励他们这么做。",
                        "今日APPS：Sago Mini Toolbox培养孩子动手能力",
                        "财政部副部长王保安调任国家统计局党组书记",
                        "2.34米男子娶1.53米女粉丝 称夫妻生活没问题",
                        "你看过穆赫兰道吗",
                        "国办发布网络提速降费十四条指导意见 鼓励流量不清零",
                        "乐视超级手机能否承载贾布斯的生态梦，这个研究生会五种语言,硕士研究生鱼片与苹果",
                        "来一段:工信部女处长每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作",

                };
        for (String sentence : sentenceArray) {
            List<Term> termList = Xmnlp.crfSegment(sentence);
            logger.info(termList);
        }
    }
}
