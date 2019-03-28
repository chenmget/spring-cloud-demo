package com.iwhalecloud.retail.oms.service.zop;

import com.iwhalecloud.retail.dto.ResultVO;

/**
 * @author Z
 * @date 2018/11/21
 */
public interface ZopClientService {

    /**
     * 获取accessToken 优先从缓存中获取
     * @return
     */
    String getAccessToken();

    /**
     * 强制刷新accessToken
     * @return
     */
    String forceRefreshAccessToken();

    /**
     * 调用能开服务
     * @param method 能力方法
     * @param version 能力版本
     * @param params 参数，将会转成json串
     * @return
     */
    ResultVO callRest(String method, String version, Object params);

    /**
     * 调用能开服务
     * @param method 能力方法
     * @param version 能力版本
     * @param params 参数，将会转成json串
     * @param timeout 超时时间
     * @return
     */
    ResultVO callRest(String method, String version, Object params,int timeout);

    /**
     * 设置能开配置信息
     * @param zopConfigStorage
     */
    void setZopConfigStorage(ZopConfigStorage zopConfigStorage);

    /**
     * 获取能开配置信息
     * @return
     */
    ZopConfigStorage getZopConfigStorage();

}
