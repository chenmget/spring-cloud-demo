package com.iwhalecloud.retail.oms.dto;

import java.io.Serializable;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/24 09:31
 * @Description:
 */
public class ShelfDetailUpdateDTO implements Serializable {
    private int status;
    private String operatingPositionId;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOperatingPositionId() {
        return operatingPositionId;
    }

    public void setOperatingPositionId(String operatingPositionId) {
        this.operatingPositionId = operatingPositionId;
    }
}

