package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.system.dto.ConfigInfoDTO;
import com.iwhalecloud.retail.system.entity.ConfigInfo;
import com.iwhalecloud.retail.system.manager.ConfigInfoManager;
import com.iwhalecloud.retail.system.service.ConfigInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ConfigInfoServiceImpl implements ConfigInfoService {

    @Autowired
    private ConfigInfoManager configInfoManager;

    /**
     * 根据配置ID获取 配置信息
     * @param cfId
     * @return
     */
    @Override
    public ConfigInfoDTO getConfigInfoById(String cfId) {

        ConfigInfo configInfo = configInfoManager.getConfigInfoById(cfId);
        if (configInfo == null){
            return null;
        }
        ConfigInfoDTO configInfoDTO = new ConfigInfoDTO();
        BeanUtils.copyProperties(configInfo, configInfoDTO);
        return configInfoDTO;
    }
}