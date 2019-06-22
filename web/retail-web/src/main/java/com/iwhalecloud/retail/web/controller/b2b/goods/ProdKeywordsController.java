package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ProdKeywordsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ProdKeywordsPageQueryReq;
import com.iwhalecloud.retail.goods2b.service.ProdKeywordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/b2b/prodKeywords")
@Slf4j
@Api(value="热门搜索关键字接口", tags={"热门搜索关键字接口:"})
public class ProdKeywordsController {
	
    @Reference
    private ProdKeywordsService prodKeywordsService;

    @ApiOperation(value = "热门搜索关键字查询", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/queryProdKeywordsForPage")
    public ResultVO<Page<ProdKeywordsDTO>> queryProdKeywordsForPage(
            @RequestParam(value = "pageNo") Integer pageNo,
            @RequestParam(value = "pageSize") Integer pageSize) {
        ProdKeywordsPageQueryReq req = new ProdKeywordsPageQueryReq();
        req.setPageNo(pageNo);
        req.setPageSize(pageSize);;
        return prodKeywordsService.queryProdKeywordsForPage(req);

    }
    
    
    
}