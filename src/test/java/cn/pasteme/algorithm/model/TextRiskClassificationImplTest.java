package cn.pasteme.algorithm.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class TextRiskClassificationImplTest {

    @Autowired
    private TextRiskClassification textRiskClassification;

    @Test
    @Ignore
    public void inference() {
        String content = "你好，世界！";
        try {
            int result = textRiskClassification.inference(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}