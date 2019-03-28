package com.iwhalecloud.retail.web.controller.b2b.goods;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ActivityGoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.BrandUrlResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.BrandService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.goods.request.BrandAddReqDTO;
import com.iwhalecloud.retail.web.controller.b2b.goods.request.BrandUpdateReqDTO;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import com.iwhalecloud.retail.web.utils.FastDFSImgStrJoinUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author he.sw
 * @date 2018/12/01
 */
@RestController
@RequestMapping("/api/b2b/goodsBrand")
@Slf4j
public class GoodsBrandB2BController {

    @Reference
    private BrandService brandService;

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;

    @ApiOperation(value = "品牌详情查询", notes = "传入品牌ID，商品ID进行查询操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="getBrand")
    public ResultVO<Page<BrandUrlResp>> getBrand(@RequestBody BrandGetReq req) {
        ResultVO<Page<BrandUrlResp>> resultVO = brandService.getBrand(req);
        Page<BrandUrlResp> respPage = resultVO.getResultData();
        List<BrandUrlResp> list = respPage.getRecords();
        if (null == list || list.isEmpty()){
            return resultVO;
        }
        for(BrandUrlResp resp:list){
            if(StringUtils.isEmpty(resp.getFileUrl())){
                continue;
            }
            String newImageFile = FastDFSImgStrJoinUtil.fullImageUrl(resp.getFileUrl(), dfsShowIp, true);
            resp.setFileUrl(newImageFile);
        }
        resultVO.setResultData(respPage);
        return resultVO;
    }


	@ApiOperation(value = "品牌查询", notes = "查询所有操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="listAll")
    public ResultVO<List<BrandUrlResp>> listAll() {
        ResultVO<List<BrandUrlResp>> resultVO = brandService.listAll();
        List<BrandUrlResp> list = resultVO.getResultData();
        if (null == list || list.isEmpty()){
            return ResultVO.success();
        }
        for(BrandUrlResp resp:list){
            if(StringUtils.isEmpty(resp.getFileUrl())){
                continue;
            }
            String newImageFile = FastDFSImgStrJoinUtil.fullImageUrl(resp.getFileUrl(), dfsShowIp, true);
            resp.setFileUrl(newImageFile);
        }
        resultVO.setResultData(list);
        return resultVO;
    }

	@ApiOperation(value = "添加品牌", notes = "添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addBrand")
    @UserLoginToken
    public ResultVO<Integer> addBrand(@RequestBody BrandAddReqDTO dto) {
        // 获取userId
        String userId = UserContext.getUserId();
        if(org.apache.commons.lang.StringUtils.isEmpty(userId)){
            ResultVO resultVO = new ResultVO();
            resultVO.setResultMsg("userId can not be null");
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            return resultVO;
        }
        BrandAddReq req = new BrandAddReq();
        BeanUtils.copyProperties(dto, req);
        req.setCreateStaff(userId);
        return brandService.addBrand(req);
        
    }

	@ApiOperation(value = "更新品牌", notes = "更新操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value="updateBrand")
    @UserLoginToken
    public ResultVO<Integer> updateBrand(@RequestBody @Valid BrandUpdateReqDTO dto, BindingResult results) {
        if(results.hasErrors()) {
            return ResultVO.error(results.getFieldError().getDefaultMessage());
        }
        // 获取userId
        String userId = UserContext.getUserId();
        if(org.apache.commons.lang.StringUtils.isEmpty(userId)){
            ResultVO resultVO = new ResultVO();
            resultVO.setResultMsg("userId can not be null");
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            return resultVO;
        }
        BrandUpdateReq req = new BrandUpdateReq();
        BeanUtils.copyProperties(dto, req);
        req.setUpdateStaff(userId);
        return brandService.updateBrand(req);
        
    }

	@ApiOperation(value = "删除品牌", notes = "删除操作")
    @ApiImplicitParam(name = "brandId", value = "brandId", paramType = "query", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value="deleteBrand")
    public ResultVO<Integer> deleteBrand(@RequestParam(value = "brandId", required = true) String brandId) {
        BrandQueryReq brandQueryReq = new BrandQueryReq();
        brandQueryReq.setBrandId(brandId);
        return brandService.deleteBrand(brandQueryReq);
    }

    @ApiOperation(value = "查询品牌图片", notes = "传入品牌id集合查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="listBrandFileUrl")
    public ResultVO<List<BrandUrlResp>> listBrandFileUrl(@RequestParam(value = "idList") @ApiParam(value = "id列表") List<String> idList) {
        BrandQueryReq req = new BrandQueryReq();
        req.setBrandIdList(idList);
        return brandService.listBrandFileUrl(req);
    }

    @ApiOperation(value = "查询分类品牌图片", notes = "传入分类id查询")
    @ApiImplicitParam(name = "catId", value = "catId", paramType = "query", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="listBrandByCatId")
    public ResultVO<List<BrandUrlResp>> listBrandByCatId(@RequestParam(value = "catId")  String catId) {
        BrandQueryReq brandQueryReq = new BrandQueryReq();
        brandQueryReq.setCatId(catId);
        ResultVO<List<BrandUrlResp>> resultVO = brandService.listBrandByCatId(brandQueryReq);
        List<BrandUrlResp> list = resultVO.getResultData();
        if (null == list || list.isEmpty()){
            return ResultVO.success();
        }
        for(BrandUrlResp resp:list){
            if(StringUtils.isEmpty(resp.getFileUrl())){
                continue;
            }
            String newImageFile = FastDFSImgStrJoinUtil.fullImageUrl(resp.getFileUrl(), dfsShowIp, true);
            resp.setFileUrl(newImageFile);
        }
        resultVO.setResultData(list);
        return resultVO;
    }

    @ApiOperation(value = "查询品牌商品", notes = "传入品牌id集合查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="listBrandActivityGoodsId")
    @UserLoginToken
    public ResultVO<Page<ActivityGoodsDTO>> listBrandActivityGoodsId(@RequestBody BrandActivityReq req) {
        UserDTO userDTO = UserContext.getUser();
        String merchantId = UserContext.getMerchantId();
        req.setRegionId(userDTO.getRegionId());
        req.setLanId(userDTO.getLanId());
        req.setSupplierId(merchantId);
        log.info("GoodsBrandB2BController listBrandActivityGoodsId req={} ", req);
        ResultVO<Page<ActivityGoodsDTO>> resultVO = brandService.listBrandActivityGoodsId(req);
        return ResultVO.success(resultVO.getResultData());
    }
}
