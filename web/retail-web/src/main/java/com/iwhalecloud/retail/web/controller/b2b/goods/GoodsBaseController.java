package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.CatDTO;
import com.iwhalecloud.retail.goods2b.dto.req.CatQueryReq;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsForPageQueryReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.web.exception.UserNoMerchantException;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;

/**
 * @author mzl
 * @date 2019/2/26
 */
@Slf4j
public class GoodsBaseController {

    public void setTargetCode(List<String> targetCodeList, Integer userFounder) throws UserNoMerchantException {
        if (userFounder == SystemConst.USER_FOUNDER_3 || userFounder == SystemConst.USER_FOUNDER_5) {
            // 商家信息
            MerchantDTO merchantDTO = UserContext.getUserOtherMsg().getMerchant();
            if (null == merchantDTO) {
                throw new UserNoMerchantException(ResultCodeEnum.ERROR.getCode(), "用户没有关联商家，请确认");
            }
            String merchantCode = merchantDTO.getMerchantCode();
            String businessEntityCode = merchantDTO.getBusinessEntityCode();
            if (!StringUtils.isEmpty(merchantCode)) {
                targetCodeList.add(merchantCode);
            }
            if (!StringUtils.isEmpty(businessEntityCode)) {
                targetCodeList.add(businessEntityCode);
            }
        } else {
            targetCodeList.add("-1");
        }
    }

    public void setReqParam(@RequestParam(value = "pageNo") Integer pageNo, @RequestParam(value = "pageSize")
            Integer pageSize, @RequestParam(value = "ids", required = false) List<String> ids, @RequestParam(value = "catIdList", required = false) List<String> catIdList, @RequestParam(value = "brandIdList", required = false) List<String> brandIdList, @RequestParam(value = "searchKey", required = false) String searchKey, @RequestParam(value = "startPrice", required = false) Long startPrice, @RequestParam(value = "endPrice", required = false) Long endPrice, @RequestParam(value = "isHaveStock", required = false) Integer isHaveStock, GoodsForPageQueryReq req, CatService catService) {
        req.setPageNo(pageNo);
        req.setPageSize(pageSize);
        req.setIds(ids);
        // 查询商品分类的所有子分类
        List<String> allCatIdList = Lists.newArrayList();
        allCatIdList = getAllCatId(catIdList, allCatIdList, catService);
        if (!CollectionUtils.isEmpty(allCatIdList)) {
            req.setCatIdList(allCatIdList);
        }
        req.setBrandIdList(brandIdList);
        req.setStartPrice(startPrice);
        req.setEndPrice(endPrice);
        req.setSearchKey(searchKey);
        req.setIsHaveStock(isHaveStock);
    }

    public List<String> getAllCatId(@RequestParam(value = "catIdList", required = false) List<String> catIdList,
                                     List<String> allCatIdList, CatService catService) {
        if (!CollectionUtils.isEmpty(catIdList)) {
            allCatIdList.addAll(catIdList);
            List<CatQueryReq> catQueryReqList = Lists.newArrayList();
            for (String catId : catIdList) {
                CatQueryReq catQueryReq = new CatQueryReq();
                catQueryReq.setCatId(catId);
                catQueryReqList.add(catQueryReq);
            }
            ResultVO<List<CatDTO>> listResultVO = catService.listProdCatByIds(catQueryReqList);
            if (listResultVO.isSuccess() && !CollectionUtils.isEmpty(listResultVO.getResultData())) {
                setSubCatId(allCatIdList, listResultVO);
            }
            // 去除重复数据
            allCatIdList = removeDuplicate(allCatIdList);
            log.info("GoodsB2BController.getAllCatId catIdList={} allCatIdList={}", JSON.toJSONString(catIdList),
                    JSON.toJSONString(allCatIdList));

        }
        return allCatIdList;
    }

    private void setSubCatId(List<String> allCatIdList, ResultVO<List<CatDTO>> listResultVO) {
        List<CatDTO> catDTOList = listResultVO.getResultData();
        for (CatDTO cat : catDTOList) {
            String catPath = cat.getCatPath();
            if (catPath != null) {
                String[] catPathArr = catPath.split("\\|");
                for (String catPathStr : catPathArr) {
                    if (!GoodsConst.ROOT_DIR.equals(catPathStr) && !StringUtils.isEmpty(catPathStr)) {
                        allCatIdList.add(catPathStr);
                    }
                }
            }
        }
    }

    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }
}
