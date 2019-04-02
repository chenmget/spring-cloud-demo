package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.GoodsRulesDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ProdGoodsRuleByExcelFileReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProdGoodsRuleEditReq;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsRulesExcelResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsRulesService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/b2b/goodsRules")
@Slf4j
public class GoodsRulesB2BController {
    @Reference
    private GoodsRulesService goodsRulesService;

    @ApiOperation(value = "批量新增更新分货规则", notes = "批量新增或更新")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/addOrUpdateGoodsRulesBatch")
    public ResultVO<GoodsRulesExcelResp> addOrUpdateGoodsRulesBatch(@RequestBody List<GoodsRulesDTO> entityList){
        ProdGoodsRuleEditReq prodGoodsRuleEditReq = new ProdGoodsRuleEditReq();
        prodGoodsRuleEditReq.setGoodsRulesDTOList(entityList);
        return goodsRulesService.addProdGoodsRuleBatch(prodGoodsRuleEditReq);
    }

    @ApiOperation(value = "删除分货规则", notes = "根据id，进行单条删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value = "/deleteGoodsRule/{id}")
    public ResultVO deleteGoodsRule(@PathVariable String id){
        ProdGoodsRuleEditReq prodGoodsRuleEditReq = new ProdGoodsRuleEditReq();
        prodGoodsRuleEditReq.setId(id);
        return goodsRulesService.deleteProdGoodsRule(prodGoodsRuleEditReq);
    }

    @ApiOperation(value = "通过excel文件上传新增更新分货规则", notes = "批量新增更新")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/addOrUpdateGoodsRulesByExcelFile",headers = "content-type=multipart/form-data")
    public ResultVO<GoodsRulesExcelResp> addOrUpdateGoodsRulesByExcelFile(@RequestParam(value = "goodsId") String goodsId, @RequestParam("file") MultipartFile file) throws Exception {
        ByteArrayOutputStream baos = cloneInputStream(file.getInputStream());
        ProdGoodsRuleByExcelFileReq prodGoodsRuleByExcelFileReq = new ProdGoodsRuleByExcelFileReq();
        prodGoodsRuleByExcelFileReq.setGoodsId(goodsId);
        prodGoodsRuleByExcelFileReq.setExcelFileBytes(baos.toByteArray());
        return goodsRulesService.addProdGoodsRuleByExcelFile(prodGoodsRuleByExcelFileReq);
    }

    @ApiOperation(value = "解析excel返回List",notes = "解析excel")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/getGoodsRulesByExcel",headers = "content-type=multipart/form-data")
    public ResultVO getGoodsRulesByExcel(@RequestParam("file") MultipartFile file) throws Exception {
        ByteArrayOutputStream baos = cloneInputStream(file.getInputStream());
        ProdGoodsRuleByExcelFileReq prodGoodsRuleByExcelFileReq = new ProdGoodsRuleByExcelFileReq();
        prodGoodsRuleByExcelFileReq.setExcelFileBytes(baos.toByteArray());
        return goodsRulesService.getGoodsRulesByExcel(prodGoodsRuleByExcelFileReq);
    }

    @ApiOperation(value = "根据条件查询分货规则", notes = "列表查询")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/listGoodsRulesByCondition")
    public ResultVO<List<GoodsRulesDTO>> listGoodsRulesByCondition(@RequestBody GoodsRulesDTO condition) {
        return goodsRulesService.queryProdGoodsRuleByCondition(condition);
    }

    private ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
