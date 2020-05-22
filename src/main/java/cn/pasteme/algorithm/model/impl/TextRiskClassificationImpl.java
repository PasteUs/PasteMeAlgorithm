package cn.pasteme.algorithm.model.impl;

import cn.pasteme.algorithm.http.HttpClient;
import cn.pasteme.algorithm.http.impl.HttpClientImpl;
import cn.pasteme.algorithm.model.TextRiskClassification;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author Lucien
 * @version 1.0.0
 */
public class TextRiskClassificationImpl implements TextRiskClassification {

    private final String modelName;

    private final HttpClient httpClient;

    private final String host = "predict.pasteme.lucien.ink";

    private final String url;

    public TextRiskClassificationImpl(String modelName) {
        this.modelName = modelName;
        this.httpClient = new HttpClientImpl();
        this.url =  String.format("http://%s/v1/models/%s:predict", this.host, this.modelName);
    }

    @Override
    public int inference(String content) {
        try {
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("content", content);
            JSONObject jsonResponse = httpClient.post(this.url, jsonRequest);
            List<Integer> resultList = jsonResponse.getJSONArray("predictions").toJavaList(Integer.class);
            assert resultList.size() == 1;
            return resultList.get(0);
        } catch (Exception e) {
            return -1;
        }
    }
}
