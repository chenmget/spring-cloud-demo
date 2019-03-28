package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods2b.dto.MerchantTagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.MerchantTagRelDeleteBatchReq;
import com.iwhalecloud.retail.goods2b.dto.req.MerchantTagRelDeleteReq;
import com.iwhalecloud.retail.goods2b.dto.req.MerchantTagRelListReq;
import com.iwhalecloud.retail.goods2b.entity.MerchantTagRel;
import com.iwhalecloud.retail.goods2b.mapper.MerchantTagRelMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class MerchantTagRelManager {
    @Resource
    private MerchantTagRelMapper merchantTagRelMapper;

    /**
     * 添加一个 店中商(分销商)和标签 关联关系
     *
     * @param merchantTagRel
     * @return
     */
    public int insert(MerchantTagRel merchantTagRel) {
        int resultInt = merchantTagRelMapper.insert(merchantTagRel);
//        if(resultInt > 0){
//            MerchantTagRelDTO merchantTagRelDTO = new MerchantTagRelDTO();
//            BeanUtils.copyProperties(merchantTagRel, merchantTagRelDTO);
//            return merchantTagRelDTO;
//        }
        return resultInt;
    }

    /**
     * 获取一个 店中商(分销商)和标签 关联关系
     *
     * @param relId
     * @return
     */
    public MerchantTagRelDTO getMerchantTagRelById(String relId) {
        MerchantTagRel merchantTagRel = merchantTagRelMapper.selectById(relId);
        if (merchantTagRel == null) {
            return null;
        }
        MerchantTagRelDTO merchantTagRelDTO = new MerchantTagRelDTO();
        BeanUtils.copyProperties(merchantTagRel, merchantTagRelDTO);
        return merchantTagRelDTO;
    }


    /**
     * 删除 店中商(分销商)和标签 关联关系
     *
     * @param req
     * @return
     */
    public int deleteMerchantTagRel(MerchantTagRelDeleteReq req) {
        QueryWrapper<MerchantTagRel> queryWrapper = new QueryWrapper<MerchantTagRel>();
        Boolean hasParams = false; // 是否有参数
        if (!StringUtils.isEmpty(req.getRelId())) {
            hasParams = true;
            queryWrapper.eq(MerchantTagRel.FieldNames.relId.getTableFieldName(), req.getRelId());
        }
        if (!StringUtils.isEmpty(req.getMerchantId())) {
            hasParams = true;
            queryWrapper.eq(MerchantTagRel.FieldNames.merchantId.getTableFieldName(), req.getMerchantId());
        }
        if (!StringUtils.isEmpty(req.getTagId())) {
            hasParams = true;
            queryWrapper.eq(MerchantTagRel.FieldNames.tagId.getTableFieldName(), req.getTagId());
        }
        // 没有参数 直接返回  不然会删整个表
        if (!hasParams) {
            return 0;
        }
        return merchantTagRelMapper.delete(queryWrapper);
    }

    /**
     * 批量删除店中商(分销商)和标签 关联关系
     *
     * @param req
     * @return
     */
    public int deleteMerchantTagRelBatch(MerchantTagRelDeleteBatchReq req) {
        QueryWrapper<MerchantTagRel> queryWrapper = new QueryWrapper<MerchantTagRel>();
        Boolean hasParams = false; // 是否有参数
        if (!StringUtils.isEmpty(req.getRelId())) {
            hasParams = true;
            queryWrapper.eq(MerchantTagRel.FieldNames.relId.getTableFieldName(), req.getRelId());
        }
        if (CollectionUtils.isNotEmpty(req.getMerchantIdList())) {
            hasParams = true;
            queryWrapper.in(MerchantTagRel.FieldNames.merchantId.getTableFieldName(), req.getMerchantIdList());
        }
        if (!StringUtils.isEmpty(req.getTagId())) {
            hasParams = true;
            queryWrapper.eq(MerchantTagRel.FieldNames.tagId.getTableFieldName(), req.getTagId());
        }
        // 没有参数 直接返回  不然会删整个表
        if (!hasParams) {
            return 0;
        }
        return merchantTagRelMapper.delete(queryWrapper);
    }

    /**
     * 商家和标签 关联关系 信息 列表查询
     *
     * @param req
     * @return
     */
    public List<MerchantTagRelDTO> listMerchantTagRel(MerchantTagRelListReq req) {

        return merchantTagRelMapper.listMerchantTagRel(req);
//        QueryWrapper<MerchantTagRel> queryWrapper = new QueryWrapper<MerchantTagRel>();
//        if(!StringUtils.isEmpty(req.getMerchantId())){
//            queryWrapper.eq(MerchantTagRel.FieldNames.merchantId.getTableFieldName(), req.getMerchantId());
//        }
//        if(!StringUtils.isEmpty(req.getTagId())){
//            queryWrapper.eq(MerchantTagRel.FieldNames.tagId.getTableFieldName(), req.getTagId());
//        }
//        List<MerchantTagRel> merchantTagRelList = merchantTagRelMapper.selectList(queryWrapper);
//        List<MerchantTagRelDTO> merchantTagRelDTOList = new ArrayList<>();
//        for (MerchantTagRel merchantTagRel : merchantTagRelList) {
//            MerchantTagRelDTO merchantTagRelDTO = new MerchantTagRelDTO();
//            BeanUtils.copyProperties(merchantTagRel, merchantTagRelDTO);
//            merchantTagRelDTOList.add(merchantTagRelDTO);
//        }
//        return merchantTagRelDTOList;
    }
}
