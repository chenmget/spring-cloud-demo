package com.iwhalecloud.retail.web.controller;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.exception.GoodsRulesException;
import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import com.iwhalecloud.retail.web.exception.ParamInvalidException;
import com.iwhalecloud.retail.web.exception.UserNotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.sql.SQLException;

/**
 * 统一异常控制类，捕获系统抛出的异常进行封装后返回到前台
 * @author Z
 *
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseController<Object>{

    @Value("${spring.profiles.active}")
    private String env;

    private ResultVO<Object> createResultVO(ResultCodeEnum resultCodeEnum,String detailMessage) {

        //如果是本地环境/测试环境/开发环境抛出详细错误
//        if (env.contains("local") || env.contains("test") || env.contains("dev") || true) {
            return resultVO(resultCodeEnum.getCode(), resultCodeEnum.getDesc(), detailMessage);
//        }

//        return resultVO(resultCodeEnum.getCode(), resultCodeEnum.getDesc(), "");
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(SQLException.class)
    public ResultVO<Object> handleSQLException(HttpServletRequest request, Exception ex) {

        log.error("请求失败[" + request.getRequestURI() + "]", ex);
        return createResultVO(ResultCodeEnum.SYSTEM_ERROR, ex.getMessage());
    }

    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="IOException occured")
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public void handleIOException(){
        log.error("handleIOException");
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultVO<Object> handleHttpMessageNotReadableException(HttpServletRequest request, Exception ex){
        log.error("请求失败[" + request.getRequestURI() + "]，参数异常", ex);

        return createResultVO(ResultCodeEnum.PARAMTER_ERROR, ex.getMessage());
    }



    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(UserNotLoginException.class)
    public ResultVO<Object> signException(HttpServletRequest request,UserNotLoginException ex) {
        log.error("请求失败[" + request.getRequestURI() + "]，用户未登录");
        return ex.getResultVo();
    }



    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(RpcException.class)
    public ResultVO<Object> handleException(HttpServletRequest request,RpcException ex){
        log.error("请求失败[" + request.getRequestURI() + "]，缺少服务提供", ex);

        final String errorDesc = "请求失败[" + request.getRequestURI() + "]，" + ResultCodeEnum.FORBID_CONSUMER.getDesc();
        return resultVO(ResultCodeEnum.FORBID_CONSUMER.getCode(), errorDesc , ex.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultVO<Object> handleException(HttpServletRequest request,Exception ex){
        log.error("请求失败[" + request.getRequestURI() + "]", ex);

        return createResultVO(ResultCodeEnum.SYSTEM_ERROR, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(ParamInvalidException.class)
    public ResultVO<Object> handleException(HttpServletRequest request,ParamInvalidException ex){
        log.error("请求失败[" + request.getRequestURI() + "]，参数校验失败,"+JSON.toJSONString(ex.getErrors()), ex);

        final String detailMessage = StringUtils.collectionToDelimitedString(ex.getErrors(),"\n");

        return resultVO(ResultCodeEnum.PARAMTER_ERROR.getCode(), detailMessage, ResultCodeEnum.PARAMTER_ERROR.getDesc());
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(UndeclaredThrowableException.class)
    public ResultVO<Object> handleException(HttpServletRequest request,UndeclaredThrowableException ex){
        if (ex.getCause() instanceof ParamInvalidException) {
            return handleException(request,(ParamInvalidException)ex.getCause());
        } else if (ex.getCause() instanceof GoodsRulesException) {
            return handleException(request,(GoodsRulesException)ex.getCause());
        } else {
            log.error("请求失败[" + request.getRequestURI() + "]", ex);

            return createResultVO(ResultCodeEnum.SYSTEM_ERROR, ex.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(MultipartException.class)
    public ResultVO<Object> handleException(HttpServletRequest request,MultipartException ex){
        log.error("请求失败[" + request.getRequestURI() + "]", ex);

        return createResultVO(ResultCodeEnum.FILE_UPLOAD_ERROR, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(GoodsRulesException.class)
    public ResultVO<Object> goodsRulesException(HttpServletRequest request,GoodsRulesException ex){
        log.error("请求失败[" + request.getRequestURI() + "]，添加分货规则失败,"+JSON.toJSONString(ex.getErrors()), ex);

        final String detailMessage = StringUtils.collectionToDelimitedString(ex.getErrors(),"\n");

        return resultVO(ResultCodeEnum.ERROR.getCode(), detailMessage, ResultCodeEnum.ERROR.getDesc());

    }
}
