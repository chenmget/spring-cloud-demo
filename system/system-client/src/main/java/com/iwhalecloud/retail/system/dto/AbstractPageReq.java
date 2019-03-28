package com.iwhalecloud.retail.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/11/29
 */
@Data
public abstract class AbstractPageReq implements Serializable {

    private static final long serialVersionUID = 3763143915437085393L;

    /**
     * 页码
     */
    Integer pageNo;

    /**
     * 页大小
     */
    Integer pageSize;
}
