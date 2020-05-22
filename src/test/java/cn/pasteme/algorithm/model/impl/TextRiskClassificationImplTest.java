package cn.pasteme.algorithm.model.impl;

import cn.pasteme.algorithm.model.TextRiskClassification;
import junit.framework.TestCase;

public class TextRiskClassificationImplTest extends TestCase {

    public void testInference() {
        TextRiskClassification textRiskClassification = new TextRiskClassificationImpl("PasteMeRIM");
        System.out.print(textRiskClassification.inference("Hello World!"));
    }
}