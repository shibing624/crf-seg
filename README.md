# crf-seg

**crf-seg**是CRF模型用于自然语言处理（NLP）的Java工具包，目标是普及自然语言处理在生产环境中的应用。
**crf-seg**具备性能高效、架构清晰、语料时新、可自定义语料、可自定义模型的特点。

#### author：xuming(shibing624)
#### environment：jdk 1.8
#### 演示页面 http://www.borntowin.cn:8080/xmnlp


----

#### CRF模型对新词有很好的识别能力，对繁体字的处理及专有名词识别良好，但开销较大。是目前中文分词效果最好的模型，可用于生产环境。

##### crf-seg调用方便：


`
System.out.println(Xmnlp.crfSegment("你好，欢迎使用CRF分词工具！"));
`