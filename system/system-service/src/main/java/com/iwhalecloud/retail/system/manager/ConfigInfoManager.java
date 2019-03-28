package com.iwhalecloud.retail.system.manager;

import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.entity.ConfigInfo;
import com.iwhalecloud.retail.system.mapper.ConfigInfoMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class ConfigInfoManager{
    @Resource
    private ConfigInfoMapper configInfoMapper;

    @Cacheable(SystemConst.CACHE_NAME_SYS_CONFIG_INFO)
    public ConfigInfo getConfigInfoById(String cfId){
        return configInfoMapper.selectById(cfId);
    }
    
}
