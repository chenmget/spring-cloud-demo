package com.iwhalecloud.retail.warehouse.dto.response.markres.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 营销资源执行结果详细
 * @author 吴良勇
 * @date 2019/3/2 11:23
 */
@Data
public class ExcuteMarkResResultResp implements Serializable {

    private List<ExcuteMarkResResultItemResp> ResultInfo_Item;



}
