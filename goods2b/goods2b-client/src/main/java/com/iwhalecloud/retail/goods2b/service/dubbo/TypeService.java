package com.iwhalecloud.retail.goods2b.service.dubbo;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ProdCRMTypeDto;
import com.iwhalecloud.retail.goods2b.dto.TypeDTO;
import com.iwhalecloud.retail.goods2b.dto.req.TypeDeleteByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.TypeIsUsedQueryByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.TypeListByNameReq;
import com.iwhalecloud.retail.goods2b.dto.req.TypeSelectByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.TypeDetailResp;
import com.iwhalecloud.retail.goods2b.dto.resp.TypeResp;

import java.util.List;

public interface TypeService {

    ResultVO saveType(TypeDTO prodTypeDTO);

    ResultVO updateType(TypeDTO prodTypeDTO);

    ResultVO deleteType(TypeDeleteByIdReq req);

    ResultVO listType(TypeListByNameReq req);

    ResultVO<TypeDTO> selectById(TypeSelectByIdReq req);

    ResultVO<List<TypeDTO>> selectAll();

    public ResultVO<List<TypeDTO>> selectSubTypeById(TypeSelectByIdReq req);

    /**
     * 校验类型是否关联产品
     * @param typeIsUsedQueryByIdReq
     * @return
     */
    public ResultVO<Boolean> typeIsUsed(TypeIsUsedQueryByIdReq typeIsUsedQueryByIdReq);

    /**
     * 查询产品类型详细分类
     * @param
     * @return
     */
    ResultVO<TypeDetailResp> getDetailType(TypeSelectByIdReq req);

    ResultVO<TypeResp> selectById(String typeId);

    /**
     *
     */
    ResultVO<List<ProdCRMTypeDto>> getCrmTypeName(String crmKindId);
}