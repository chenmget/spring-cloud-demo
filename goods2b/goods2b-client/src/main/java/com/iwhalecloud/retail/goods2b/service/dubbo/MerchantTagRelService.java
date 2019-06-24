package com.iwhalecloud.retail.goods2b.service.dubbo;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.MerchantTagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;

import java.util.List;

public interface MerchantTagRelService {


    /**
     * 添加一个 店中商(分销商)和标签 关联关系
     *
     * @param req
     * @return
     */
    ResultVO<Integer> saveMerchantTagRel(MerchantTagRelSaveReq req);

    /**
     * 批量添加店中商(分销商)和标签 关联关系
     *
     * @param req
     * @return
     */
    ResultVO<Integer> saveMerchantTagRelBatch(MerchantTagRelSaveBatchReq req);

    /**
     * 获取一个 店中商(分销商)和标签 关联关系
     *
     * @param req
     * @return
     */
    ResultVO<MerchantTagRelDTO> getMerchantTagRelById(MerchantTagRelQueryReq req);


    /**
     * 删除 店中商(分销商)和标签 关联关系
     *
     * @param req
     * @return
     */
    ResultVO<Integer> deleteMerchantTagRel(MerchantTagRelDeleteReq req);

    /**
     * 批量删除店中商(分销商)和标签 关联关系
     *
     * @param req
     * @return
     */
    ResultVO<Integer> deleteMerchantTagRelBatch(MerchantTagRelDeleteBatchReq req);

    /**
     * 店中商(分销商)和标签 关联关系 信息 列表查询
     *
     * @param req
     * @return
     */
    ResultVO<List<MerchantTagRelDTO>> listMerchantTagRel(MerchantTagRelListReq req);

    /**
     * 查询商家和标签的集合
     * @param req
     * @return
     */
    ResultVO<List<MerchantTagRelDTO>> listMerchantAndTag(MerchantTagRelListReq req);

}