package cn.pasteme.algorithm.model;

import java.io.IOException;

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
     * @param content 文本内容
     * @return 模型的推理结果，0 代表 Normal，1 代表 Risk
     * @throws IOException HTTP 请求出错
     */
    int inference(String content) throws IOException;
}
