package com.iwhalecloud.retail.warehouse.dto.response.markresswap;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/11 15:33
 */
@Data
@ApiModel("仓库信息同步接口返回参数")
public class SynMarkResStoreResp implements Serializable {

    private String message;
    private String code;


}
