package com.iwhalecloud.retail.warehouse.dto.response.markres.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 营销资源执行结果
 * @author 吴良勇
 * @date 2019/3/2 11:22
 */
@Data
public class ExcuteMarkResResp extends AbstractMarkResResp implements Serializable {
    private ExcuteMarkResResultResp ResultInfo;

}
