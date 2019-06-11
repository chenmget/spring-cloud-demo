package com.iwhalecloud.retail.system.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NoticeMsgReq implements Serializable {


    /**
     * 短信通知模板
     */
    private List templateList;

    private List<BaseZopMsgReq> baseZopMsgReqs;
}
