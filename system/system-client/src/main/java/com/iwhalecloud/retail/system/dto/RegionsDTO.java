package com.iwhalecloud.retail.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author My
 * @Date 2018/11/23
 **/
@Data
public class RegionsDTO implements Serializable {

    /**
     * 父级Id
     */
    private String pRegionId;
}
