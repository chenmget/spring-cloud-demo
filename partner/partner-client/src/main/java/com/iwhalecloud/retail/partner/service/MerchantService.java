package com.iwhalecloud.retail.partner.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.MerchantDetailDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.dto.resp.*;
import com.iwhalecloud.retail.system.dto.request.UserFactoryMerchantReq;

import java.util.List;

public interface MerchantService{

    /**
     * 根据条件 查询商家条数
     * @param req
     * @return
     */
    ResultVO<Integer> countMerchant(MerchantCountReq req);

    /**
     * 根据商家id  获取一个 商家 概要信息（字段不够用的话 用getMerchantDetail（）取）
     * @param merchantId
     * @return
     */
    ResultVO<MerchantDTO> getMerchantById(String merchantId);

    /**
     * 根据商家编码  获取一个 商家 概要信息（字段不够用的话 用getMerchantDetail（）取）
     * @param merchantCode
     * @return
     */
    ResultVO<MerchantDTO> getMerchantByCode(String merchantCode);


    /**
     * 获取一个 商家 概要信息（字段不够用的话 用getMerchantDetail（）取）
     * @param req
     * @return
     */
    ResultVO<MerchantDTO> getMerchant(MerchantGetReq req);

    /**
     * 获取一个 商家详情（全表的）
     * @param req
     * @return
     */
    ResultVO<MerchantDetailDTO> getMerchantDetail(MerchantGetReq req);

    /**
     * 编辑 商家 信息
     * @param req
     * @return
     */
    ResultVO<Integer> updateMerchant(MerchantUpdateReq req);

    /**
     * 批量编辑商家信息
     * @param req
     * @return
     */
    ResultVO<Integer> updateMerchantBatch(MerchantUpdateBatchReq req);

    /**
     * 商家 信息列表（只取 部分必要字段）
     * @param req
     * @return
     */
    ResultVO<List<MerchantDTO>> listMerchant(MerchantListReq req);

    /**
     * 商家 信息列表分页
     * @param pageReq
     * @return
     */
    ResultVO<Page<MerchantPageResp>> pageMerchant(MerchantPageReq pageReq);

    /**
     * 商家 绑定 用户
     * @param req
     * @return
     */
    ResultVO<Integer> bindUser(MerchantBindUserReq req) throws Exception;

    /**
     * 零售商类型的 商家 信息列表分页
     * @param pageReq
     * @return
     */
    ResultVO<Page<RetailMerchantDTO>> pageRetailMerchant(RetailMerchantPageReq pageReq);

    /**
     * 供应商类型的 商家 信息列表分页
     * @param pageReq
     * @return
     */
    ResultVO<Page<SupplyMerchantDTO>> pageSupplyMerchant(SupplyMerchantPageReq pageReq);

    /**
     * 厂商类型的 商家 信息列表分页
     * @param pageReq
     * @return
     */
    ResultVO<Page<FactoryMerchantDTO>> pageFactoryMerchant(FactoryMerchantPageReq pageReq);

    /**
     * 根据地市和区县集合查询商家信息
     * @param req
     * @return
     */
    ResultVO<List<String>> listMerchantByLanCity(MerchantListLanCityReq req);

    /**
     * 根据商家id 获取一个 商家 概要信息     *
     * @param merchantId
     * @return
     */
    MerchantDTO getMerchantInfoById(String merchantId);

    /**
     * 商家信息列表（只取部分必要字段）
     * @param req
     * @return
     */
    ResultVO<List<MerchantLigthResp>> listMerchantForOrder(MerchantLigthReq req);

    /**
     * 商家信息列表（只取部分必要字段）
     * @param req
     * @return
     */
    ResultVO<MerchantLigthResp> getMerchantForOrder(MerchantGetReq req);

    /**
     * 获取商家ID列表
     * @param
     * @return
     */
    List<String> getMerchantIdList(String merchantName);


    /**
     * 管理平台新增厂商
     * @param req
     * @return
     */
    ResultVO<String> saveFactoryMerchant(FactoryMerchantSaveReq req);


    /**
     * 注册地包商
     * @param req
     * @return
     */
    ResultVO registLandSupplier(SupplierResistReq req);


    /**
     * 注册省包商
     * @param req
     * @return
     */
    ResultVO registProvinceSupplier(SupplierResistReq req);

    /**
     * 根据商家表字段筛选条件获取商家id列表
     * @param req
     * @return
     */

//    List<String> getMerchantIdListForPayment(MerchantIdListForPaymentReq req);


    /**
     * 地市管理员注册地包商
     * @param req
     * @return
     */
    ResultVO registLandSupplierForAdmin(SupplierResistReq req);

    /**
     * 修改商户信息
     * @param req
     * @return
     */
    ResultVO editMerchant(MerchantEditReq req);
    /**
     * 管理员注册省包商
     * @param req
     * @return
     */
    ResultVO registProvinceSupplierForAdmin(SupplierResistReq req);

    /**
     * 厂商自注册
     * @param req
     * @return
     */
    ResultVO<String>  registerFactoryMerchant(FactoryMerchantSaveReq req);

    /**
     * 获取厂商详情
     * @param merchantId
     * @return
     */
    ResultVO<FactoryMerchantResp> getFactoryMerchant(String merchantId);
    /**
     * 获取供应商信息
     * @param merchantId
     * @return
     */
    ResultVO<SupplierResistResp> getSupplierInfo(String merchantId);
}