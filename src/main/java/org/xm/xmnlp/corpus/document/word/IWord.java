package org.xm.xmnlp.corpus.document.word;

import java.io.Serializable;

/**
 * 词语接口
 */
public interface IWord extends Serializable {
    String getValue();

    String getLabel();

    void setLabel(String label);

    void setValue(String value);
}
