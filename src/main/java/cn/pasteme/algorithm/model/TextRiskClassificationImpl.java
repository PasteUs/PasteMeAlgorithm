package cn.pasteme.algorithm.model;

import cn.pasteme.algorithm.http.HttpClient;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author Lucien
 * @version 1.0.0
 */
@Slf4j
@Component
@Qualifier("textRiskClassificationImpl")
public class TextRiskClassificationImpl implements TextRiskClassification {

    private final String modelName;

    private final HttpClient httpClient;

    private final String host;

    private final String url;

    public TextRiskClassificationImpl(
            @Autowired HttpClient httpClient,
            @Value("${pasteme.algorithm.model.TextRiskClassificationImpl.host}") String host,
            @Value("${pasteme.algorithm.model.TextRiskClassificationImpl.modelName}") String modelName) {
        this.host = host;
        this.modelName = modelName;
        this.httpClient = httpClient;
        this.url =  String.format("http://%s/v1/models/%s:predict", this.host, this.modelName);
    }

    @Override
    public int inference(String content) throws IOException {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("content", content);
        JSONObject jsonResponse = httpClient.post(this.url, jsonRequest);
        log.info("TextRiskClassification get jsonResponse = {} from httpClient", jsonResponse);
        List<Integer> resultList = jsonResponse.getJSONArray("predictions").toJavaList(Integer.class);
        assert resultList.size() == 1;
        return resultList.get(0);
    }
}
