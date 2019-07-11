package com.iwhalecloud.retail.goods2b.busiservice;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsRulesConst;
import com.iwhalecloud.retail.goods2b.dto.GoodsProductRelDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulesDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulesProductDTO;
import com.iwhalecloud.retail.goods2b.dto.req.CheckRuleReq;
import com.iwhalecloud.retail.goods2b.dto.req.QueryProductObjReq;
import com.iwhalecloud.retail.goods2b.entity.Product;
import com.iwhalecloud.retail.goods2b.manager.ProductManager;
import com.iwhalecloud.retail.goods2b.mapper.GoodsProductRelMapper;
import com.iwhalecloud.retail.goods2b.reference.BusinessEntityReference;
import com.iwhalecloud.retail.goods2b.reference.MerchantReference;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsRulesService;
import com.iwhalecloud.retail.partner.dto.BusinessEntityDTO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantRulesCheckReq;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/****
 * @author gs_10010
 * @date 2019/7/9 20:55
 */
@Slf4j
@Component
public class GoodsRulesProductService {

    @Reference
    private GoodsRulesService goodsRulesService;

    @Reference
    private MerchantRulesService merchantRulesService;

    @Autowired
    private BusinessEntityReference businessEntityReference;
    @Autowired
    private MerchantReference merchantReference;
    @Autowired
    private ProductManager productManager;

    @Autowired
    private GoodsProductRelMapper goodsProductRelMapper;


    /**
     * 按规格校验
     *
     * @param model
     * @return
     */
    public ResultVO checkRuleByProduct(CheckRuleReq model) {
        Map<String, Long> map = new HashMap<>();
        List<String> targetList = new ArrayList<>();

        ResultVO resultVO = getObjIdsByGoodsRules(model);
        if (!resultVO.isSuccess()) {
            return resultVO;
        }
        for (int i = 0; i < model.getEntityList().size(); i++) {
            GoodsRulesDTO goodsRulesDTO = model.getEntityList().get(i);
            //把分货数量按产品分类放进map
            Iterator keys = map.keySet().iterator();
            Boolean keyFlag = false;
            while (keys.hasNext()) {
                if (keys.next().equals(goodsRulesDTO.getProductId())) {
                    keyFlag = true;
                    break;
                }
            }
            if (keyFlag) {
                //当前产品已经在map
                Long marketNum = goodsRulesDTO.getMarketNum() + map.get(goodsRulesDTO.getProductId());
                map.put(goodsRulesDTO.getProductId(), marketNum);
            } else {
                //当前产品不在map
                map.put(goodsRulesDTO.getProductId(), goodsRulesDTO.getMarketNum());
            }
        }

        for (int i = 0; i < model.getGoodsProductRelList().size(); i++) {
            Boolean rulesNotEmpty = false;
            GoodsProductRelDTO goodsProductRelDTO = model.getGoodsProductRelList().get(i);
            Iterator keys = map.keySet().iterator();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (key.equals(goodsProductRelDTO.getProductId())) {
                    rulesNotEmpty = true;
                    if (map.get(key) > goodsProductRelDTO.getSupplyNum()) {
                        //每个产品规格的所有分货规则分货数量的和，必须小于等于该产品规格的库存
                        return ResultVO.error("每个产品规格的所有分货规则分货数量的和，必须小于等于该产品规格的库存");
                    }
                }
            }
            if (!rulesNotEmpty) {
                //每个产品规格必须有一条分货规则
                return ResultVO.error("每个产品规格必须有一条分货规则");
            }
        }

       if(CollectionUtils.isEmpty(model.getTargetList())){
            return ResultVO.success();
       }

       return checkMerchant(model.getSupplierId(),model.getTargetList());
    }

    /**
     * 校验分货数量，分货对象类型
     *
     * @param model
     * @return
     */
    private ResultVO getObjIdsByGoodsRules(CheckRuleReq model) {
        model.setTargetList(new ArrayList<>());
        for (int i = 0; i < model.getEntityList().size(); i++) {
            GoodsRulesDTO goodsRulesDTO = model.getEntityList().get(i);
            Long purchasedNum = goodsRulesDTO.getPurchasedNum() == null ? 0 : goodsRulesDTO.getPurchasedNum();
            //地包，店中商 需要校验
            if (GoodsRulesConst.Stockist.PARTNER_IN_SHOP_TYPE.getValue().equals(goodsRulesDTO.getTargetType()) ||
                    GoodsRulesConst.Stockist.DB_CODE.getValue().equals(goodsRulesDTO.getTargetType())) {
                model.getTargetList().add(goodsRulesDTO.getTargetId());
            }

            if (goodsRulesDTO.getMarketNum() <= 0) {
                //分货数量大于0
                return ResultVO.error("分货数量必须大于0");
            } else if (goodsRulesDTO.getMarketNum() < purchasedNum) {
                //分货数量大于等于已提货数量
                return ResultVO.error("分货数量必须大于等于已提货数量");
            }
        }
        return ResultVO.success();
    }

    /**
     * 按商品校验
     */
    public ResultVO checkRuleByGoods(CheckRuleReq model) {
        ResultVO resultVO = getObjIdsByGoodsRules(model);
        if (!resultVO.isSuccess()) {
            return resultVO;
        }

        Long totalMarket = 0L;
        for (int i = 0; i < model.getEntityList().size(); i++) {
            GoodsRulesDTO goodsRulesDTO = model.getEntityList().get(i);
            totalMarket = totalMarket + goodsRulesDTO.getMarketNum();
        }

        Long totalSupplyNum = 0L;
        for (int i = 0; i < model.getGoodsProductRelList().size(); i++) {
            Boolean rulesNotEmpty = false;
            GoodsProductRelDTO goodsProductRelDTO = model.getGoodsProductRelList().get(i);
            totalSupplyNum = totalSupplyNum + goodsProductRelDTO.getSupplyNum();
        }
        if (totalMarket > totalSupplyNum) {
            //每个产品规格的所有分货规则分货数量的和，必须小于等于该产品规格的库存
            return ResultVO.error("每个产品规格的所有分货规则分货数量的和，必须小于等于该产品规格的库存");
        }

        if(CollectionUtils.isEmpty(model.getTargetList())){
            return ResultVO.success();
        }

        return checkMerchant(model.getSupplierId(),model.getTargetList());
    }


    public List<GoodsRulesProductDTO> queryProductObj(QueryProductObjReq req) {
        List<GoodsRulesProductDTO> passGoodsLsit = new ArrayList<>();
        List<Product> productList = new ArrayList<>();
        //按productBaseId 查询产品
        if (!StringUtils.isEmpty(req.getProductBaseId())) {
            productList = productManager.list(new LambdaQueryWrapper<Product>()
                    .eq(Product::getProductBaseId, req.getProductBaseId()));
            //按productIds查询产品
        } else if (!CollectionUtils.isEmpty(req.getProductIds())) {
            productList = productManager.list(new LambdaQueryWrapper<Product>()
                    .in(Product::getProductId, req.getProductIds()));
        }

        if (CollectionUtils.isEmpty(productList)) {
            return passGoodsLsit;
        }
        for (GoodsRulesDTO entity : req.getDtoList()) {

            Product p = productList.get(0);
            entity.setProductName(p.getUnitName());
            entity.setAssignType(req.getAssignedType());
            entity.setProductBaseId(p.getProductBaseId());
            boolean b = supplyTargetInfo(entity);
            log.info("gs_10010_queryProductObj,supplyTargetInfo_entity{},b{}", JSON.toJSONString(entity), b);
            if (!b) {
                continue;
            }
            GoodsRulesProductDTO rulesProductDTO = new GoodsRulesProductDTO();
            BeanUtils.copyProperties(entity, rulesProductDTO);
            if (StringUtils.isEmpty(entity.getProductCode())) {
                passGoodsLsit.add(rulesProductDTO);
                continue;
            }

            for (Product product : productList) {
                if (rulesProductDTO.getProductCode().equals(product.getSn())) {
                    entity.setProductId(product.getProductId());
                    rulesProductDTO.setAttrValue1(product.getAttrValue1());
                    rulesProductDTO.setAttrValue2(product.getAttrValue2());
                    rulesProductDTO.setAttrValue3(product.getAttrValue3());
                    rulesProductDTO.setProductId(product.getProductId());
                    passGoodsLsit.add(rulesProductDTO);
                    break;
                }
            }
            log.info("gs_10010_queryProductObj,product_entity{}", JSON.toJSONString(entity));

        }
        return passGoodsLsit;
    }

    /**
     * 补充目标对象信息
     *
     * @param entity
     */
    public boolean supplyTargetInfo(GoodsRulesDTO entity) {
        String type = entity.getTargetType();
        String code = entity.getTargetCode();
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(code)) {
            return false;
        }
        //经营主体
        if (GoodsRulesConst.Stockist.BUSINESS_ENTITY_TYPE.getValue().equals(type)) {
            BusinessEntityDTO businessEntityDTO = businessEntityReference.getBusinessEntityByCode(code);
            if (businessEntityDTO == null) {
                return false;
            }
            entity.setTargetId(businessEntityDTO.getBusinessEntityId());
            entity.setTargetName(businessEntityDTO.getBusinessEntityName());
            return true;
            //店中商类型编码
        } else if (GoodsRulesConst.Stockist.PARTNER_IN_SHOP_TYPE.getValue().equals(type)) {

            MerchantDTO merchantDTO = merchantReference.getMerchantByCode(code);
            if (merchantDTO == null) {
                return false;
            }
            entity.setTargetId(merchantDTO.getMerchantId());
            entity.setTargetName(merchantDTO.getMerchantName());
            return true;
            //地包商
        } else if (GoodsRulesConst.Stockist.DB_CODE.getValue().equals(type)) {
            MerchantDTO merchantDTO = merchantReference.getMerchantByCode(code);
            if (merchantDTO == null) {
                return false;
            }
            entity.setTargetId(merchantDTO.getMerchantId());
            entity.setTargetName(merchantDTO.getMerchantName());
            return true;
        }

        return false;
    }


    /**
     * 地包，店中商校验商家权限
     */
    public ResultVO checkMerchant(String supplierId, List<String> targetList) {
        MerchantRulesCheckReq merchantRulesCheckReq = new MerchantRulesCheckReq();
        merchantRulesCheckReq.setSourceMerchantId(supplierId);
        merchantRulesCheckReq.setTargetMerchantIds(targetList);
        log.info("校验卖家配置权限     merchantRulesCheckReq= {} ", merchantRulesCheckReq);
        ResultVO merchantResultVO = merchantRulesService.checkMerchantRules(merchantRulesCheckReq);

        if (!merchantResultVO.isSuccess()) {
            //分货规则对象必须是当前供应商有权限的对象
            return ResultVO.error(merchantResultVO.getResultMsg());
        }
        return ResultVO.success();
    }
}
