package com.iwhalecloud.retail.warehouse.dto.response.markres.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 营销资源执行结果详细
 * @author 吴良勇
 * @date 2019/3/2 11:23
 */
@Data
public class ExcuteMarkResResultSimplResp implements Serializable {

    private String resultCode;
    private String resultMsg;


}
