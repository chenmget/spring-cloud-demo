package com.iwhalecloud.retail.oms.service.zop;

/**
 * @author Z
 * @date 2018/11/21
 */
public interface ZopConfigStorage {


    /**
     * 获取accessToken
     * @return
     */
    String getAccessToken();


    /**
     * 设置token
     * @param accessToken
     */
    void setAccessToken(String accessToken);


    /**
     * 判断token是否失效
     * @return
     */
    boolean isAccessTokenExpired();


    /**
     * 失效token
     */
    void expireAccessToken();

    /**
     * 获取配置能开的url路径
     * @return
     */
    String getUrl();

    /**
     * 获取配置的appId
     * @return
     */
    String getAppId();

    /**
     * 获取访问密钥
     * @return
     */
    String getAppSecret();

    /**
     * 获取请求超时时间
     * @return
     */
    int getTimeout();

    /**
     * 设置失效时间
     * @param expiresTime
     */
    void setExpiresTime(long expiresTime);

    void setAppId(String appId);

    void setAppSecret(String appSecret);

    void setUrl(String url);

    void setTimeout(int timeout);


}
