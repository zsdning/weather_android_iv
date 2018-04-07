package com.iframe.realm.dao;

/**
 * Created by zsdning on 2016/6/29.
 */

import com.iframe.realm.module.Person;

import java.util.List;

/**
 * Created by zsdning.
 */
public interface PersonDao {

    /**
     * 插入Person
     * @param person
     * @throws Exception
     */
    void insertPerson(Person person)throws  Exception;


    /**
     * 获取所有的用户
     * @return
     * @throws Exception
     */
    List<Person> getAllPerson()throws Exception;

    /**
     *  更新用户
     *  @param person
     *  @return
     * @throws Exception
     */
    Person updatePerson(Person person)throws Exception;

    /**
     * 删除用户
     * @param personId
     * @throws Exception
     */
    void deletePerson(String personId)throws Exception;

    /**
     * 查询用户
     * @param personId
     * @return
     * @throws Exception
     */
    boolean queryPerson(String personId)throws Exception;

    /**
     * 获取用户
     * @param personId
     * @return
     * @throws Exception
     */
    Person getPerson(String personId)throws Exception;


    /**
     * 异步插入Product
     * @param person
     * @throws Exception
     */
    void insertPersonAsync(Person person)throws Exception;

    /**
     * 结束  释放数据库资源
     * @throws Exception
     */
    void finishRealm()throws  Exception;

}
