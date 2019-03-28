package com.iwhalecloud.retail.order2b.interceptor;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.SourceFromContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Activate(
        group = {"provider"},
        order = -100
)
@Slf4j
public class SourceFromProviderFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = null;
        Map<String, String> params = invocation.getAttachments();
        String sourceFrom = params.get("SOURCE_FROM");
        String path = params.get("path");
        log.debug("SourceFromProviderFilter param path={} souceform={}", path, sourceFrom);
        try {
            String sourceFromExists = SourceFromContext.getSourceFrom();

            // 有隐形传递 web -> server
            if (StringUtils.isNotEmpty(sourceFrom)){
                // web -> server first time
                if (StringUtils.isEmpty(sourceFromExists)) {
                    sourceFromExists = sourceFrom;
                    SourceFromContext.setSourceFrom(sourceFromExists);
                }

                // web -> server anther differnt time
                if (StringUtils.isNotEmpty(sourceFromExists) && !sourceFromExists.equals(sourceFrom)) {
                    SourceFromContext.removeSourceFrom();
                    sourceFromExists = sourceFrom;
                    SourceFromContext.setSourceFrom(sourceFromExists);
                }
            }

            result = invoker.invoke(invocation);
        } catch (Exception ex) {
            log.error("SourceFromProviderFilter error: ", ex);
        } finally {
            if (StringUtils.isNotEmpty(sourceFrom)) {
                SourceFromContext.removeSourceFrom();
            }
        }
        return result;
    }
}
