package com.iwhalecloud.retail.goods2b.service.dubbo;

import com.iwhalecloud.retail.goods2b.dto.ProdProductChangeDTO;
import com.iwhalecloud.retail.goods2b.dto.ProdProductChangeDetailDTO;

import java.util.List;

/**
 * Created by Administrator on 2019/5/14.
 */
public interface ProdProductChangeDetailService {

    public String insert(ProdProductChangeDetailDTO prodProductChangeDetailDTO);

    public Boolean update(ProdProductChangeDetailDTO prodProductChangeDetailDTO);

    public Integer batchInsert(ProdProductChangeDTO prodProductChangeDTO);

    public List<ProdProductChangeDetailDTO> getProductChangeDetail(ProdProductChangeDetailDTO prodProductChangeDetailDTO);

}
