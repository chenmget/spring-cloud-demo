package com.iwhalecloud.retail.warehouse.dto.response.markres.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 营销资源查询返回结果
 * @author 吴良勇
 * @date 2019/3/2 9:47
 */
@Data
public class QueryMarkResResp<T> extends AbstractMarkResResp implements Serializable {
    private QueryMarkResQueryResultsResp<T> QueryResults;
}
