package com.iwhalecloud.retail.goods2b.service.dubbo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.CatDTO;
import com.iwhalecloud.retail.goods2b.dto.req.CatAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatQueryReq;
import com.iwhalecloud.retail.goods2b.dto.req.CatUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.resp.CatListResp;
import com.iwhalecloud.retail.goods2b.dto.resp.CatResp;

import java.util.List;


public interface CatService {
    /**
     * 新增产品类别
     * @param req
     * @return
     */
    ResultVO<Boolean> addProdCat(CatAddReq req);

    /**
     * 新增产品类别
     * @param req
     * @return
     */
    ResultVO<String> addProdCatByZT(CatAddReq req);

    /**
     * 修改产品类别
     * @param req
     * @return
     */
    ResultVO<Boolean> updateProdCat(CatUpdateReq req);

    /**
     * 修改产品类别-中台
     * @param req
     * @return
     */
    ResultVO<Boolean> updateProdCatByZT(CatUpdateReq req);

    /**
     * 修改保存排序
     * @param catQueryReq
     * @return
     */
    ResultVO<Boolean> batchUpdateProdCat(CatQueryReq catQueryReq);
    /**
     * 删除产品类别
     * @param catQueryReq
     * @return
     */
    ResultVO<Boolean> deleteProdCat(CatQueryReq catQueryReq);

    /**
     * 查询产品类别列表
     * @return
     */
    ResultVO<IPage> listProdCatByCatName(CatQueryReq catQueryReq);
    /**
     * 查询产品类别
     * @return
     */
    ResultVO<CatResp> queryProdCat(CatQueryReq catQueryReq);

    /**
     * 查询产品类别列表
     * @return
     */
    ResultVO<CatListResp> queryCatList(CatQueryReq catQueryReq);

    /**
     * 查询产品类别
     * @return
     */
    ResultVO<List<CatDTO>> listProdCatByIds(List<CatQueryReq> catQueryReqList);
}