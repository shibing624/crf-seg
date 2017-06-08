# crf-seg

**crf-seg**是CRF模型用于自然语言处理（NLP）的Java工具包，目标是普及自然语言处理在生产环境中的应用。
**crf-seg**具备性能高效、架构清晰、语料时新、可自定义语料、可自定义模型的特点。

#### author：xuming(shibing624)
#### environment：jdk 1.8
#### 演示页面 http://www.borntowin.cn:8080/xmnlp


----

#### CRF模型对新词有很好的识别能力，对繁体字的处理及专有名词识别良好，但开销较大。是目前中文分词效果最好的模型，可用于生产环境。

## 使用
模型文件需要另外下载，并不包含在源码中，网盘下载：http://pan.baidu.com/s/1skQW35j，放置在 data/model/segment 下。

##### crf-seg调用方便：


`
System.out.println(Xmnlp.crfSegment("你好，欢迎使用CRF分词工具！"));
`

## 自定义模型
1. 提供linux版和windows版的crf++模型生成工具，网盘下载：http://pan.baidu.com/s/1skKkTgL 。
2. 请通过命令行参数指定CRF++生成txt格式的模型，比如：
   
   crf_learn  -f 3 -c 4.0 template train.bmes.txt crf-simple.model -t
   
   然后将生成的model.txt的路径替换到配置项CRFSegmentModelPath，首次运行后会得到相应的model.txt.bin文件；
   下次加载时会直接从bin缓存加载，速度会快很多。
