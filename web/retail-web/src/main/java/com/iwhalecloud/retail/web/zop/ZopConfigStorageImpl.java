package com.iwhalecloud.retail.web.zop;

/**
 * @author Z
 * @date 2018/11/21
 */
public class ZopConfigStorageImpl implements ZopConfigStorage {

    private String appId;

    private String appSecret;

    private String url;

    private int timeout;

    private volatile long expiresTime = 0;

    private String accessToken;

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public boolean isAccessTokenExpired() {
        if (expiresTime == -1) {
            return false;
        }
        return System.currentTimeMillis() > this.expiresTime;
    }

    @Override
    public void expireAccessToken() {
        this.expiresTime = 0;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public String getAppId() {
        return this.appId;
    }

    @Override
    public String getAppSecret() {
        return this.appSecret;
    }

    @Override
    public int getTimeout() {
        return this.timeout;
    }

    @Override
    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }

    @Override
    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
