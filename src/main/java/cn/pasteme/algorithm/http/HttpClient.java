package cn.pasteme.algorithm.http;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

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
     * @throws IOException IO Exception
     */
    JSONObject post(String url, JSONObject json) throws IOException;

    /**
     * POST Request
     *
     * @param url URL
     * @param json JSON parameter in string type
     * @return JSON response
     * @throws IOException IO Exception
     */
    JSONObject post(String url, String json) throws IOException;
}
