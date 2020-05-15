package cn.pasteme.algorithm.http;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Lucien
 * @version 1.0.0
 */
public interface HttpClient {

    /**
     * POST Request
     *
     * @param url URL
     * @param json JSON parameter
     * @return JSON response
     */
    JSONObject post(String url, JSONObject json);
}
