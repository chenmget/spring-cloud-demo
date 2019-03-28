package com.iwhalecloud.retail.web.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.GoodsCountRankDTO;
import com.iwhalecloud.retail.oms.dto.resquest.GoodsCountRankRequest;
import com.iwhalecloud.retail.oms.service.GoodsCountRankService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/goodsCountRank")
public class GoodsCountRankController {
    @Reference
    private GoodsCountRankService goodsCountRankService;


    @ApiOperation(value = "查询GoodsCountRankDTO列表", notes = "根据adscriptionCity eventCode，查询GoodsCountRankDTO列表")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数未填对"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    // 查询搜索关键字统计列表
    @RequestMapping(value = "/queryRankList", method = RequestMethod.POST)
    public ResultVO<Page<GoodsCountRankDTO>> queryKeywordsRankList(@RequestBody GoodsCountRankRequest request) {
        ResultVO<Page<GoodsCountRankDTO>> resultVO = new ResultVO<>();
        Page<GoodsCountRankDTO> rankDTO = goodsCountRankService.queryGoodsCountRank(request);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(rankDTO);
        resultVO.setResultMsg("SUCCESS");
        return resultVO;
    }
}
