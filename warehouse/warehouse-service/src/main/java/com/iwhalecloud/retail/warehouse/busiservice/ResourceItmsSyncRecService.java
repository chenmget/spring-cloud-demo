package com.iwhalecloud.retail.warehouse.busiservice;

import com.iwhalecloud.retail.warehouse.entity.MktResItmsSyncRec;

import java.util.List;

public interface ResourceItmsSyncRecService {

    Boolean updateByMktResChngEvtDetailId(List<MktResItmsSyncRec> mktResItmsSyncRecList);
}
