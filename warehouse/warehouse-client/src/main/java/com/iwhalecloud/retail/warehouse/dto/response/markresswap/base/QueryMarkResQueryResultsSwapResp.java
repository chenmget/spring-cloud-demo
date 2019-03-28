package com.iwhalecloud.retail.warehouse.dto.response.markresswap.base;

/**
 * @author 吴良勇
 * @date 2019/3/6 20:17
 */

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryMarkResQueryResultsSwapResp<T> implements Serializable {
    private List<T> queryInfo;

}