package com.iwhalecloud.retail.promo.errorcode;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.iwhalecloud.retail.dto.ErrorCodeDTO;
import com.iwhalecloud.retail.dto.ResultCode;
import com.iwhalecloud.retail.promo.errorcode.manager.ErrorCodeManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 异常编码初始化辅助类
 * @author: Z
 * @date: 2019/7/2 9:50
 */
@Service
@Slf4j
public class ErrorCodeInitHelper {
    private static final String MODULE_CODE = "promo";
    @Resource
    private ErrorCodeManager errorCodeManager;

    /**
     * 需要初始化的ErrorCode类
     */
    private Collection<Object> beans;


    /**
     * 初始化加了ErroCodeAnnotation注解的类属性
     *
     * @param beans
     * @return
     */
    public void initErrorCodes(Collection<Object> beans) {
        this.beans = beans;
        refreshInitErrorCodes();
    }


    /**
     * 重新刷新系统启动时初始化的ErrorCode类，
     */
    public void refreshInitErrorCodes() {

        if (CollectionUtils.isEmpty(beans)) {
            log.warn(">>>not found errorCode bean >>>");
            return;
        }

        Map<String, ErrorCodeDTO> errorCodeMap = getErrorCodeMap();

        for (Object bean : beans) {
            log.info(">>>init start>>>" + bean.toString());
            this.initErrorCode(errorCodeMap, bean);
            log.info(">>>init end>>>" + bean.toString());
        }
    }

    /**
     * 初始化类属性的异常编码信息
     *
     * @param errorCodeMap
     * @param obj
     * @return
     */
    private boolean initErrorCode(Map<String, ErrorCodeDTO> errorCodeMap, Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isPublic(field.getModifiers()) && field.getType() == ResultCode.class) {
                ResultCode resultCode = getResultCode(errorCodeMap, field.getName());
                ReflectionUtils.setField(field, obj, resultCode);
                log.info(">>>ErrorCodeInitHelper.initErrorCode class={} fieldName={} value={}>>>",
                        obj.getClass().getSimpleName(), field.getName(), JSON.toJSON(resultCode));
            }
        }
        return true;
    }

    /**
     * 获取异常编码对象
     *
     * @param errorCodeMap
     * @param name
     * @return
     */
    private ResultCode getResultCode(Map<String, ErrorCodeDTO> errorCodeMap, String name) {

        String errorMsg = getErrorMsg(errorCodeMap, name);
        return new ResultCode() {
            @Override
            public String getCode() {
                return name;
            }

            @Override
            public String getDesc() {
                return errorMsg;
            }
        };
    }

    /**
     * 获取异常描述
     *
     * @param errorCodeMap
     * @param name
     * @return
     */
    private String getErrorMsg(Map<String, ErrorCodeDTO> errorCodeMap, String name) {
        if (errorCodeMap.containsKey(name)) {
            return errorCodeMap.get(name).getErrorMsg();
        }

        return name + " unconfig errorcode";
    }

    /**
     * 获取异常编码Map
     */
    private Map<String, ErrorCodeDTO> getErrorCodeMap() {
        List<ErrorCodeDTO> errorCodes = errorCodeManager.queryErrorCodes(MODULE_CODE);
        if (CollectionUtils.isEmpty(errorCodes)) {
            return Maps.newHashMap();
        }

        return errorCodes.stream().collect(Collectors.toMap(ErrorCodeDTO::getErrorCode, Function.identity()));
    }

}
