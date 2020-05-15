package cn.pasteme.algorithm.tensorflow.serving.client.impl;

import cn.pasteme.algorithm.tensorflow.serving.client.TensorFlowServingClient;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author Lucien
 * @version 1.0.0
 */
public class TensorFlowServingClientImpl implements TensorFlowServingClient {

    private final String modelName;

    private final Integer version;

    public TensorFlowServingClientImpl(String modelName, Integer version) {
        this.modelName = modelName;
        this.version = version;
    }

    @Override
    public JSONObject inference(JSONObject param) {
        return null;
    }
}
