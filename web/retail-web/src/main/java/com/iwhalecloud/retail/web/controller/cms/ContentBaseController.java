package com.iwhalecloud.retail.web.controller.cms;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsDetailResp;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsService;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.*;
import com.iwhalecloud.retail.oms.dto.response.content.ContentBaseBatchResponse;
import com.iwhalecloud.retail.oms.dto.response.content.ContentBasePersonalDTO;
import com.iwhalecloud.retail.oms.dto.response.content.ContentBaseResponseDTO;
import com.iwhalecloud.retail.oms.dto.response.content.ContentIdLIstResp;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentAddReq;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentBaseBatchReq;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentBaseByObjTypePageReq;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentBasePageReq;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentBaseRequestDTO;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentEditReq;
import com.iwhalecloud.retail.oms.dto.resquest.content.*;
import com.iwhalecloud.retail.oms.service.*;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import com.iwhalecloud.retail.partner.service.PartnerShopService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ji.kai
 * @date 2018/10/28 15:27
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/contentBase")
public class ContentBaseController {
	
	@Reference
    private ContentBaseService contentBaseService;
    @Reference
    private ContentOrderpicService contentOrderpicService;
    @Reference
    private ContentPicService contentPicService;
    @Reference
    private ContentPicsetService contentPicsetService;
    @Reference
    private CatalogService catalogService;
    @Reference
    private ContentTagService contentTagService;
    @Reference
    private TagService tagService;
    @Reference
    private ContentMaterialService contentMaterialService;
    @Reference
    private ContentVideosService contentVideosService;
    @Reference
    private ContentVediolv2Service contentVediolv2Service;
    @Reference
    private ContentTextService contentTextService;
    @Reference
    private ContentChkhisService contentChkhisService;
    @Reference
    private ContentTextDetailService contentTextDetailService;
    @Reference
    private PartnerShopService partnerShopService;
    @Reference
    private OperatingPositionBindService operatingPositionBindService;
    @Reference
    private ProdGoodsService prodGoodsService;

    /**
     * 内容列表分页查询接口.
     *
     * @param page
     * @return
     * @author Ji.kai
     * @date 2018/10/29 15:27
     */
    @ApiOperation(value = "内容列表分页查询接口", notes = "根据在页面上选择的目录、内容类型、内容状态、标签以及输入的关键字等查询内容")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/qryPagelist", method = RequestMethod.POST)
    public ResultVO<Page<ContentBaseDTO>> qryContentBasePageList(@RequestBody @ApiParam( value = "查询条件", required = true )ContentBasePageReq page) {
        ResultVO<Page<ContentBaseDTO>> resultVO = new ResultVO<>();
        log.info("page:{}", JSON.toJSONString(page));
        try {
            Page<ContentBaseDTO> res = contentBaseService.qryContentBasePageList(page);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(res);
            resultVO.setResultMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    /**
     * 线上商城首页广告接口.
     *
     * @param page
     * @return
     * @author WuLiangHang
     */
    @ApiOperation(value = "线上商城首页广告/软文推送接口", notes = "向线上商城首页提供广告/软文推送接口")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/qryPicsetOrTextPagelist", method = RequestMethod.POST)
    public ResultVO<Page<ContentBaseDTO>> qryPicsetContentBasePageList(@RequestBody @ApiParam( value = "查询条件", required = true ) ContentBasePTPageReq page) {
        ResultVO<Page<ContentBaseDTO>> resultVO = new ResultVO<>();
        log.info("page:{}", JSON.toJSONString(page));
        try {
            Date date = new Date();
            page.setCurrentTime(date);
            page.setStatus(OmsConst.ContentStatusEnum.HAVE_PUBLISH.getCode().toString());
            Page<ContentBaseDTO> res = contentBaseService.qryContentBasePicsetOrTextPageList(page);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(res);
            resultVO.setResultMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "查看当前内容的详情", notes = "根据内容类型查询内容基础信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryContentDeatil", method = RequestMethod.POST)
    public ResultVO<ContentBaseResponseDTO> queryContentDeatil(@RequestBody ContentBaseRequestDTO contentBaseRequestDTO) {
        ResultVO<ContentBaseResponseDTO> resultVO = new ResultVO<ContentBaseResponseDTO>();
        log.info("contentBaseRequestDTO:{}", JSON.toJSONString(contentBaseRequestDTO));
        ContentBaseResponseDTO contentBaseResponseDTO = new ContentBaseResponseDTO();
        try {
            ContentBasePersonalDTO contentBasePersonalDTO = new ContentBasePersonalDTO();
            ContentBaseDTO contentBaseDTO = new ContentBaseDTO();
            contentBaseDTO.setContentId(contentBaseRequestDTO.getContentId());
            //调用各个原子服务
            ContentBaseDTO contentBase = contentBaseService.queryContentBase(contentBaseDTO);
            if(contentBase!=null){
                contentBaseResponseDTO.setContentBase(contentBase);
            }
            Long cataId = contentBase.getCatalogId();
            //类型	0：软文 1：单图 2：轮播图 3：推广图集 4：纯视频 5：关联视频
            Integer type = contentBase.getType();
            List<CataLogDTO> catalogs = new ArrayList<CataLogDTO>();
            catalogs = catalogService.queryCatalog(cataId);
            contentBasePersonalDTO.setCataLogs(catalogs);
            Long contentId = contentBaseRequestDTO.getContentId();
            ContentTagDTO contentTagDTO = new ContentTagDTO();
            contentTagDTO.setContentId(contentId);
            List<ContentTagDTO> contentTags = new ArrayList<ContentTagDTO>();
            contentTags  = contentTagService.queryContentTag(contentTagDTO);
            contentBasePersonalDTO.setContentTags(contentTags);
            List<Long> tagIds = new ArrayList<Long>();
            if(contentTags.size()>0){
                for (int i = 0; i < contentTags.size() ; i++) {
                    tagIds.add(contentTags.get(i).getTagId());
                }
            }
            List<TagDTO> tTags  = new ArrayList<TagDTO>();
            if(tagIds.size()>0){
                tTags  = tagService.queryTagListByParam(tagIds);
            }
            contentBasePersonalDTO.setTags(tTags);
            List<Long> matids = new ArrayList<>();
            List<ContentMaterialDTO> contentMaterials = new ArrayList<ContentMaterialDTO>();
            contentMaterials = contentMaterialService.queryContentMaterialList(contentId);
            contentBasePersonalDTO.setContentMaterials(contentMaterials);
            if(contentMaterials.size()>0){
                for (int i = 0; i < contentMaterials.size() ; i++) {
                    matids.add(contentMaterials.get(i).getMatid());
                }
            }
            if(OmsConst.ContentTypeEnum.CONTENT_TEXT.getCode().equals(String.valueOf(type).trim())){
                List<ContentTextDTO> contentTexts = new ArrayList<ContentTextDTO>();
                contentTexts = contentTextService.queryContentText(contentId);
                if(contentTexts.size()>0){
                    for (int i = 0; i < contentTexts.size(); i++) {
                        List<ContentTextDetailDTO> contentTextDetailDTOs = new ArrayList<ContentTextDetailDTO>();
                        contentTextDetailDTOs = contentTextDetailService.queryContentTextDetail(contentTexts.get(i).getTextid());
                        contentTexts.get(i).setContentTextDetails(contentTextDetailDTOs);
                    }
                }
                contentBasePersonalDTO.setContentTexts(contentTexts);
            }
            if(OmsConst.ContentTypeEnum.CONTENT_PIC.getCode().equals(String.valueOf(type).trim())){
                List<ContentPicDTO> contentPics = new ArrayList<ContentPicDTO>();
                contentPics = contentPicService.queryContentPicList(contentId);
                // objType为1的记录通过objId查询商品名称，方便点击事件上传商品名称
                // add by lin.qi
                for (ContentPicDTO content : contentPics){
                    if (content.getObjtype() == 1){
                        com.iwhalecloud.retail.goods.dto.
                                ResultVO<ProdGoodsDetailResp> resultVOGoods = prodGoodsService.queryGoodsDetail(content.getObjid());
                        ProdGoodsDetailResp resp = resultVOGoods.getResultData();
                        content.setObjName(null!=resp? resp.getName():null);
                    }
                }
                contentBasePersonalDTO.setContentPics(contentPics);
            }
            if(OmsConst.ContentTypeEnum.CONTENT_ORDERPIC.getCode().equals(String.valueOf(type).trim())){
                List<ContentOrderpicDTO> contentOrderpics = new ArrayList<ContentOrderpicDTO>();
                contentOrderpics =contentOrderpicService.queryContentOrderPicList(contentId);
                contentBasePersonalDTO.setContentOrderpics(contentOrderpics);
            }
            if(OmsConst.ContentTypeEnum.CONTENT_PICSET.getCode().equals(String.valueOf(type).trim())){
                List<ContentPicsetDTO>  contentPicsets = new ArrayList<ContentPicsetDTO>();
                contentPicsets = contentPicsetService.queryContentPicsetList(contentId);
                contentBasePersonalDTO.setContentPicsets(contentPicsets);
            }
            if(OmsConst.ContentTypeEnum.CONTENT_VIDEO.getCode().equals(String.valueOf(type).trim())){
                List<Long> contentIds = new ArrayList<>();
                contentIds.add(contentId);
                List<ContentVideosDTO> contentVideosDefaultContents = new ArrayList<ContentVideosDTO>();
                contentVideosDefaultContents = contentVideosService.queryContentVideoDefalutList(contentIds);
                contentBasePersonalDTO.setContentVedios(contentVideosDefaultContents);
            }
            if(OmsConst.ContentTypeEnum.CONTENT_VIDEO_REL.getCode().equals(String.valueOf(type).trim())){
                List<Long> contentIds = new ArrayList<>();
                contentIds.add(contentId);
                List<ContentVideosDTO> contentVideos = new ArrayList<ContentVideosDTO>();
                contentVideos = contentVideosService.queryContentVideoDefalutList(contentIds);
                contentBasePersonalDTO.setContentVedios(contentVideos);
                if(contentVideos.size()>0){
                    List<Long> upmatids = new ArrayList<>();
                    for (int i = 0; i < contentVideos.size(); i++) {
                        if(String.valueOf(contentVideos.get(i).getHavelv2mat()).equals("1")){
                            upmatids.add(contentVideos.get(i).getMatid());
                        }
                    }
                    if(upmatids!=null){
                        List<ContentVediolv2DTO>  contentVediolv2s = new ArrayList<ContentVediolv2DTO>();
                        contentVediolv2s = contentVediolv2Service.queryContentVediolByUpmatid(upmatids);
                        contentBasePersonalDTO.setContentVediolv2s(contentVediolv2s);
                    }
                }
            }
            contentBaseResponseDTO.setContentBasePersonal(contentBasePersonalDTO);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(contentBaseResponseDTO);
            resultVO.setResultMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "批量查看当前内容的详情", notes = "批量查看根据内容类型查询内容基础信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryBatchContentDeatil", method = RequestMethod.POST)
    public ResultVO<ContentBaseBatchResponse> queryBatchContentDeatil(@RequestBody ContentBaseBatchReq contentBaseBatchReq) {
        ResultVO<ContentBaseBatchResponse> resultVO = new ResultVO<ContentBaseBatchResponse>();
        log.info("contentBaseBatchReq:{}", JSON.toJSONString(contentBaseBatchReq));
        List<ContentBaseResponseDTO> contentBaseResponses = new ArrayList<ContentBaseResponseDTO>();
        try {
            List<Long> contentIds = contentBaseBatchReq.getContentIds();
            if(contentIds.size()>0){
                for (int i = 0; i < contentIds.size(); i++) {
                    ContentBaseRequestDTO contentBaseRequestDTO = new ContentBaseRequestDTO();
                    contentBaseRequestDTO.setContentId(contentIds.get(i));
                    ResultVO<ContentBaseResponseDTO> responseDTOResultVO = queryContentDeatil(contentBaseRequestDTO);
                    contentBaseResponses.add(responseDTOResultVO.getResultData());
                }
                ContentBaseBatchResponse contentBaseBatchResponse= new ContentBaseBatchResponse();
                contentBaseBatchResponse.setContentBaseResponses(contentBaseResponses);
                resultVO.setResultData(contentBaseBatchResponse);
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                resultVO.setResultMsg("成功");
            }
        }catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "商品单图内容新增测试方法", notes = "登记内容相关信息，提交后上传素材文件，并对相应的素材登记库表登记")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/addContentPic", method = RequestMethod.POST)
    public ResultVO<Boolean> addContentPic(@RequestParam(value = "goodsId", required = true)String goodsId) {
        ResultVO<Boolean> resultVO = new ResultVO<>();
        log.info("contentAddReq:{}", JSON.toJSONString(goodsId));
        try {
            String userId = UserContext.getUserId();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(contentBaseService.addContentPic(goodsId, "add", userId));
            resultVO.setResultMsg("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "内容新增", notes = "登记内容相关信息，提交后上传素材文件，并对相应的素材登记库表登记")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/addContentBase", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Boolean> addContentBase(@RequestBody @ApiParam( value = "插入内容", required = true ) ContentAddReq contentAddReq) {
        ResultVO<Boolean> resultVO = new ResultVO<>();
        log.info("contentAddReq:{}", JSON.toJSONString(contentAddReq));
        try {
            String userId = UserContext.getUserId();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(contentBaseService.addContentBase(contentAddReq, userId));
            resultVO.setResultMsg("插入成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "内容删除", notes = "对内容基础信息进行删除操作，将表中该记录的状态更新为已作废/删除")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/deleteContentBase/{contentId}", method = RequestMethod.DELETE)
    @UserLoginToken
    public ResultVO deleteContentBase(@PathVariable @ApiParam( value = "删除内容", required = true ) String contentId) {
        ResultVO resultVO = new ResultVO<>();
        log.info("contentId:", contentId);
        try {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            if(contentBaseService.contentBaseStatusChange(Long.parseLong(contentId),OmsConst.ContentStatusEnum.HAVE_CANCELLATION.getCode())){
                resultVO.setResultData("删除成功");
                resultVO.setResultMsg("success");
            }else {
                resultVO.setResultData("删除失败");
                resultVO.setResultMsg("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "内容编辑", notes = "点击编辑按钮，在弹出的页面上自动带入内容的相关信息，对相应字段进行修改并提交")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/editContentBase", method = RequestMethod.PUT)
    @UserLoginToken
    public ResultVO<Boolean> editContentBase(@RequestBody @ApiParam( value = "编辑内容", required = true ) ContentEditReq contentEditReq) {
        ResultVO<Boolean> resultVO = new ResultVO<>();
        log.info("contentEditeq:{}", JSON.toJSONString(contentEditReq));
        try {
            String userId = UserContext.getUserId();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(contentBaseService.editContentBase(contentEditReq, userId));
            resultVO.setResultMsg("编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "提交审核", notes = "提交审核")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/commitCheck", method = RequestMethod.GET)
    @UserLoginToken
    public ResultVO<Boolean> commitCheck(@RequestParam(value = "contentId", required = true)String contentId) {
        ResultVO<Boolean> resultVO = new ResultVO<>();
        log.info("contentId:{}", contentId);
        try {
            String userId = UserContext.getUserId();
            ContentBaseDTO contentBaseDTO = new ContentBaseDTO();
            contentBaseDTO.setContentId(Long.valueOf(contentId));
            contentBaseDTO.setOprId(userId);
            contentBaseDTO.setUpdDate(new Date());
            contentBaseDTO.setStatus(OmsConst.ContentStatusEnum.FOR_AUDIT.getCode());
            //内容审核表
            ContentChkhisDTO contentChkhi = new ContentChkhisDTO();
            contentChkhi.setContentid(Long.valueOf(contentId));
            contentChkhi.setOprid(userId);
            contentChkhi.setCommitdate(new Date());
            contentChkhi.setChkdate(new Date());
            contentChkhi.setResult(OmsConst.ValidStatusEnum.NOT_VALID.getCode());
            contentChkhi.setCommitoprid(userId);
            contentBaseService.updateContentBase(contentBaseDTO,contentChkhi);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultMsg("提交审核成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "失效前提醒", notes = "后台定时程序，每天0:20自动扫描内容基础信息表中状态不为6（已失效）和7（已删除/作废）且失效时间和当天相差小于3天的记录，发送短信和系统通知到内容的创建人，短信和通知内容可参考“你好，你提交的内容【内容名称】将于3天内到期，如需延期，请在系统中调整。”")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/remindBeforeInvalidate", method = RequestMethod.GET)
    public ResultVO<List<ContentBaseDTO>> remindBeforeInvalidate() {
        ResultVO<List<ContentBaseDTO>> resultVO = new ResultVO<>();
        try {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(contentBaseService.remindBeforeInvalidate());
            resultVO.setResultMsg("编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "内容失效", notes = "后台定时程序，每天0:10自动扫描内容基础信息表中状态不为6（已失效）和7（已删除/作废）的记录，取记录的失效时间，如果失效时间小于当前时间，则将该记录的状态改为6，并删除内容在发布表中的记录。")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/invalidate", method = RequestMethod.GET)
    public ResultVO<Integer> invalidateContentBase() {
        ResultVO<Integer> resultVO = new ResultVO<>();
        try {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(contentBaseService.invalidateContentBase());
            resultVO.setResultMsg("编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }


    /**
     * 查询条件根据对象类型 内容列表分页查询接口.
     *
     * @param page
     * @return
     * @author Ji.kai
     * @date 2018/10/29 15:27
     */
    @ApiOperation(value = "查询条件根据对象类型，内容列表分页查询接口", notes = "查询条件根据对象类型 查询内容")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/qryPagelistByObjType", method = RequestMethod.POST)
    public ResultVO<Page<ContentBaseDTO>> qryContentBaseByObjTypePageList(@RequestBody @ApiParam( value = "查询条件", required = true )ContentBaseByObjTypePageReq page) {
        ResultVO<Page<ContentBaseDTO>> resultVO = new ResultVO<>();
        log.info("page:{}", JSON.toJSONString(page));
        try {
            Page<ContentBaseDTO> res = contentBaseService.qryContentBaseByObjTypePageList(page);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(res);
            resultVO.setResultMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }



    @ApiOperation(value = "根据商品ID集合查询对应的内容ID", notes = "根据商品ID集合查询对应的内容ID")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryContentIdList", method = RequestMethod.POST)
    public ResultVO queryContentIdList(@RequestParam(value = "productIds", required = true)List<String> productIds) {
        ResultVO resultVO = new ResultVO();
        log.info("productIds:{}", productIds);
        ContentIdLIstResp contentIdLIstResp = new ContentIdLIstResp();
        contentIdLIstResp = contentBaseService.queryContentIdList(productIds);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(contentIdLIstResp);
        resultVO.setResultMsg("查询成功");
        return resultVO;
    }
}