package com.iwhalecloud.retail.net;

/**
 * @author gongS
 * @date 2018/7/17 17:57
 */

public interface FinishCallBack {

    /**
     * 成功
     * @param  message data
     */
    void success(String message);

    /**
     * 失败
     * @param  message error
     */
    void failure(String message);
}
