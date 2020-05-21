package cn.pasteme.algorithm.http.impl;

import cn.pasteme.algorithm.http.HttpClient;
import com.alibaba.fastjson.JSONObject;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpClientImplTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

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