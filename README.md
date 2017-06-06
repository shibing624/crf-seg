# crf-seg
CRF

CRF对新词有很好的识别能力，对繁体字的处理及专有名词识别良好，但开销较大。是目前分词效果最好的模型，可用于生产环境。

crf-seg调用方便：


System.out.println(Xmnlp.crfSegment("你好，欢迎使用CRF分词工具！"));