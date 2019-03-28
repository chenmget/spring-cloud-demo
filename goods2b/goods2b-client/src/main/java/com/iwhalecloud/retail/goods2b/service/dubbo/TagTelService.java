package com.iwhalecloud.retail.goods2b.service.dubbo;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.TagTelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;

import java.util.List;

public interface TagTelService {

    /**
     * 根据ID查询标签产品关系
     * @param tagTelGetByIdReq
     * @return
     */
    ResultVO<TagTelDTO> getTagTel(TagTelGetByIdReq tagTelGetByIdReq);

    /**
     * 获取所有有效品牌列表
     * @return
     */
    ResultVO<List<TagTelDTO>> listAll();

    /**
     * 添加标签产品关系
     * @param req
     * @return
     */
    ResultVO<Integer> addTagTel(TagTelAddReq req);

    /**
     * 批量添加标签产品关系
     * @param req
     * @return
     */
    ResultVO<Boolean> batchAddTagTel(TagTelBatchAddReq req);

    /**
     * 修改标签产品关系
     * @param req
     * @return
     */
    ResultVO<Integer> updateTagTel(TagTelUpdateReq req);

    /**
     * 删除标签产品关系
     * @param req
     * @return
     */
    ResultVO<Integer> deleteTagTel(TagTelDeleteByIdReq req);


    /**
     * 根据产品ID删除标签产品关系
     * @param req
     * @return
     */
    ResultVO<Boolean> deleteTagTelByProductId(TagTelDeleteByProductIdReq req);

    /**
     * 根据产品ID查询标签
     * @return
     */
    ResultVO<List<TagTelDTO>> listTagByProductId(TagTelListByProductIdReq req);
}