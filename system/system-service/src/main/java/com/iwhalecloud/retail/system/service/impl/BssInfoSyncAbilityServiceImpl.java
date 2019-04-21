package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.system.service.BssInfoSyncAbilityService;
import com.iwhalecloud.retail.system.service.BssInfoSyncService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * bss 信息同步接口能力
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年04月02日
 */
@Service
public class BssInfoSyncAbilityServiceImpl implements BssInfoSyncAbilityService{

    @Autowired
    BssInfoSyncService bssInfoSyncService;

    @Override
    public Map<String, Object> syncOrgInfo(String jsonString) {
        Map<String, Object> resultMap = new HashMap<>();
        String resultStr = bssInfoSyncService.syncOrg(jsonString);
        resultMap.put("resultData", resultStr);
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        return resultMap;
    }

    @Override
    public Map<String, Object> userInfoSync(String jsonString) {
        Map<String, Object> resultMap = new HashMap<>();
        String resultStr = bssInfoSyncService.userInfoSync(jsonString);
        resultMap.put("resultData", resultStr);
        resultMap.put("resultCode", "0");
        resultMap.put("resultMsg", "调用成功");
        return resultMap;
    }
}
