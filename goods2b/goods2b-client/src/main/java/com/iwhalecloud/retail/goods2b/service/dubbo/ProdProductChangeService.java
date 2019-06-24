package com.iwhalecloud.retail.goods2b.service.dubbo;

import com.iwhalecloud.retail.goods2b.dto.ProdProductChangeDTO;

/**
 * Created by Administrator on 2019/5/14.
 */
public interface ProdProductChangeService {

    String insert(ProdProductChangeDTO prodProductChangeDTO);

    Integer delete(ProdProductChangeDTO prodProductChangeDTO);

    String getVerNumByProductBaseId(String productBaseId);

    Boolean getProductChange(String productBaseId);
}
