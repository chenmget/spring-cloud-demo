package com.iwhalecloud.retail.order2b.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

@Aspect
@Component
public class MapperKeySeqAop {

    @Autowired
    private WhaleCloudKeyGenerator whaleCloudKeyGenerator;

    @Around("execution(* com.iwhalecloud.retail.order2b.mapper.*.*(..)) ")
    public Object builderKeySeq(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        WhaleCloudDBKeySequence annotationPresent = method.getAnnotation(WhaleCloudDBKeySequence.class);
        if (annotationPresent == null) {
            return point.proceed();
        }

        Object prams = point.getArgs()[0];
        int i=0;
        if (prams instanceof List && !CollectionUtils.isEmpty((Collection<?>) prams)) {
            List list = (List) prams;
            for (Object object : list) {
                if (!updateKeySeq(object,String.valueOf(++i))) {
                    break;
                }
            }
        } else {
            updateKeySeq(prams,String.valueOf(i));
        }
        return point.proceed();
    }

    private boolean updateKeySeq(Object o,String fla) throws Exception {
        WhaleCloudDBKeySequence paramsConfig = o.getClass().getAnnotation(WhaleCloudDBKeySequence.class);
        if (paramsConfig == null || StringUtils.isEmpty(paramsConfig.keySeqName())) {
            return false;
        }
        Field field = o.getClass().getDeclaredField(paramsConfig.keySeqName());
        field.setAccessible(true);
        DBTableSequence dbTableSequence=paramsConfig.keySeqValue();
        String val = whaleCloudKeyGenerator.tableKeySeq(dbTableSequence.getCode()).concat(fla);
        field.set(o, val);
        field.setAccessible(false);
        return true;
    }

}
