package com.iwhalecloud.retail.warehouse.dto.response.markres.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/5 10:32
 */
@Data
public class ExcuteMarkResExcuteSimplResp extends AbstractMarkResResp implements Serializable {

    private  ExcuteMarkResResultSimplResp Result;

}
