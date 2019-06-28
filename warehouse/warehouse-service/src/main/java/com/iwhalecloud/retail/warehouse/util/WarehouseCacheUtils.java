package com.iwhalecloud.retail.warehouse.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.iwhalecloud.retail.warehouse.common.ResourceConst.WAREHOUSE_COMMON_CACHE_KEY;

/**
 * @Description: 仓库缓存工具类
 * @author: Z
 * @date: 2019/6/28 9:57
 */
@Service
@Slf4j
public class WarehouseCacheUtils {

    /**
     * 从缓存中获取值
     * @param key
     * @return
     */
    @Cacheable(value = WAREHOUSE_COMMON_CACHE_KEY, key = "#key", condition = "#key != null")
    public String get(String key) {
        log.info("WarehouseCacheUtils.get is null, key={}", key);
        return null;
    }

    /**
     * 更新缓存，如果缓存中不存在则新增，否则更新
     * @param key
     * @param value
     * @return
     */
    @CachePut(value = WAREHOUSE_COMMON_CACHE_KEY, key = "#key", condition = "#key != null")
    public String put(String key, String value) {
        log.info("WarehouseCacheUtils.set value, key={}, value={}", key, value);
        return value;
    }

    /**
     * 清空缓存
     * @param key
     */
    @CacheEvict(value = WAREHOUSE_COMMON_CACHE_KEY, key = "#key", condition = "#key != null")
    public void evict(String key) {
        log.info("WarehouseCacheUtils.evict value, key={}", key);
    }
}
