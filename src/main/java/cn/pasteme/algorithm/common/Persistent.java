package cn.pasteme.algorithm.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Lucien
 * @version 1.0.0
 */
public interface Persistent extends Serializable {

    interface SaveObject {
        void saveObject(ObjectOutputStream objectOutputStream) throws Exception;
    }

    interface LoadObject {
        void loadObject(ObjectInputStream objectInputStream) throws Exception;
    }

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
     * @param filePath 文件路径
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
}
