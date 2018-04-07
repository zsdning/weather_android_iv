package com.iframe.realm.dao;

/**
 * Created by zsdning on 2016/6/29.
 */

import android.content.Context;

import com.iframe.realm.module.Person;
import com.iframe.util.RealmUtil;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @className:ProductDaoImpl
 * @desc:
 * @author:zsdning
 * @datetime:16/6/29
 */
public class PersonDaoImpl implements PersonDao {

    private Context context;
    private Realm mRealm;

    public PersonDaoImpl(Context context) {
        mRealm = RealmUtil.getIntance(context).getRealm();
    }


    /**
     * @param person
     * @throws Exception
     * @同步插入用户
     */
    @Override
    public void insertPerson(Person person) throws Exception {
        mRealm.beginTransaction();
        Person person1 = mRealm.copyToRealm(person);
        mRealm.commitTransaction();
        //mRealm.close();
    }

    /**
     * 获取所有的用户
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<Person> getAllPerson() throws Exception {
        List<Person> mlist;
        mlist = mRealm.where(Person.class).findAll();
        // mRealm.close();
        return mlist;
    }

    /**
     * @param person
     * @throws Exception
     */
    @Override
    public Person updatePerson(Person person) throws Exception {
        mRealm.beginTransaction();
        Person product1 = mRealm.copyToRealmOrUpdate(person);
        mRealm.commitTransaction();
        // mRealm.close();
        return product1;
    }

    /**
     * 删除用户
     * @param personId
     * @throws Exception
     */
    @Override
    public void deletePerson(String personId) throws Exception {
        //删除 productId = ".."的用户
        Person product = mRealm.where(Person.class).equalTo("personId", personId).findFirst();
        mRealm.beginTransaction();
        product.deleteFromRealm();
        mRealm.commitTransaction();
    }

    /**
     * 查询用户
     * @param personId
     * @return
     * @throws Exception
     */
    @Override
    public boolean queryPerson(String personId) throws Exception {
        //查询 productId = ".."的用户
        RealmResults<Person> products = mRealm.where(Person.class).equalTo("personId", personId).findAll();
        mRealm.beginTransaction();
        if(products.size() > 0){
            mRealm.commitTransaction();
            return  true;
        }
        mRealm.commitTransaction();
        return false;
    }

    /**
     * 获取用户
     * @param personId
     * @return
     * @throws Exception
     */
    @Override
    public Person getPerson(String personId) throws Exception {
        //获取 productId = ".."的用户
        RealmResults<Person> persons = mRealm.where(Person.class).equalTo("personId", personId).findAll();
        mRealm.beginTransaction();
        if(persons.size() > 0){
            mRealm.commitTransaction();
            return  persons.get(0);
        }
        mRealm.commitTransaction();
        return null;
    }

    /**
     * 异步插入Person
     *
     * @param person
     * @throws Exception
     */
    @Override
    public void insertPersonAsync(final Person person) throws Exception {
        //一个Realm只能在同一个线程中访问，在子线程中进行数据库操作必须重新获取Realm对象：
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.beginTransaction();
                Person person1 = realm.copyToRealm(person);
                realm.commitTransaction();
                //realm.close();//并且要记得在离开线程时要关闭 realm.close();
            }
        });
        //mRealm.close();//关闭Realm对象
    }

    @Override
    public void finishRealm() throws Exception {
        mRealm.close();
    }
}
