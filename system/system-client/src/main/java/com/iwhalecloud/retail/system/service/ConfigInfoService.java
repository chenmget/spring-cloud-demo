package com.iwhalecloud.retail.system.service;


import com.iwhalecloud.retail.system.dto.ConfigInfoDTO;

public interface ConfigInfoService{

    /**
     * 根据配置ID获取 配置信息
     * @param cfId
     * @return
     */
    ConfigInfoDTO getConfigInfoById(String cfId);

}