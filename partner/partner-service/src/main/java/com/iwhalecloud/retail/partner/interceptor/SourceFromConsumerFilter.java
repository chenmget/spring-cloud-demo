package com.iwhalecloud.retail.partner.interceptor;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.iwhalecloud.retail.dto.SourceFromContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Activate(
        group = {"consumer"},
        order = -100
)
@Slf4j
public class SourceFromConsumerFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String sourceFromExists = "";
        Map<String, String> params = invocation.getAttachments();
        String path = params.get("path");
        // 继续传递 server -> server
        sourceFromExists = SourceFromContext.getSourceFrom();
        if (StringUtils.isNotEmpty(sourceFromExists)) {
            log.info("SourceFromConsumerFilter param path={} souceform={}", path, sourceFromExists);
            RpcContext.getContext().setAttachment("SOURCE_FROM", sourceFromExists);
        }
        return invoker.invoke(invocation);
    }
}
