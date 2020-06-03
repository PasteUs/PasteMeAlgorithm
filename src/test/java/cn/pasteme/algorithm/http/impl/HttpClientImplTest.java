package cn.pasteme.algorithm.http.impl;

import cn.pasteme.algorithm.http.HttpClient;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class HttpClientImplTest {

    @Test
    public void testPost() {
        HttpClient httpClient = new HttpClientImpl();
        try {
            JSONObject jsonResponse = httpClient.post(
                    "http://predict.pasteme.lucien.ink/v1/models/PasteMeRIM:predict",
                    "{\"content\": \"Hello World!\"}");
            log.info("{}", jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}