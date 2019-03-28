package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.cache.ICache;
import com.iwhalecloud.retail.oms.service.TempCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * @author pjq
 * @date 2018/10/8
 */
@Slf4j
@Service
public class TempCartServiceImpl implements TempCartService {

    @Autowired
    private ICache cache;

    public static Integer nameSpace = 1;

    @Override
    public boolean tempAddCart(String key, String value) {
        String str =  "";
        try {
            str = (String) cache.get(nameSpace,key);
            if(StringUtils.isEmpty(str)){
                cache.set(nameSpace,key,value);
            }
            cache.delete(nameSpace,key);
            cache.set(nameSpace,key, value);
        }catch (Exception e){
            log.info("TempCartServiceImpl tempAddCart Exception={} ",e);
            return false;
        }
        return true;
    }

    @Override
    public String getTempCart(String key) {
        String str =  (String) cache.get(nameSpace,key);
        if(StringUtils.isEmpty(str)){
            return null;
        }
        cache.delete(nameSpace,key);
        return str;
    }

    @Override
    public boolean selectTempCart(String key) {
        String str =  (String) cache.get(nameSpace,key);
        if(StringUtils.isEmpty(str)){
            return false;
        }
        return true;
    }
}
