package cn.pasteme.algorithm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Lucien
 * @version 1.0.1
 */
@SpringBootApplication(scanBasePackages = "cn.pasteme.algorithm")
public class AlgorithmApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlgorithmApplication.class, args);
    }
}
