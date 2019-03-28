package com.iwhalecloud.retail.order2b.config;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.SourceFromContext;
import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Slf4j
public class Order2bContext implements Serializable {

    private static final long serialVersionUID = -6634783591580517814L;

    public static final String DB_LAN_ID_NAME="lanId";
    public static final String DB_SOURCE_FROM="sourceFrom";

    private static ThreadLocal<OrderRequest> dubboRequest = new ThreadLocal<>();


    public static void setDBLanId(Object req) {
        OrderRequest request = new OrderRequest();
        if(req!=null){
            BeanUtils.copyProperties(req,request);
        }
        request.setHttpId(System.currentTimeMillis());
        request.setSourceFrom(SourceFromContext.getSourceFrom());
        log.debug("gs_10010_setDBLanId,req{}", JSON.toJSONString(request));
        dubboRequest.set(request);
    }

    public static OrderRequest getDubboRequest() {
        if(dubboRequest.get()==null){
            setDBLanId(new Object());
        }
        return dubboRequest.get();
    }

    public static void remove() {
        dubboRequest.remove();
    }


}
