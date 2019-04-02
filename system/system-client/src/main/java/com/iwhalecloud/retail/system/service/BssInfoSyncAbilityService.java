package com.iwhalecloud.retail.system.service;

import java.util.Map;

/**
 * bss3.0信息同步能力接口
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年04月02日
 */
public interface BssInfoSyncAbilityService {

    Map<String, Object> syncOrgInfo(String jsonString);
}
