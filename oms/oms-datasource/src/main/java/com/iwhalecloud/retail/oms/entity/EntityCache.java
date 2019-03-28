package com.iwhalecloud.retail.oms.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/13
 **/
@Data
public class EntityCache implements Serializable {
    /**
     * 保存的数据
     */
    private  Object datas;

    /**
     * 设置数据失效时间,为0表示永不失效
     */
    private  long timeOut;

    /**
     * 最后刷新时间
     */
    private  long lastRefeshTime;

    public EntityCache(Object datas, long timeOut, long lastRefeshTime) {
        this.datas = datas;
        this.timeOut = timeOut;
        this.lastRefeshTime = lastRefeshTime;
    }


}
