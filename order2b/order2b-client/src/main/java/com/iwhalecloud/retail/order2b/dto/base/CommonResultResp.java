package com.iwhalecloud.retail.order2b.dto.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 *
 */
@Data
@ApiModel(value = "接口返回对象")
public class CommonResultResp<T> implements Serializable {

    /**
     * 响应编码
     */
    @ApiModelProperty(value = "响应编码")
    private String resultCode;
    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息")
    private String resultMsg;
    /**
     * 响应结果
     */
    @ApiModelProperty(value = "响应结果")
    private T resultData;

    public CommonResultResp(){}

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
        boolean b=ResultCodeEnum.SUCCESS.getCode().equals(this.resultCode);
        if (b && StringUtils.isEmpty(getResultMsg())){
            setResultMsg("成功");
        }
    }

    @JsonIgnore
    public boolean isSuccess(){
        return ResultCodeEnum.SUCCESS.getCode().equals(this.resultCode);
    }

    @JsonIgnore
    public boolean isFailure(){
        return ResultCodeEnum.ERROR.getCode().equals(this.resultCode);
    }
}
