package com.iwhalecloud.retail.web.controller.cms;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsDetailResp;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsService;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.ContentVediolv2DTO;
import com.iwhalecloud.retail.oms.dto.ContentVideosDTO;
import com.iwhalecloud.retail.oms.dto.response.content.ContentVedioctrQryResp;
import com.iwhalecloud.retail.oms.dto.response.content.ContentVedioctrSeqResp;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentVedioctrAddReq;
import com.iwhalecloud.retail.oms.service.ContentVedioctrService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/contentVedioctr")
public class ContentVedioctrController {
	
    @Reference
    private ContentVedioctrService contentVedioctrService;

    @Reference
    private ProdGoodsService prodGoodsService;

    /**
     * 新增内容播放管理
     * @param contentVedioctrAddReq
     * @return
     * @author Ji.kai
     * @date 2018/11/7 15:27
     */
    @ApiOperation(value = "新增内容播放管理", notes = "新增内容播放管理")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO<Boolean> addContentVedioctr(@RequestBody @ApiParam( value = "新增内容播放管理对象", required = true ) ContentVedioctrAddReq contentVedioctrAddReq) {
        ResultVO<Boolean> resultVO = new ResultVO<>();
        log.info("contentVedioctrAddReq:{}", JSON.toJSONString(contentVedioctrAddReq));
        try {
            contentVedioctrService.addContentVedioctr(contentVedioctrAddReq);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(true);
            resultVO.setResultMsg("新增成功");
        } catch (Exception e) {
            resultVO.setResultData(false);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }


    /**
     * 内容播放管理查询
     * @param storageNum
     * @return
     * @author Ji.kai
     * @date 2018/11/7 15:27
     */
    @ApiOperation(value = "内容播放管理查询", notes = "内容播放管理查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storageNum", value = "货架编码", paramType = "query", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/qry", method = RequestMethod.GET)
    public ResultVO<ContentVedioctrQryResp> qryContentVedioctr(@RequestParam String storageNum) {
        ResultVO<ContentVedioctrQryResp> resultVO = new ResultVO<>();
        log.info("storageNum:{}", JSON.toJSONString(storageNum));
        try {
            ContentVedioctrQryResp contentVedioctrQryResp = contentVedioctrService.qryContentVedioctr(storageNum);
            if (contentVedioctrQryResp.getContentVedioctrSeqs() != null && contentVedioctrQryResp.getContentVedioctrSeqs().size() > 0){
                for(ContentVedioctrSeqResp contentVedioctrSeqResp: contentVedioctrQryResp.getContentVedioctrSeqs()){
                    if (contentVedioctrSeqResp.getContentVedios() != null && contentVedioctrSeqResp.getContentVedios().size()>0){
                        for(ContentVideosDTO contentVideosDTO: contentVedioctrSeqResp.getContentVedios()){
                            if(OmsConst.ContentObjType.GOODS.getCode().equals(contentVideosDTO.getObjtype())){
                                //contentVideosDTO.setProdGoodsDetail(getGoodsIntroResp(contentVideosDTO.getObjid()));
                            }
                        }
                    }
                    if (contentVedioctrSeqResp.getContentVediolv2s() != null && contentVedioctrSeqResp.getContentVediolv2s().size()>0){
                        for(ContentVediolv2DTO contentVediolv2DTO: contentVedioctrSeqResp.getContentVediolv2s()){
                            if(OmsConst.ContentObjType.GOODS.getCode().equals(contentVediolv2DTO.getObjtype())){
                                //contentVediolv2DTO.setProdGoodsDetail(getGoodsIntroResp(contentVediolv2DTO.getObjid()));
                            }
                        }
                    }
                }
            }
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(contentVedioctrQryResp);
            resultVO.setResultMsg("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    private ProdGoodsDetailResp getGoodsIntroResp(String goodId){
        com.iwhalecloud.retail.goods.dto.
                ResultVO<ProdGoodsDetailResp> resultVOGoods = prodGoodsService.queryGoodsDetail(goodId);
        return  resultVOGoods.getResultData();
    }

}