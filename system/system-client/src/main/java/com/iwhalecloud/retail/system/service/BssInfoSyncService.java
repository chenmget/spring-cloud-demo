package com.iwhalecloud.retail.system.service;


public interface BssInfoSyncService {

    /**
     * 统一门户用户信息同步
     *
     * @param stringJson
     * @return
     */
    String userInfoSync(String stringJson);

    /**
     * bss组织信息同步 add by xu.qinyuan
     *
     * @param jsonString
     * @return
     */
    String syncOrg(String jsonString);

}
