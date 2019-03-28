package com.iwhalecloud.retail.goods2b.service.dubbo;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ComplexInfoDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ComplexInfoQueryReq;
import com.iwhalecloud.retail.goods2b.dto.req.ComplexInfoReq;

import java.util.List;

/**
 * Created by Administrator on 2019/2/27.
 */
public interface ComplexInfoService  {

    /**
     * batchAdd
     * @param req
     * @return
     */
    Boolean batchAddComplexInfo(ComplexInfoReq req);

    /**
     * add
     * @param complexInfoDTO
     * @return
     */
    int insertComplexInfo(ComplexInfoDTO complexInfoDTO);

    /**
     * delete
     * @param complexInfoDTO
     * @return
     */
    int deleteOneComplexInfo(ComplexInfoDTO complexInfoDTO);

    /**
     * delete
     * @param complexInfoDTO
     * @return
     */
    int deleteComplexInfoByGoodsId(ComplexInfoDTO complexInfoDTO);

    /**
     * update
     * @param req
     * @return
     */
    int updateComplexInfo(ComplexInfoDTO req);

    /**
     * query
     * @param complexInfoQueryReq
     * @return
     */
    ResultVO<List<ComplexInfoDTO>> queryComplexInfo(ComplexInfoQueryReq complexInfoQueryReq);


}
