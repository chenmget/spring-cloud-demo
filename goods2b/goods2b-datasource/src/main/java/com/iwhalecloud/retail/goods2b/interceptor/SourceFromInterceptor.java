package com.iwhalecloud.retail.goods2b.interceptor;

import com.iwhalecloud.retail.dto.SourceFromContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.util.Properties;


@Intercepts({@Signature(
        type= Executor.class,
        method="update",
        args={MappedStatement.class,Object.class})})
public class SourceFromInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation
                .getArgs()[0];
        String sqlId = mappedStatement.getId();
        String namespace = sqlId.substring(0, sqlId.indexOf('.'));
        Executor exe = (Executor) invocation.getTarget();
        String methodName = invocation.getMethod().getName();
        if ("query".equals(methodName)) {

        }
        else if("update".equals(methodName)){
            Object parameter = invocation.getArgs()[1];
            Field[] fields = parameter.getClass().getDeclaredFields();
            for (Field field : fields){
                if ("sourceFrom".equals(field.getName())){
                    field.setAccessible(true);
                    field.set(parameter, SourceFromContext.getSourceFrom());
                    break;
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object obj) {
        return Plugin.wrap(obj, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
