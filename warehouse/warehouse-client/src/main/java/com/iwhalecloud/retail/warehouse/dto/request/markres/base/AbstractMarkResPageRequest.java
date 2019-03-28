package com.iwhalecloud.retail.warehouse.dto.request.markres.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 营销资源分页请求基类
 */
@Data
public abstract class AbstractMarkResPageRequest  implements Serializable {
    private String pageSize;
    private String pageIndex;


}
