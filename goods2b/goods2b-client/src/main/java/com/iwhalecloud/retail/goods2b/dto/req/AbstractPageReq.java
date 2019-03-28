package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/11/29
 */
@Data
public abstract class AbstractPageReq extends AbstractRequest implements Serializable {

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
