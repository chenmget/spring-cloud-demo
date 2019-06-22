package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.common.GoodsRulesConst;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulesDTO;
import com.iwhalecloud.retail.goods2b.entity.GoodsRules;
import com.iwhalecloud.retail.goods2b.mapper.GoodsMapper;
import com.iwhalecloud.retail.goods2b.mapper.GoodsRulesMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class GoodsRulesManager {
    @Resource
    private GoodsRulesMapper goodsRulesMapper;

    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 批量增加或更新
     * @param entityList
     * @return
     */
    public int addOrUpdateBatch(List<GoodsRulesDTO> entityList){
        List<GoodsRules> prodGoodsRuleAddList = new ArrayList<>();
        List<GoodsRules> prodGoodsRuleUpdateList = new ArrayList<>();
        List<GoodsRules> prodGoodsRuleDeleteList = new ArrayList<>();
        Boolean flag;
        String goodsId = entityList.get(0).getGoodsId();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(GoodsRules.FieldNames.goodsId.getTableFieldName(),goodsId);
        List<GoodsRules> resultList = goodsRulesMapper.selectList(queryWrapper);
        for (int i = 0; i < resultList.size(); i++) {
            flag = false;
            GoodsRules goodsRules = resultList.get(i);
            for (int j = 0; j < entityList.size(); j++) {
                GoodsRulesDTO entity = entityList.get(j);
                if(goodsRules.getProductId().equals(entity.getProductId())){
                    if(goodsRules.getTargetCode().equals(entity.getTargetCode())){
                        if(goodsRules.getTargetType().equals(entity.getTargetType())) {
                            GoodsRules entityGoodsRules = new GoodsRules();
                            BeanUtils.copyProperties(entity,entityGoodsRules);
                            entityGoodsRules.setState(GoodsRulesConst.state.EFF.getValue());
                            entityGoodsRules.setGoodsRuleId(goodsRules.getGoodsRuleId());
                            prodGoodsRuleUpdateList.add(entityGoodsRules);
                            flag = true;
                            break;
                        }
                    }
                }
            }
            if(!flag){
                goodsRules.setState(GoodsRulesConst.state.EXP.getValue());
                prodGoodsRuleDeleteList.add(goodsRules);
            }
        }

        for (int k = 0; k < entityList.size(); k++) {
            flag = false;
            GoodsRulesDTO entity = entityList.get(k);
            for (int l = 0; l < resultList.size(); l++) {
                GoodsRules goodsRules = resultList.get(l);
                if(entity.getProductId().equals(goodsRules.getProductId())){
                    if(entity.getTargetCode().equals(goodsRules.getTargetCode())){
                        if(entity.getTargetType().equals(goodsRules.getTargetType())) {
                            flag = true;
                            break;
                        }
                    }
                }
            }
            if(!flag){
                GoodsRules entityGoodsRules = new GoodsRules();
                BeanUtils.copyProperties(entity,entityGoodsRules);
                entityGoodsRules.setState(GoodsRulesConst.state.EFF.getValue());
                prodGoodsRuleAddList.add(entityGoodsRules);
            }
        }
        
//        for (GoodsRulesDTO item : entityList){
//            boolean flag = true;
//            QueryWrapper queryWrapper = genBasicQueryWrapper(item);
//            List<GoodsRules> resultList = goodsRulesMapper.selectList(queryWrapper);
//            if (null!=resultList && resultList.size()>0){
//                GoodsRules goodsRules = resultList.get(0);
//                String id = goodsRules.getGoodsRuleId();
//                item.setGoodsRuleId(id);
//                GoodsRules prodGoodsRule = new GoodsRules();
//                BeanUtils.copyProperties(item,prodGoodsRule);
//                Long marketNum = prodGoodsRule.getMarketNum();
//                Long purchasedNum = goodsRules.getPurchasedNum();
//                purchasedNum = purchasedNum==null?0:purchasedNum;
//                // 提货数量>0则不允许修改
//                if (purchasedNum > 0) {
//                    flag = false;
//                    prodGoodsRule.setPurchasedNum(purchasedNum);
//                }
//                // 分货数量<提货数量则不允许修改
//                if (marketNum < purchasedNum) {
//                    flag = false;
//                    prodGoodsRule.setMarketNum(goodsRules.getMarketNum());
//                }
//                prodGoodsRuleUpdateList.add(prodGoodsRule);
//            } else {
//                GoodsRules prodGoodsRule = new GoodsRules();
//                BeanUtils.copyProperties(item,prodGoodsRule);
//                prodGoodsRule.setState(GoodsConst.StatusCdEnum.STATUS_CD_VALD.getCode());
//                prodGoodsRuleAddList.add(prodGoodsRule);
//            }
//            if (flag) {
//                Goods goods = new Goods();
//                goods.setGoodsId(item.getGoodsId());
//                goods.setIsAllot(GoodsConst.IsAllotEnum.IS_ALLOT.getCode());
//                goodsMapper.updateById(goods);
//            }
//        }
        int addEffRow = prodGoodsRuleAddList.size()>0 ? goodsRulesMapper.insertBatch(prodGoodsRuleAddList) : 0;
        int updateEffRow = prodGoodsRuleUpdateList.size()>0 ? goodsRulesMapper.updateBatch(prodGoodsRuleUpdateList) : 0;
        int deleteEffRow = prodGoodsRuleDeleteList.size()>0 ? goodsRulesMapper.updateBatch(prodGoodsRuleDeleteList) : 0;
        return addEffRow + updateEffRow + deleteEffRow;
    }

    /**
     * 单个增加或更新
     * @param entity
     * @return
     */
    public int addOrUpdateOne(GoodsRulesDTO entity){
        QueryWrapper queryWrapper = genBasicQueryWrapper(entity);
        List<GoodsRules> resultList = goodsRulesMapper.selectList(queryWrapper);
        int effRow;
        if (null!=resultList && resultList.size()>0){
            String id = resultList.get(0).getGoodsRuleId();
            entity.setGoodsRuleId(id);
            effRow = updateById(entity);
        } else {
            GoodsRules prodGoodsRule = new GoodsRules();
            BeanUtils.copyProperties(entity,prodGoodsRule);
            prodGoodsRule.setState(GoodsConst.StatusCdEnum.STATUS_CD_VALD.getCode());
            effRow = goodsRulesMapper.insert(prodGoodsRule);
        }
        return effRow;
    }

    /**
     * 单个删除
     * @param id
     * @return
     */
    public int deleteOne(String id){
        return goodsRulesMapper.deleteById(id);
    }

    /**
     * 根据ID批量删除
     * @param idList
     * @return
     */
    public int deleteBatch(List<String> idList){
        return goodsRulesMapper.deleteBatchIds(idList);
    }

    /**
     * 根据条件删除
     * @param condition
     * @return
     */
    public int deleteByCondition(GoodsRulesDTO condition){
        QueryWrapper queryWrapper = genBasicQueryWrapper(condition);
        return goodsRulesMapper.delete(queryWrapper);
    }

    /**
     * 根据ID更新
     * @param condition
     * @return
     */
    public int updateById(GoodsRulesDTO condition){
        GoodsRules prodGoodsRule = new GoodsRules();
        BeanUtils.copyProperties(condition,prodGoodsRule);
        return goodsRulesMapper.updateById(prodGoodsRule);
    }

    /**
     * 根据条件更新
     * @param condition
     * @return
     */
    public int updateByCondtion(GoodsRulesDTO condition){
        QueryWrapper queryWrapper = genBasicQueryWrapper(condition);
        GoodsRules prodGoodsRule = new GoodsRules();
        BeanUtils.copyProperties(condition,prodGoodsRule);
        return goodsRulesMapper.update(prodGoodsRule, queryWrapper);
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public GoodsRulesDTO queryById(String id){
        GoodsRules entity = goodsRulesMapper.selectById(id);
        GoodsRulesDTO dto = new GoodsRulesDTO();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }

    public List<GoodsRulesDTO> queryGoodsRules(String goodsId,String productId,String targetType,String targetId) {
        GoodsRulesDTO goodsRulesDTO = new GoodsRulesDTO();
        goodsRulesDTO.setGoodsId(goodsId);
        goodsRulesDTO.setProductId(productId);
        goodsRulesDTO.setTargetType(targetType);
        goodsRulesDTO.setTargetId(targetId);

        return this.listByConditon(goodsRulesDTO);
    }

    /**
     * 根据条件查询列表
     * @param condition
     * @return
     */
    public List<GoodsRulesDTO> listByConditon(GoodsRulesDTO condition){
        QueryWrapper queryWrapper = genBasicQueryWrapper(condition);
        queryWrapper.eq(GoodsRules.FieldNames.state.getTableFieldName(),GoodsRulesConst.state.EFF.getValue());
        List<GoodsRules> entityList = goodsRulesMapper.selectList(queryWrapper);
        List<GoodsRulesDTO> dtoList = new ArrayList<>();
        for (GoodsRules entity : entityList){
            GoodsRulesDTO dto = new GoodsRulesDTO();
            BeanUtils.copyProperties(entity,dto);
            dtoList.add(dto);
        }
        return dtoList;
    }

    private QueryWrapper genBasicQueryWrapper(GoodsRulesDTO condition){
        QueryWrapper queryWrapper = new QueryWrapper();
        if (null == condition){
            return queryWrapper;
        }
        if (!StringUtils.isEmpty(condition.getGoodsId())){
            queryWrapper.eq(GoodsRules.FieldNames.goodsId.getTableFieldName(),condition.getGoodsId());
        }
        if (!StringUtils.isEmpty(condition.getProductId())){
            queryWrapper.eq(GoodsRules.FieldNames.productId.getTableFieldName(),condition.getProductId());
        }
        if (!StringUtils.isEmpty(condition.getProductCode())){
            queryWrapper.eq(GoodsRules.FieldNames.productCode.getTableFieldName(),condition.getProductCode());
        }
        if (!StringUtils.isEmpty(condition.getTargetId())){
            queryWrapper.eq(GoodsRules.FieldNames.targetId.getTableFieldName(),condition.getTargetId());
        }
        if (!StringUtils.isEmpty(condition.getTargetCode())){
            queryWrapper.eq(GoodsRules.FieldNames.targetCode.getTableFieldName(),condition.getTargetCode());
        }
        if (!StringUtils.isEmpty(condition.getTargetType())){
            queryWrapper.eq(GoodsRules.FieldNames.targetType.getTableFieldName(),condition.getTargetType());
        }
        return queryWrapper;
    }

    /**
     * 修改提货数量
     * @param goodsId 商品ID
     * @param productId 产品ID
     * @param targetType 目标对象类型,1-经营主体，2-零售商
     * @param targetId 目标ID
     * @param purchasedNum 提货数量
     * @return 修改数量的行数
     */
    public int updatePurchaseNum(String goodsId, String productId, String targetType, String targetId, Long purchasedNum) {

        GoodsRules goodsRules = new GoodsRules();
        goodsRules.setGoodsId(goodsId);
        goodsRules.setProductId(productId);
        goodsRules.setTargetType(targetType);
        goodsRules.setTargetId(targetId);
        goodsRules.setPurchasedNum(purchasedNum);

        return goodsRulesMapper.updatePurchaseNum(goodsRules);
    }

}
