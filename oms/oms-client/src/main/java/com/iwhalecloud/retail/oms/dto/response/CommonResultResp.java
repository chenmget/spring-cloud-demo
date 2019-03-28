package com.iwhalecloud.retail.oms.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 出参公共类
 *
 * @author mzl
 * @date 2018/9/30
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

    private CommonResultResp(String resultCode){
        this.resultCode = resultCode;
    }

    private CommonResultResp(String resultCode, T resultData){
        this.resultCode = resultCode;
        this.resultData = resultData;
    }

    private CommonResultResp(String resultCode, String resultMsg, T resultData){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.resultData = resultData;
    }

    private CommonResultResp(String resultCode, String resultMsg){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public static <T> CommonResultResp<T> success(){
        return new CommonResultResp<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getDesc());
    }

    public static <T> CommonResultResp<T> successMessage(String msg){
        return new CommonResultResp<>(ResultCodeEnum.SUCCESS.getCode(),msg);
    }

    public static <T> CommonResultResp<T> success(T data){
        return new CommonResultResp<>(ResultCodeEnum.SUCCESS.getCode(),ResultCodeEnum.SUCCESS.getDesc(),data);
    }

    public static <T> CommonResultResp<T> success(String msg, T data){
        return new CommonResultResp<T>(ResultCodeEnum.SUCCESS.getCode(),msg,data);
    }


    public static <T> CommonResultResp<T> error(){
        return new CommonResultResp<>(ResultCodeEnum.ERROR.getCode(), ResultCodeEnum.ERROR.getDesc());
    }

    public static <T> CommonResultResp<T> error(String errorMessage){
        return new CommonResultResp<>(ResultCodeEnum.ERROR.getCode(),errorMessage);
    }

    public static <T> CommonResultResp<T> errorEnum(ResultCodeEnum resultCodeEnum){
        return new CommonResultResp<>(resultCodeEnum.getCode(), resultCodeEnum.getDesc());
    }

    public static <T> CommonResultResp<T> error(String errorCode, String errorMessage){
        return new CommonResultResp<T>(errorCode,errorMessage);
    }

    @JsonIgnore
    public boolean isSuccess(){
        return ResultCodeEnum.SUCCESS.getCode().equals(this.resultCode);
    }
}
