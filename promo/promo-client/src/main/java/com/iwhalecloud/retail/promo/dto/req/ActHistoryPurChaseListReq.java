package com.iwhalecloud.retail.promo.dto.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lhr 2019-03-13 20:31:30
 */
@Data
public class ActHistoryPurChaseListReq extends AbstractPageReq implements Serializable{
    private static final long serialVersionUID = 3315550467298864402L;

    private List<ActHistoryPurChaseAddReq> actHistoryPurChaseAddReqList;

}
