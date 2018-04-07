package com.iframe.realm.dao;

import android.content.Context;

import com.iframe.util.RealmUtil;
import com.iframe.util.ReflectionUtils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Administrator on 2016/7/16.
 */
public class RealmDaoImpl<T extends RealmObject> implements RealmDao<T> {
    private Context context;
    private Realm mRealm;
    private Class<T> type;

    public RealmDaoImpl(){

    }

    public RealmDaoImpl(Context context) {
        type = ReflectionUtils.getSuperGenericType(getClass());
        mRealm = RealmUtil.getIntance(context).getRealm();
    }


    /**
     * @param person
     * @throws Exception
     * @同步插入用户
     */
    @Override
    public void insertObject(T person) throws Exception {
        mRealm.beginTransaction();
        T person1 = mRealm.copyToRealm(person);
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
    public List<T> getAllObject() throws Exception {
        List<T> mlist;
        mlist = mRealm.where(type).findAll();
        // mRealm.close();
        return mlist;
    }

    /**
     * @param object
     * @throws Exception
     */
    @Override
    public T updateObject(T object) throws Exception {
        mRealm.beginTransaction();
        T product1 = mRealm.copyToRealmOrUpdate(object);
        mRealm.commitTransaction();
        // mRealm.close();
        return product1;
    }

    /**
     * 删除用户
     * @param objectId
     * @throws Exception
     */
    @Override
    public void deleteObject(String objectId) throws Exception {
        //删除 productId = ".."的用户
        T product = mRealm.where(type).equalTo("objectId", objectId).findFirst();
        mRealm.beginTransaction();
        product.deleteFromRealm();
        mRealm.commitTransaction();
    }

    /**
     * 查询用户
     * @param objectId
     * @return
     * @throws Exception
     */
    @Override
    public boolean queryObject(String objectId) throws Exception {
        //查询 productId = ".."的用户
        RealmResults<T> products = mRealm.where(type).equalTo("objectId", objectId).findAll();
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
     * @param objectId
     * @return
     * @throws Exception
     */
    @Override
    public T getObject(String objectId) throws Exception {
        //获取 productId = ".."的用户
        RealmResults<T> persons = mRealm.where(type).equalTo("personId", objectId).findAll();
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
     * @param object
     * @throws Exception
     */
    @Override
    public void insertObjectAsync(final T object) throws Exception {
        //一个Realm只能在同一个线程中访问，在子线程中进行数据库操作必须重新获取Realm对象：
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.beginTransaction();
                T person1 = realm.copyToRealm(object);
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
