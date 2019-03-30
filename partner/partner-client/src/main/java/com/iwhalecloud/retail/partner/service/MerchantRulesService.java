package com.iwhalecloud.retail.partner.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantRulesDTO;
import com.iwhalecloud.retail.partner.dto.MerchantRulesDetailDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.dto.resp.MerchantRulesDetailPageResp;
import com.iwhalecloud.retail.partner.dto.resp.TransferPermissionGetResp;

import java.util.List;

public interface MerchantRulesService {

    /**
     * 添加一个或一组 商家 权限规则
     * @param req
     * @return
     */
    ResultVO<Integer> saveMerchantRules(MerchantRulesSaveReq req);

    /**
     * 获取一个 商家 权限规则
     * @param merchantRulesId
     * @return
     */
    ResultVO<MerchantRulesDTO> getMerchantRulesById(String merchantRulesId);

    /**
     * 更新 商家 权限规则
     * @param req
     * @return
     */
//    ResultVO<Integer> updateMerchantRules(MerchantRulesUpdateReq req);

    /**
     * 删除 商家 权限规则
     * @param req
     * @return
     */
    ResultVO<Integer> deleteMerchantRules(MerchantRulesDeleteReq req);

    /**
     * 商家 权限规则 信息 列表查询
     * @param req
     * @return
     */
    ResultVO<List<MerchantRulesDTO>> listMerchantRules(MerchantRulesListReq req);

    /**
     * 商家 权限规则详情 信息 列表查询
     * @param req
     * @return
     */
    ResultVO<List<MerchantRulesDetailDTO>> listMerchantRulesDetail(MerchantRulesDetailListReq req);

    /**
     * 商家 权限规则详情 信息 分页查询
     * @param req
     * @return
     */
    ResultVO<Page<MerchantRulesDetailPageResp>> pageMerchantRules(MerchantRulesDetailPageReq req);


    /**
     * 获取调拨权限
     * @param merchantId
     * @return
     */
    ResultVO<TransferPermissionGetResp> getTransferPermission(String merchantId);

    /**
     * 获取经营权限--机型
     * @param merchantId
     * @return
     */
    ResultVO<List<String>> getBusinessModelPermission(String merchantId);


    /**
     * 获取绿色通道权限--机型
     * @param merchantId
     * @return
     */
    ResultVO<List<String>> getGreenChannelPermission(String merchantId);


    /**
     * 通过查询对象获取targetId的集合
     * @param req
     * @return
     */
    ResultVO<List<String>> getCommonPermission(MerchantRulesCommonReq req);

    /**
     * 通过merchantId查询商家品牌和机型权限集合
     * @param merchantId
     * @return 机型id集合
     */
    ResultVO<List<String>> getProductAndBrandPermission(String merchantId);

    /**
     * 通过merchantId查询商家区域和对象权限集合
     * @param req
     * @return 机型id集合
     */
    ResultVO<List<String>> getRegionAndMerchantPermission(MerchantRulesCommonReq req);
    
    /**
     * 批量添加 商家 权限规则
     * @param req
     * @return
     */
    ResultVO<Integer> saveExcelMerchantRules(List<MerchantRuleSaveReq> req);

    /**
     * 商家权限规则校验
     *
     * @param req 规则校验入参
     * @return ResultVO
     */
    ResultVO<String> checkMerchantRules(MerchantRulesCheckReq req);

}