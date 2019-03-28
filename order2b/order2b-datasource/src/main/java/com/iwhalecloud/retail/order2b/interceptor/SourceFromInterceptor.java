package com.iwhalecloud.retail.order2b.interceptor;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.config.Order2bContext;
import com.iwhalecloud.retail.order2b.config.TelDBDefValueConfig;
import com.iwhalecloud.retail.order2b.config.WhaleCloudDBKeySequence;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;


@Intercepts
        ({
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
        })
@Slf4j
public class SourceFromInterceptor implements Interceptor {

    @Autowired
    private TelDBDefValueConfig telDBDefValueConfig;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        MappedStatement mappedStatement = (MappedStatement) invocation
                .getArgs()[0];

        Object parameter = invocation.getArgs()[1];

        if(parameter == null){
            return invocation.proceed();
        }

        String methodName = invocation.getMethod().getName();
        if ("query".equals(methodName)) {
            setSelectFragmentField(parameter);
        } else if ("update".equals(methodName)) {

            setUpdateFragmentField(mappedStatement.getId(), parameter);
        }
        Object returnObject = invocation.proceed();
        /**
         * 输出日志
         */
//        outLog(mappedStatement, parameter);
        return returnObject;
    }

    private void setSelectFragmentField(Object parameter) throws IllegalAccessException {

        if (parameter instanceof Map) {
            //遍历map中的值
            Map<String, Object> map = (Map) parameter;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if ("et".equals(entry.getKey())) {
                    setDBParamsValue(entry.getValue(),false);
                    continue;
                }
                if (entry.getValue() instanceof List) {
                    List params = (List) entry.getValue();
                    for (Object obj : params) {
                        setDBParamsValue(obj,true);
                    }
                } else {
                    setDBParamsValue(entry.getValue(),true);
                }
            }
        } else {
            setDBParamsValue(parameter,true);
        }
    }

    private void setUpdateFragmentField(String methodName, Object parameter) throws IllegalAccessException {

        /**
         * 设置value
         */
        setSelectFragmentField(parameter);

        /**
         * 插入
         */
        WhaleCloudDBKeySequence whaleCloudDBKeySequence = getWhaleCloud(methodName);
        if (whaleCloudDBKeySequence != null) {
            telDBDefValueConfig.setTableDefTime(parameter);
        }
    }

    /**
     *
     * @param obj
     * @param isUpdateLanId true 更新 ,false 不更新
     * @throws IllegalAccessException
     */
    private void setDBParamsValue(Object obj, boolean isUpdateLanId) throws IllegalAccessException {

        /**
         * 分页不需要处理
         */
        if (obj == null || obj instanceof Page) {
            return;
        }
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = obj.getClass();
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }

        String lanId = Order2bContext.getDubboRequest().getLanId();
        if (!isUpdateLanId) {
            lanId = null;
        }

        for (Field field : fieldList) {

            switch (field.getName()) {
                //sourceFrom
                case Order2bContext.DB_SOURCE_FROM:
                    field.setAccessible(true);
                    field.set(obj, Order2bContext.getDubboRequest().getSourceFrom());
                    field.setAccessible(false);
                    break;
                //lanId
                case Order2bContext.DB_LAN_ID_NAME:
                    field.setAccessible(true);
                    field.set(obj, lanId);
                    field.setAccessible(false);
                    break;
            }
        }
    }


    @Override
    public Object plugin(Object obj) {
        return Plugin.wrap(obj, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private void outLog(MappedStatement mappedStatement, Object parameterObject) {
        String statementId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = getSql(boundSql, parameterObject, configuration);
        log.info("gs_10010_mapper_httpId={},req={}\n " +
                        "------------------------mapper= {}\n" +
                        "-------------------------- sql= {}", Order2bContext.getDubboRequest().getHttpId(),
                JSON.toJSONString(parameterObject), statementId, sql);
    }

    private String getSql(BoundSql boundSql, Object parameterObject, Configuration configuration) {
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    sql = replacePlaceholder(sql, value);
                }
            }
        }
        return sql;
    }


    private String replacePlaceholder(String sql, Object propertyValue) {
        String result;
        if (propertyValue != null) {
            if (propertyValue instanceof String) {
                result = "'" + propertyValue + "'";
            } else if (propertyValue instanceof Date) {
                result = "'" + DATE_FORMAT.format(propertyValue) + "'";
            } else {
                result = propertyValue.toString();
            }
        } else {
            result = "null";
        }
        return sql.replaceFirst("\\?", Matcher.quoteReplacement(result));
    }

    private WhaleCloudDBKeySequence getWhaleCloud(String interfaceName) {
        WhaleCloudDBKeySequence annotationPresent = null;
        if (interfaceName.contains("!")) {
            interfaceName = interfaceName.substring(0, interfaceName.indexOf("!"));
        }
        String className = interfaceName.substring(0, interfaceName.lastIndexOf("."));

        try {
            Class<?> cla = Class.forName(className);
            Method[] methods = cla.getMethods();
            for (Method method : methods) {
                annotationPresent = method.getAnnotation(WhaleCloudDBKeySequence.class);
                if (annotationPresent != null) {
                    return annotationPresent;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return annotationPresent;
    }
}
