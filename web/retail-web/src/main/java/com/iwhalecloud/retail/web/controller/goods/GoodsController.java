package com.iwhalecloud.retail.web.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods.common.GoodsConst;
import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.AccMbrDTO;
import com.iwhalecloud.retail.goods.dto.ProdGoodsDTO;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.*;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsAddResp;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsDeleteResp;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsDetailResp;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsEditResp;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsService;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.service.ContentBaseService;
import com.iwhalecloud.retail.oms.service.OperatingPositionBindService;
import com.iwhalecloud.retail.partner.dto.SupplierDTO;
import com.iwhalecloud.retail.partner.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mzl
 * @date 2018/11/30
 */
@RestController
@RequestMapping("/api/goods")
@Slf4j
public class GoodsController {

    @Reference
    private ProdGoodsService prodGoodsService;

    @Reference
    private OperatingPositionBindService operatingPositionBindService;

    @Reference
    private ContentBaseService contentBaseService;

    @Reference
    private SupplierService supplierService;

    /**
     * 添加商品
     */
    @PostMapping(value="addGoods")
    public ResultVO<ProdGoodsAddResp> addGoods(@RequestBody ProdGoodsAddReq req) {
        return prodGoodsService.addGoods(req);
    }

    /**
     * 编辑商品
     */
    @PostMapping(value="editGoods")
    public ResultVO<ProdGoodsEditResp> editGoods(@RequestBody ProdGoodsEditReq req) {
        return prodGoodsService.editGoods(req);
    }

    /**
     * 删除商品
     */
    @PostMapping(value="deleteGoods")
    public ResultVO<ProdGoodsDeleteResp> deleteGoods(@RequestBody ProdGoodsDeleteReq req) {
        if (req == null || StringUtils.isEmpty(req.getGoodsId())) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        return prodGoodsService.deleteGoods(req);
    }

    /**
     * 商品列表查询
     */
    @GetMapping(value="queryGoodsForPage")
    public ResultVO<Page<ProdGoodsDTO>> queryGoodsForPage(@RequestParam(value = "pageNo", required = false) Integer pageNo, @RequestParam
        (value = "pageSize", required = false) Integer pageSize, @RequestParam(value = "ids", required = false) List<String> ids,
        @RequestParam(value = "catId", required = false) String catId, @RequestParam(value = "searchKey", required = false)
        String searchKey, @RequestParam(value = "marketEnable", required = false) String marketEnable,
        @RequestParam(value = "adscriptionShopId", required = false) String adscriptionShopId, @RequestParam(value = "sortType",
        required = false) Integer sortType) {
        ProdGoodsQueryReq req = new ProdGoodsQueryReq();
        String defaultOrderBy = GoodsConst.SortTypeEnum.CREATE_TIME_DESC.getValue();
        if (sortType == null) {
            req.setSortType(defaultOrderBy);
        } else {
            String sortTypeValue = GoodsConst.SortTypeEnum.getValueByKey(sortType);
            req.setSortType(sortTypeValue == null ? defaultOrderBy : sortTypeValue);
        }
        setPageParam(pageNo, pageSize, req);
        if (!StringUtils.isEmpty(adscriptionShopId)) {
            try {
                List<String> goodsIdList = operatingPositionBindService.getGoodsIdsByAdscriptionShopId(adscriptionShopId);

                if (!CollectionUtils.isEmpty(goodsIdList)) {
                    req.setIds(goodsIdList);
                } else {
                    Page<ProdGoodsDTO> dtoPage = new Page<>();
                    dtoPage.setRecords(Lists.newArrayList());
                    return ResultVO.success(dtoPage);
                }
            } catch (Exception ex) {
                log.error("GoodsController.queryGoodsForPage getGoodsIdsByAdscriptionShopId throw exception exception ex={}",  ex);
                return ResultVO.errorEnum(ResultCodeEnum.SERVICE_INVOKE_EXCEPTION);
            }
        }
        req.setIds(ids);
        req.setSearchKey(searchKey);
        req.setCatId(catId);
        req.setMarketEnable(marketEnable);
        log.info("GoodsController.queryGoodsForPag req={}", JSON.toJSONString(req));
        ResultVO<Page<ProdGoodsDTO>> resultVO = prodGoodsService.queryGoodsForPage(req);
        if (resultVO.isSuccess() && resultVO.getResultData() != null && !CollectionUtils.isEmpty(resultVO.getResultData().getRecords())) {
            resultVO = setSupplierName(resultVO);
        }
        return resultVO;
    }

    private ResultVO setSupplierName(ResultVO<Page<ProdGoodsDTO>> resultVO) {
        List<ProdGoodsDTO> prodGoodsDTOList = resultVO.getResultData().getRecords();
        List<String> supplierIds = prodGoodsDTOList.stream().map(ProdGoodsDTO::getSupperId).collect(Collectors.toList());
        try {
            List<SupplierDTO> supplierDTOList = supplierService.getSupplierListByIds(supplierIds);
            if (!CollectionUtils.isEmpty(supplierDTOList)) {
                for (ProdGoodsDTO productDTO : prodGoodsDTOList) {
                    for (SupplierDTO supplierDto : supplierDTOList) {
                        if (supplierDto.getSupplierId().equals(productDTO.getSupperId())) {
                            productDTO.setSupperName(supplierDto.getSupplierName());
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error("GoodsController.queryGoodsForPage getSupplierListByIds throw exception exception ex={}", ex);
            return ResultVO.errorEnum(ResultCodeEnum.SERVICE_INVOKE_EXCEPTION);
        }
        return resultVO;
    }

    private void setPageParam(@RequestParam(value = "pageNo", required = false) Integer pageNo, @RequestParam
            (value = "pageSize", required = false) Integer pageSize, ProdGoodsQueryReq req) {
        if (StringUtils.isEmpty(pageNo) || StringUtils.isEmpty(pageSize)) {
            req.setPageNo(0);
            req.setPageSize(10);
        } else {
            req.setPageNo(pageNo);
            req.setPageSize(pageSize);

        }
    }

    /**
     * 商品列表查询
     */
    @GetMapping(value="listGoods")
    public ResultVO<List<ProdGoodsDTO>> listGoods(@RequestParam(value = "ids", required = false) List<String> ids,@RequestParam(value = "catId",
        required = false) String catId, @RequestParam(value = "searchKey", required = false) String searchKey,
        @RequestParam(value = "marketEnable", required = false) String marketEnable) {
        if (ids == null && catId == null && searchKey == null && marketEnable == null) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        ProdGoodsListReq req = new ProdGoodsListReq();
        req.setIds(ids);
        req.setSearchKey(searchKey);
        req.setCatId(catId);
        req.setMarketEnable(marketEnable);
        return prodGoodsService.listGoods(req);
    }

    /**
     * 商品详情查询
     */
    @GetMapping(value="queryGoodsDetail")
    public ResultVO<ProdGoodsDetailResp> queryGoodsDetail(@RequestParam(value = "goodsId") String goodsId) {
        log.info("GoodsController.queryGoodsDetail goodsId={}", goodsId);
        return prodGoodsService.queryGoodsDetail(goodsId);
    }

    /**
     * 修改上下架状态
     */
    @PostMapping(value="updateMarketEnable")
    public ResultVO<Boolean> updateMarketEnable(@RequestBody UpdateMarketEnableReq req, HttpServletRequest request) {
        ResultVO resultVO = getUserId(request);
        if (!resultVO.isSuccess()) {
            return resultVO;
        }
        String userId = (String)resultVO.getResultData();
        ResultVO<ProdGoodsQueryByIdReq> reqResultVO =prodGoodsService.queryGoods(req.getGoodsId());
        ProdGoodsQueryByIdReq goodsQueryByIdReq = reqResultVO.getResultData();
        if (!reqResultVO.isSuccess() || goodsQueryByIdReq == null) {
            return ResultVO.successMessage("商品为空");
        }
        String typeId = goodsQueryByIdReq.getTypeId();
        log.info("GoodsController.updateMarketEnable req={},typeId={}",JSON.toJSONString(req), typeId);
        boolean flag = false;
        try {
            if (!GoodsConst.GoodsType.CONTRACT.getCode().equals(typeId)) {
                if (GoodsConst.MARKET_ENABLE_ZERO.equals(Integer.valueOf(req.getMarketEnable()))) {
                    flag = contentBaseService.addContentPic(req.getGoodsId(), OmsConst.ActionTypeEnum.DELETE.getCode(), userId);

                } else if (GoodsConst.MARKET_ENABLE.equals(Integer.valueOf(req.getMarketEnable()))) {
                    flag = contentBaseService.addContentPic(req.getGoodsId(), OmsConst.ActionTypeEnum.ADD.getCode(),
                            userId);
                }
            }
            if (flag || GoodsConst.GoodsType.CONTRACT.getCode().equals(typeId)) {
                prodGoodsService.updateMarketEnableByPrimaryKey(req.getGoodsId(), Integer.parseInt(req.getMarketEnable()));
                flag = true;
                log.info("updateMarketEnable req={},flag={}", JSON.toJSONString(req), flag);
            }
        } catch (Exception ex) {
            log.error("updateMarketEnable addContentPic throw exception req={}, exception ex={}", JSON.toJSONString(req), ex);
            return ResultVO.errorEnum(ResultCodeEnum.SERVICE_INVOKE_EXCEPTION);
        }
        return ResultVO.success(flag);
    }

    private ResultVO getUserId(HttpServletRequest request) {
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        // 如果http请求头没有token则尝试从session中获取
        if (StringUtils.isEmpty(token)) {
            token = (String)request.getSession().getAttribute("TOKEN");
        }
        if (StringUtils.isEmpty(token)) {
            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
        }
        // 执行认证
        if (token == null || token.equals("")) {
            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
        }
        String id;
        try {
            Map<String, Claim> claims = JWT.decode(token).getClaims();
            id = claims.get("id").asString();
        } catch (Exception ex) {
            log.error("GoodsROPController.getUserId Exception ex={}",ex);
            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
        }
        if (StringUtils.isEmpty(id)) {
            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
        }
        return ResultVO.success(id);
    }

    @GetMapping(value="getAccMbr")
    public ResultVO< List<AccMbrDTO>> getAccMbr(@RequestParam(value = "pageNo", required = false) Integer pageNo, @RequestParam
            (value = "pageSize", required = false) Integer pageSize,@RequestParam(value = "orgId") String orgId,
                              @RequestParam(value = "lastNum") String lastNum) {
        List<AccMbrDTO> accMbrDTOList = Lists.newArrayList();
        AccMbrDTO accMbrDTO = new AccMbrDTO();
        accMbrDTO.setAccNbr("15918756329");
        accMbrDTO.setDeposit(100.0);
        accMbrDTOList.add(accMbrDTO);
        AccMbrDTO accMbrDTO2 = new AccMbrDTO();
        accMbrDTO2.setAccNbr("18918756389");
        accMbrDTO2.setDeposit(150.0);
        accMbrDTOList.add(accMbrDTO);
        return ResultVO.success(accMbrDTOList);
    }
}
