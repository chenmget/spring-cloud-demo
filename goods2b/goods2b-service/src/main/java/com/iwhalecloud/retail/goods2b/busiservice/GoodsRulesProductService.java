package com.iwhalecloud.retail.goods2b.busiservice;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.common.GoodsRulesConst;
import com.iwhalecloud.retail.goods2b.dto.GoodsProductRelDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulesDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulesProductDTO;
import com.iwhalecloud.retail.goods2b.dto.req.QueryProductObjReq;
import com.iwhalecloud.retail.goods2b.entity.Product;
import com.iwhalecloud.retail.goods2b.manager.ProductManager;
import com.iwhalecloud.retail.goods2b.mapper.GoodsProductRelMapper;
import com.iwhalecloud.retail.goods2b.model.CheckRuleModel;
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


    public ResultVO addGoodsCheckRuleGoods(CheckRuleModel model) {
        log.info("gs_10010_addGoodsCheckRuleGoods,req{}", JSON.toJSONString(model));
        ResultVO resultVO;
        if (model.getDisProductType() == null) {
            model.setDisProductType(GoodsConst.DIS_PRODUCT_TYPE_2);
        }

        switch (model.getDisProductType()) {
            //按商品校验
            case GoodsConst.DIS_PRODUCT_TYPE_1:
                resultVO = checkRuleByGoods(model);
                break;
            //默认按规格校验
            default:
                resultVO = goodsRulesService.checkGoodsRules(
                        model.getEntityList(),
                        model.getGoodsProductRelList(),
                        model.getSupplierId());
                break;
        }
        return resultVO;
    }

    /**
     * 按商品校验
     */
    public ResultVO checkRuleByGoods(CheckRuleModel model) {

        if (CollectionUtils.isEmpty(model.getEntityList())) {
            //每个产品规格必须有一条分货规则
            return ResultVO.error("每个产品规格必须有一条分货规则");
        }

        Map<String, Long> map = new HashMap<>();
        List<String> targetList = new ArrayList<>();
        Long totalMarket = 0L;
        for (int i = 0; i < model.getEntityList().size(); i++) {
            GoodsRulesDTO goodsRulesDTO = model.getEntityList().get(i);
            Long purchasedNum = goodsRulesDTO.getPurchasedNum() == null ? 0 : goodsRulesDTO.getPurchasedNum();
            if (GoodsRulesConst.Stockist.PARTNER_IN_SHOP_TYPE.getValue().equals(goodsRulesDTO.getTargetType())) {
                targetList.add(goodsRulesDTO.getTargetId());
            }
            if (goodsRulesDTO.getMarketNum() <= 0) {
                //分货数量大于0
                return ResultVO.error("分货数量必须大于0");
            } else if (goodsRulesDTO.getMarketNum() < purchasedNum) {
                //分货数量大于等于已提货数量
                return ResultVO.error("分货数量必须大于等于已提货数量");
            }
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

        MerchantRulesCheckReq merchantRulesCheckReq = new MerchantRulesCheckReq();
        merchantRulesCheckReq.setSourceMerchantId(model.getSupplierId());
        merchantRulesCheckReq.setTargetMerchantIds(targetList);
        log.info("校验卖家配置权限     merchantRulesCheckReq= {} ", merchantRulesCheckReq);
        ResultVO merchantResultVO = merchantRulesService.checkMerchantRules(merchantRulesCheckReq);

        if (!merchantResultVO.isSuccess()) {
            //分货规则对象必须是当前供应商有权限的对象
            return ResultVO.error(merchantResultVO.getResultMsg());
        }
        return ResultVO.success();
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
            if (!supplyTargetInfo(entity)) {
                continue;
            }
            Product p = productList.get(0);
            entity.setProductName(p.getUnitName());
            entity.setAssignType(req.getAssignedType());
            entity.setProductBaseId(p.getProductBaseId());
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
            return true;
        }

        return false;
    }
}
