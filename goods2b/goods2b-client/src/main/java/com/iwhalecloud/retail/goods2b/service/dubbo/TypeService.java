package com.iwhalecloud.retail.goods2b.service.dubbo;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.TypeDTO;
import com.iwhalecloud.retail.goods2b.dto.req.TypeDeleteByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.TypeIsUsedQueryByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.TypeListByNameReq;
import com.iwhalecloud.retail.goods2b.dto.req.TypeSelectByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.TypeDetailResp;

import java.util.List;

public interface TypeService {

    public ResultVO saveType(TypeDTO prodTypeDTO);

    public ResultVO updateType(TypeDTO prodTypeDTO);

    public ResultVO deleteType(TypeDeleteByIdReq req);

    public ResultVO listType(TypeListByNameReq req);

    public ResultVO selectById(TypeSelectByIdReq req);

    public ResultVO<List<TypeDTO>> selectAll();

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
}