package com.iwhalecloud.retail.web.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.ListGoodsRankingsDTO;
import com.iwhalecloud.retail.oms.dto.resquest.ListGoodsRankingsReq;
import com.iwhalecloud.retail.oms.service.GoodsRankingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/order/goodRanking")
public class GoodsRankingController {

    @Reference
    private GoodsRankingsService goodsRankingsService;


    @GetMapping("/listGoodRanking")
    public ResultVO listGoodsRanking(@RequestParam(value = "objType") String objType, @RequestParam(value = "goodsId") String goodsId, @RequestParam(value = "goodsName") String goodsName,@RequestParam(value = "pageNo") int pageNo, @RequestParam(value = "pageSize") int pageSize) {
        ResultVO resultVO = new ResultVO();
        ListGoodsRankingsReq req = new ListGoodsRankingsReq();
        req.setGoodsId(goodsId);
        req.setGoodsName(goodsName);
        req.setObjType(objType);
        req.setPageNo(pageNo);
        req.setPageSize(pageSize);
        
        Page<ListGoodsRankingsDTO> listGoodsRankingsDTO  = goodsRankingsService.listGoodsRankings(req);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(listGoodsRankingsDTO);
        resultVO.setResultMsg("成功");
        return resultVO;
    }
}