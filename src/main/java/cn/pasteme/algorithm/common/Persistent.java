package cn.pasteme.algorithm.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 可序列化对象接口
 *
 * @author Lucien
 * @version 1.0.1
 */
public interface Persistent extends Serializable {

    /**
     * 将对象持久化至磁盘
     *
     * @param filePath 文件路径
     * @return 持久化成功与否
     */
    boolean save(String filePath);

    /**
     * 将对象持久化至磁盘
     *
     * @param filePath   文件路径
     * @param saveObject 持久化行为
     * @return 持久化成功与否
     */
    default boolean save(String filePath, SaveObject saveObject) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            saveObject.saveObject(objectOutputStream);
            objectOutputStream.close();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从磁盘加载对象
     *
     * @param filePath 文件路径
     * @return 加载成功与否
     */
    boolean load(String filePath);

    /**
     * 从磁盘加载对象
     *
     * @param filePath   文件路径
     * @param loadObject lambda 函数
     * @return boolean
     */
    default boolean load(String filePath, LoadObject loadObject) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            loadObject.loadObject(objectInputStream);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    interface SaveObject {

        /**
         * lambda 接口函数
         *
         * @param objectOutputStream 序列化输出流
         * @throws Exception 异常
         */
        void saveObject(ObjectOutputStream objectOutputStream) throws Exception;
    }

    interface LoadObject {

        /**
         * lambda 接口函数
         *
         * @param objectInputStream 序列化输入流
         * @throws Exception 异常
         */
        void loadObject(ObjectInputStream objectInputStream) throws Exception;
    }
}
