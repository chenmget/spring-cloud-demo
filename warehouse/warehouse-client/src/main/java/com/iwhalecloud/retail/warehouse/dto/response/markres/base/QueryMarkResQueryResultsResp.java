package com.iwhalecloud.retail.warehouse.dto.response.markres.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 营销资源查询返回结果集
 * @author 吴良勇
 * @date 2019/3/2 9:49
 */
@Data
public class QueryMarkResQueryResultsResp<T> implements Serializable {
    private List<T> QueryInfo;

}
