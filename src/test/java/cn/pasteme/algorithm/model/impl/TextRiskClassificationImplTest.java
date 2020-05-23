package cn.pasteme.algorithm.model.impl;

import cn.pasteme.algorithm.model.TextRiskClassification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TextRiskClassificationImplTest {

    @Autowired
    TextRiskClassification textRiskClassification;

    @Test
    public void testInference() {
        System.out.print(textRiskClassification.inference("Hello World!"));
    }
}