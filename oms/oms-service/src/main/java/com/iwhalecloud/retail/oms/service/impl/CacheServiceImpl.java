package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.cache.ICache;
import com.iwhalecloud.retail.oms.manager.ICacheManage;
import com.iwhalecloud.retail.oms.service.ICacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

/**
 * @Author My
 * @Date 2018/11/13
 **/
@Slf4j
@Service
public class CacheServiceImpl implements ICacheService {

    @Autowired
    ICacheManage iCacheManage;

    //   是否走集群
    private String jiqun = "yes";

    @Autowired
    private ICache cache;

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public boolean tempAddCart(String key, String value) {
        String str =  "";
        try {
            str =  (String)jedisCluster.get(key);
            if(StringUtils.isEmpty(str)){
                jedisCluster.set(key, value);
            }
            jedisCluster.del(key);
            jedisCluster.set(key, value);
        }catch (Exception e){
            log.info("CacheServiceImpl tempAddCart Exception={} ",e);
            return false;
        }
        return true;
    }

    @Override
    public String getTempCart(String key) {
        String str =  (String)jedisCluster.get(key);
        if(StringUtils.isEmpty(str)){
            return null;
        }
        jedisCluster.del(key);
        return str;
    }

    @Override
    public boolean selectTempCart(String key) {
        return jedisCluster.exists(key);
    }
}
