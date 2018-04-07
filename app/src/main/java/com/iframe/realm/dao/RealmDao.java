package com.iframe.realm.dao;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by Administrator on 2016/7/16.
 */
public interface RealmDao <T extends RealmObject> {
    /**
     * 插入Object
     * @param object
     * @throws Exception
     */
    void insertObject(T object)throws  Exception;


    /**
     * 获取所有的用户
     * @return
     * @throws Exception
     */
    List<T> getAllObject()throws Exception;

    /**
     *  更新用户
     *  @param object
     *  @return
     * @throws Exception
     */
    T updateObject(T object)throws Exception;

    /**
     * 删除用户
     * @param objectId
     * @throws Exception
     */
    void deleteObject(String objectId)throws Exception;

    /**
     * 查询用户
     * @param objectId
     * @return
     * @throws Exception
     */
    boolean queryObject(String objectId)throws Exception;

    /**
     * 获取用户
     * @param objectId
     * @return
     * @throws Exception
     */
    T getObject(String objectId)throws Exception;


    /**
     * 异步插入Object
     * @param object
     * @throws Exception
     */
    void insertObjectAsync(T object)throws Exception;

    /**
     * 结束  释放数据库资源
     * @throws Exception
     */
    void finishRealm()throws  Exception;
}
