package com.iwhalecloud.retail.system.utils;

import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Description: 仓库缓存工具类
 * @author: Z
 * @date: 2019/6/28 9:57
 */
@Service
@Slf4j
public class SysUserCacheUtils {

    /**
     * 从缓存中获取值
     * @param key
     * @return
     */
    @Cacheable(value = SystemConst.CACHE_NAME_SYS_USER, key = "#key", condition = "#key != null")
    public User get(String key) {
        log.info("WarehouseCacheUtils.get is null, key={}", key);
        return null;
    }

    /**
     * 更新缓存，如果缓存中不存在则新增，否则更新
     * @param key
     * @param value
     * @return
     */
    @CachePut(value = SystemConst.CACHE_NAME_SYS_USER, key = "#key", condition = "#key != null")
    public User put(String key, User value) {
        log.info("WarehouseCacheUtils.set value, key={}, value={}", key, value);
        return value;
    }

    /**
     * 清空缓存
     * @param key
     */
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_USER, key = "#key", condition = "#key != null")
    public void evict(String key) {
        log.info("WarehouseCacheUtils.evict value, key={}", key);
    }
}
