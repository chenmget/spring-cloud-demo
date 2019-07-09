package com.iwhalecloud.retail.goods2b.busiservice;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.model.CheckRuleModel;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsRulesService;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/****
 * @author gs_10010
 * @date 2019/7/9 20:55
 */
@Slf4j
@Component
public class GoodsRulesProductService {

    @Reference
    private GoodsRulesService goodsRulesService;

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

        return ResultVO.success();
    }

}
