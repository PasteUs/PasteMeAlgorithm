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
                    "http://docker:8501/v1/models/PasteMeRIM:predict",
                    "{\"instances\": [[1.0, 2.0], [2.0, 3.0]]}");
            log.info("{}", jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}