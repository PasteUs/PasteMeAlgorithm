package cn.pasteme.algorithm.model;

/**
 * 通过 HTTP 调用 TensorFlow serving 的 API
 *
 * @author Lucien
 * @version 1.0.0
 */
public interface TextRiskClassification {

    /**
     * 调用模型
     *
     * @param jsonString 模型参数
     * @return JSON 模型的推理结果
     */
    int inference(String jsonString);
}
