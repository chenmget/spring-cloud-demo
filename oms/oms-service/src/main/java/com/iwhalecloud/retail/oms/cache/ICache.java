package com.iwhalecloud.retail.oms.cache;

import java.io.Serializable;

/**
 * 缓存操作类
 * @author Z
 * @date 2018/11/23
 */
public interface ICache {


    /**
     * 根据Key值和命名空间返回对象
     * @param nameSpace 数据所在的namespace
     * @param key 要获取的数据的key
     * @return 数据
     */
    public Serializable get(int nameSpace,String  key);


    /**
     * 按照命名空间设置数据，如果数据已经存在，则覆盖，如果不存在，则新增 <br>
     * 如果是新增，则有效时间为0，即不失效
     * @param nameSpace
     * @param key
     * @param value
     */
    public void set(int nameSpace,String key, Serializable value);

    /**
     * 按照命名空间设置数据，如果数据已经存在，则覆盖，如果不存在，则新增 <br>
     * 如果是新增，则有效时间为expireTime，单位为秒
     * @param nameSpace
     * @param key
     * @param value
     * @param expireTime
     */
    public void set(int nameSpace,String key, Serializable value, int expireTime);



    /**
     * 根据命名空间和key值删除相应的值
     * @param nameSpace
     * @param key
     */
    public void delete(int nameSpace,String key);


    /**
     * 根据命名空间清除cache
     * @param nameSpace
     */
    public void clear(int nameSpace);
}
