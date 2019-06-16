package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.MerchantTagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.entity.MerchantTagRel;
import com.iwhalecloud.retail.goods2b.manager.MerchantTagRelManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.MerchantTagRelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class MerchantTagRelServiceImpl implements MerchantTagRelService {

    @Autowired
    private MerchantTagRelManager merchantTagRelManager;

    /**
     * 添加一个 店中商(分销商)和标签 关联关系
     *
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> saveMerchantTagRel(MerchantTagRelSaveReq req) {
        log.info("MerchantTagRelServiceImpl.saveMerchantTagRel(), input: MerchantTagRelSaveReq={} ", JSON.toJSONString(req));
        int resultInt = 0;
        if (!CollectionUtils.isEmpty(req.getTagIdList())) {
            // 批量插入
            for (String tagId : req.getTagIdList()) {
                MerchantTagRel merchantTagRel = new MerchantTagRel();
                BeanUtils.copyProperties(req, merchantTagRel);
                merchantTagRel.setTagId(tagId);
                resultInt = resultInt + merchantTagRelManager.insert(merchantTagRel);
            }
//        } else if (!StringUtils.isEmpty(req.getTagId())){
//            MerchantTagRel merchantTagRel = new MerchantTagRel();
//            BeanUtils.copyProperties(req, merchantTagRel);
//            resultInt =  merchantTagRelManager.insert(merchantTagRel);
        }
        log.info("MerchantTagRelServiceImpl.saveMerchantTagRel(), output: insert effect rows={} ", resultInt);
        if (resultInt <= 0) {
            return ResultVO.error("新增商家标签关联关系信息失败，，请确认参数");
        }
        return ResultVO.success(resultInt);
    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO<Integer> saveMerchantTagRelBatch(MerchantTagRelSaveBatchReq req) {
        log.info("MerchantTagRelServiceImpl.saveMerchantTagRelBatch(), input: MerchantTagRelSaveReq={} ", JSON.toJSONString(req));
        final int[] resultInt = {0};
        if (!CollectionUtils.isEmpty(req.getMerchantIdList())) {
            if (!CollectionUtils.isEmpty(req.getTagIdList())) {
                req.getMerchantIdList().forEach(item -> {
                    for (String tagId : req.getTagIdList()) {
                        MerchantTagRel merchantTagRel = new MerchantTagRel();
                        BeanUtils.copyProperties(req, merchantTagRel);
                        merchantTagRel.setTagId(tagId);
                        merchantTagRel.setMerchantId(item);
                        resultInt[0] += merchantTagRelManager.insert(merchantTagRel);
                    }
                });
            }
        }
        log.info("MerchantTagRelServiceImpl.saveMerchantTagRelBatch(), output: insert effect rows={} ", resultInt[0]);
        if (resultInt[0] <= 0) {
            return ResultVO.error("批量新增商家标签关联关系信息失败，请确认参数");
        }
        return ResultVO.success(resultInt[0]);
    }

    /**
     * 获取一个 店中商(分销商)和标签 关联关系
     *
     * @param req
     * @return
     */
    @Override
    public ResultVO<MerchantTagRelDTO> getMerchantTagRelById(MerchantTagRelQueryReq req) {
        log.info("MerchantTagRelServiceImpl.getMerchantTagRelById(), 入参relId={} ", req.getRelId());
        MerchantTagRelDTO merchantTagRelDTO = merchantTagRelManager.getMerchantTagRelById(req.getRelId());
        log.info("MerchantTagRelServiceImpl.getMerchantTagRelById(), 出参manufacturerDTO={} ", merchantTagRelDTO);
        return ResultVO.success(merchantTagRelDTO);
    }


    /**
     * 删除 店中商(分销商)和标签 关联关系
     *
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> deleteMerchantTagRel(MerchantTagRelDeleteReq req) {
        log.info("MerchantTagRelServiceImpl.deleteMerchantTagRel(), 入参PartnerTagRelDeleteReq={} ", req);
        int result = merchantTagRelManager.deleteMerchantTagRel(req);
        log.info("MerchantTagRelServiceImpl.deleteMerchantTagRel(), 出参对象(删除影响数据条数）={} ", result);
//        if (result <= 0){
//            return ResultVO.error("删除店中商(分销商)和标签 关联关系失败");
//        }
        return ResultVO.success("删除店中商(分销商)和标签 关联关系的条数：" + result, result);
    }

    @Override
    public ResultVO<Integer> deleteMerchantTagRelBatch(MerchantTagRelDeleteBatchReq req) {
        log.info("MerchantTagRelServiceImpl.deleteMerchantTagRelBatch(), 入参PartnerTagRelDeleteReq={} ", req);
        int result = merchantTagRelManager.deleteMerchantTagRelBatch(req);
        log.info("MerchantTagRelServiceImpl.deleteMerchantTagRelBatch(), 出参对象(删除影响数据条数）={} ", result);
        return ResultVO.success("批量删除店中商(分销商)和标签 关联关系的条数：" + result, result);
    }

    /**
     * 店中商(分销商)和标签 关联关系 信息 列表查询
     *
     * @param req
     * @return
     */
    @Override
    public ResultVO<List<MerchantTagRelDTO>> listMerchantTagRel(MerchantTagRelListReq req) {
        log.info("MerchantTagRelServiceImpl.listMerchantTagRel(), 入参PartnerTagRelListReq={} ", req);
        List<MerchantTagRelDTO> list = merchantTagRelManager.listMerchantTagRel(req);
        log.info("MerchantTagRelServiceImpl.listMerchantTagRel(), 出参list={} ", list);
        return ResultVO.success(list);
    }


    @Override
    public ResultVO<List<MerchantTagRelDTO>> listMerchantAndTag(MerchantTagRelListReq req){
        return ResultVO.success(merchantTagRelManager.listMerchantAndTag(req));
    }
}