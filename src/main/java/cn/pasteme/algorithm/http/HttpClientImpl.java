package cn.pasteme.algorithm.http;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Lucien
 * @version 1.0.0
 */
@Slf4j
@Component
public class HttpClientImpl implements cn.pasteme.algorithm.http.HttpClient {

    private final HttpClient httpClient;

    public HttpClientImpl() {
        this.httpClient = HttpClientBuilder.create().build();
    }

    private String inputStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1 << 10];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        return byteArrayOutputStream.toString(StandardCharsets.UTF_8.name());
    }

    @Override
    public JSONObject post(String url, JSONObject json) throws IOException {
        return post(url, json.toString());
    }

    @Override
    public JSONObject post(String url, String json) throws IOException {
        HttpPost request = new HttpPost(url);
        StringEntity params = new StringEntity(json, "UTF-8");
        request.addHeader("Content-Type", "application/json");
        request.setEntity(params);
        HttpResponse response = httpClient.execute(request);
        InputStream inputStream = response.getEntity().getContent();
        String stringResponse = inputStreamToString(inputStream);
        log.info("Get response = {} from {}", stringResponse, url);
        return JSONObject.parseObject(stringResponse);
    }

}
