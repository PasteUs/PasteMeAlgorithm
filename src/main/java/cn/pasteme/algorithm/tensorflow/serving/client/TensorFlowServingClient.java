package cn.pasteme.algorithm.tensorflow.serving.client;

import com.alibaba.fastjson.JSONObject;

/**
 * 通过 HTTP 调用 TensorFlow serving 的 API
 *
 * @author Lucien
 * @version 1.0.0
 */
public interface TensorFlowServingClient {

    /**
     * 调用模型
     *
     * @param param 模型参数
     * @return JSON 模型的推理结果
     */
    JSONObject inference(JSONObject param);
}
