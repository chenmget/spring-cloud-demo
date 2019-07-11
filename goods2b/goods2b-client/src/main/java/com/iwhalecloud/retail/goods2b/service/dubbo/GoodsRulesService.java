package com.iwhalecloud.retail.goods2b.service.dubbo;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulesDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulesProductDTO;
import com.iwhalecloud.retail.goods2b.dto.req.CheckRuleReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProdGoodsRuleByExcelFileReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProdGoodsRuleEditReq;
import com.iwhalecloud.retail.goods2b.dto.req.QueryProductObjReq;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsRulesExcelResp;

import java.util.List;

public interface GoodsRulesService {

    ResultVO<GoodsRulesExcelResp> addProdGoodsRuleByExcelFile(ProdGoodsRuleByExcelFileReq prodGoodsRuleByExcelFileReq) throws Exception;

    ResultVO<GoodsRulesExcelResp> addProdGoodsRuleBatch(ProdGoodsRuleEditReq prodGoodsRuleEditReq);

    ResultVO addProdGoodsRule(GoodsRulesDTO entity);

    ResultVO deleteProdGoodsRuleBatch(ProdGoodsRuleEditReq prodGoodsRuleEditReq);

    ResultVO deleteProdGoodsRule(ProdGoodsRuleEditReq prodGoodsRuleEditReq);

    ResultVO deleteProdGoodsRuleByCondition(GoodsRulesDTO condition);

    ResultVO updateProdGoodsRuleByCondition(GoodsRulesDTO condition);

    ResultVO updateProdGoodsRuleById(GoodsRulesDTO dto);

    ResultVO<GoodsRulesDTO> queryProdGoodsRuleById(ProdGoodsRuleEditReq prodGoodsRuleEditReq);

    ResultVO<List<GoodsRulesProductDTO>> queryProdGoodsRuleByCondition(GoodsRulesDTO condition);

    /**
     * 购买时的分货规则校验
     * @param prodGoodsRuleEditReq
     * @return
     */
    ResultVO<Boolean> checkGoodsProdRule(ProdGoodsRuleEditReq prodGoodsRuleEditReq);

    /**
     * 分货规则业务操作，用于实扣和逻辑回滚。将会根据传入的商家ID以及商家归属的经营主体修改提货数量
     * <br> 前提条件：商品必须是带有分货规则（PROD_GOODS.IS_ALLOT等于1）
     * @param prodGoodsRuleEditReq
     * @return
     */
    ResultVO<Boolean> raiseMarketNumDiffer(ProdGoodsRuleEditReq prodGoodsRuleEditReq);

    ResultVO deleteGoodsRuleByGoodsId(ProdGoodsRuleEditReq prodGoodsRuleEditReq);

    /**
     * 校验分货规则配置对象的经营主体和零售商是否存在交叉记录，比如配置了经营主体又同时配置了经营主体下的零售商
     * @param prodGoodsRuleEditReq
     * @return
     */
    ResultVO<Boolean> overlappingRecordsValid(ProdGoodsRuleEditReq prodGoodsRuleEditReq);

    ResultVO getGoodsRulesByExcel(ProdGoodsRuleByExcelFileReq prodGoodsRuleByExcelFileReq) throws Exception;

    /**
     * 添加商品，规则校验
     * @param req
     * @return
     */
    ResultVO checkGoodsRules(CheckRuleReq req);

    /**
     * 校验分货对象
     * @param req
     * @return list
     */
    ResultVO<List<GoodsRulesProductDTO>> queryProductObj(QueryProductObjReq req);

    /**
     * 校验分货对象是否有经营权限
     * @param  req
     * @return  aa
     */
    ResultVO checkObj(CheckRuleReq req);
}
