package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods.dto.ProdTagsDTO;
import com.iwhalecloud.retail.goods.dto.ResultVO;
//import com.iwhalecloud.retail.goods.entity.Tags;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsDetailResp;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsService;
import com.iwhalecloud.retail.goods.service.dubbo.ProdTagsService;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.*;
import com.iwhalecloud.retail.oms.dto.response.content.ContentIdLIstResp;
import com.iwhalecloud.retail.oms.dto.response.content.ContentIdResp;
import com.iwhalecloud.retail.oms.dto.resquest.content.*;
import com.iwhalecloud.retail.oms.entity.*;
import com.iwhalecloud.retail.oms.manager.*;
import com.iwhalecloud.retail.oms.service.ContentBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class ContentBaseServiceImpl implements ContentBaseService {

    @Autowired
    private ContentBaseManager contentBaseManager;
    @Autowired
    private ContentTagManager contentTagManager;
    @Autowired
    private ContentMaterialManager contentMaterialManager;
    @Autowired
    private ContentTextManager contentTextManager;
    @Autowired
    private ContentPicManager contentPicManager;
    @Autowired
    private ContentOrderpicManager contentOrderpicManager;
    @Autowired
    private ContentPicsetManager contentPicsetManager;
    @Autowired
    private ContentVideosManager contentVideosManager;
    @Autowired
    private ContentVediolv2Manager contentVediolv2Manager;
    @Autowired
    private ContentChkhisManager contentChkhisManager;
    @Reference
    private ProdGoodsService prodGoodsService;
    @Reference
    private ProdTagsService tagsOpenService;
    @Autowired
    private TagManager tagManager;
    @Autowired
    private ContentTextDetailManager contentTextDetailManager;
    @Value("${fdfs.showUrl}")
    private String url;
    @Autowired
    private ContentPublishManager contentPublishManager;
    @Autowired
    private OperatingPositionBindManager operatingPositionBindManager;

    @Override
    public Page<ContentBaseDTO> qryContentBasePageList(ContentBasePageReq page) {
        Page<ContentBaseDTO> contentBaseDTOPage = contentBaseManager.qryContentBasePageList(page);
        for(ContentBaseDTO contentBaseDTO:contentBaseDTOPage.getRecords()){
            contentBaseDTO.setContentMaterials(contentMaterialManager.queryContentMaterialList(contentBaseDTO.getContentId()));
        }
        return contentBaseDTOPage;
    }

    @Override
    public Page<ContentBaseDTO> qryContentBasePicsetOrTextPageList(ContentBasePTPageReq page) {
//        contentMaterialManager
        Page<ContentBaseDTO> contentBaseDTOPage = contentBaseManager.qryContentBasePicsetOrTextPageList(page);
        List<ContentBaseDTO> contentBaseDTOList = contentBaseDTOPage.getRecords();
        for(ContentBaseDTO contentBaseDTO:contentBaseDTOList){
            Long contentId = contentBaseDTO.getContentId();
            contentBaseDTO.setContentMaterials(contentMaterialManager.queryContentMaterialList(contentId));
        }
        return contentBaseDTOPage;
    }


    @Override
    public List<ContentBaseDTO> qryContentBaseList(ContentBaseDTO contentBaseDTO) {
        return contentBaseManager.qryContentBaseList(contentBaseDTO);
    }


    @Override
    public ContentBaseDTO queryContentBase(ContentBaseDTO contentBaseDTO) {
        return contentBaseManager.queryContentBase(contentBaseDTO);
    }

    @Override
    public List<ContentBaseDTO> remindBeforeInvalidate() {
        List<ContentBaseDTO> contentBaseDTOS = contentBaseManager.getExpContentBases(3); //暂时天数写死，预留未来可配置参数;
        return contentBaseDTOS;
    }

    @Override
    public int invalidateContentBase() {
        return contentBaseManager.invalidateContentBase();
    }

    @Override
    @Transactional
    public int updateContentBase(ContentBaseDTO contentBaseDTO, ContentChkhisDTO contentChkhisDto) {
        ContentBaseDTO contentBase = contentBaseManager.queryContentBaseById(contentBaseDTO.getContentId());
        int updateNum = 0;
        if (contentBase != null) {
            updateNum = contentBaseManager.updateContentBase(contentBaseDTO);
            if (updateNum > 0) {
                ContentChkhis contentChkhis = new ContentChkhis();
                BeanUtils.copyProperties(contentChkhisDto,contentChkhis);
                contentChkhisManager.insertContentChkhis(contentChkhis);
            }
        }
        return updateNum;
    }

    @Override
    public Page<ContentBaseDTO> qryContentBaseByObjTypePageList(ContentBaseByObjTypePageReq page) {
        Page<ContentBaseDTO> contentBaseDTOPage = contentBaseManager.qryContentBaseByObjTypePageList(page);
        for(ContentBaseDTO contentBaseDTO:contentBaseDTOPage.getRecords()){
            contentBaseDTO.setContentMaterials(contentMaterialManager.queryContentMaterialList(contentBaseDTO.getContentId()));
        }
        return contentBaseDTOPage;
    }


    @Override
    public ContentIdLIstResp queryContentIdList(List<String> productIds) {
        ContentIdLIstResp contentIdLIstResp = new ContentIdLIstResp();
        List<ContentIdResp> contentIdResps = new ArrayList<ContentIdResp>();
        if (productIds.size() > 0) {
            for (int i = 0; i < productIds.size(); i++) {
                List<Long> contentIds = contentPicManager.queryContentIdListByProductId(productIds.get(i));
                ContentIdResp contentIdResp = new ContentIdResp();
                contentIdResp.setProductId(productIds.get(i));
                contentIdResp.setContentIds(contentIds);
                contentIdResps.add(contentIdResp);
            }
        }
        contentIdLIstResp.setContentIdResps(contentIdResps);
        return contentIdLIstResp;
    }

    @Override
    @Transactional
    public boolean addContentBase(ContentAddReq contentAddReq, String userId) {
        ContentBaseDTO contentBaseDTO = contentAddReq.getContentBase();
        List<ContentTagDTO> contentTagDTOList = contentAddReq.getContentTagList();
        List<Map<String, Object>> itemInfo = contentAddReq.getItemInfo();
        //创建时间
        Date date = new Date();
        Integer type = contentBaseDTO.getType();
        //插入内容基础信息
        contentBaseDTO.setUpdDate(date);
        if (contentBaseDTO.getStatus() != OmsConst.ContentStatusEnum.HAVE_PUBLISH.getCode()) {
            contentBaseDTO.setStatus(OmsConst.ContentStatusEnum.NOT_AUDIT.getCode());
        }
        contentBaseDTO.setOprId(userId);
        ContentBaseDTO contentBaseDTO1 = contentBaseManager.insertContentBase(contentBaseDTO);
        //内容ID
        Long contentId = contentBaseDTO1.getContentId();

        if (contentTagDTOList != null && contentTagDTOList.size() > 0) {
            //插入标签内容
            for (ContentTagDTO contentTagDTO : contentTagDTOList) {
                contentTagDTO.setContentId(contentId);
                contentTagDTO.setOprDate(date);
                contentTagDTO.setOprId(userId);
                contentTagManager.insertContentTag(contentTagDTO);
            }
        }

        List<Map<String, Object>> objInfoList = null;
        Map<String, Object> objInfo = null;
        List<String> contentUrlList = null;
        String contentUrl = null;
        String contentThumbPathUrl = null;
        String contentPathUrl = null;
        Integer objtype = null;
        String objid = null;
        String objurl = null;
        ContentMaterialDTO contentMaterialDTO = null;
        ContentMaterialDTO contentMaterialDTO1 = null;
        Long matid = null;
        switch (type) {
            case OmsConst.CONTENT_TEXT:
                //获取软文详细信息
                List<ContentTextDetailDTO> contentTextDetailDTOList = contentAddReq.getContentTextDetail();

                //保存软文信息
                ContentTextDTO contentTextDTO = new ContentTextDTO();
                contentTextDTO.setContentid(contentId);
                contentTextDTO.setUpddate(date);
                contentTextDTO.setOprid(userId);
                ContentTextDTO contentTextDTO1 = contentTextManager.insertContentText(contentTextDTO);
                Long textId = contentTextDTO1.getTextid();

                for (ContentTextDetailDTO contentTextDetailDTO : contentTextDetailDTOList) {
                    contentTextDetailDTO.setTextId(textId);
                    contentTextDetailDTO.setTxtContentId(contentId);
                    contentTextDetailDTO.setGmtCreat(date);
                    contentTextDetailManager.insertContentTextDetail(contentTextDetailDTO);
                }

                break;
            case OmsConst.CONTENT_PIC:
                //单图
                contentUrlList = (List<String>) (itemInfo.get(0).get("contentUrl"));
                contentUrl = null!=contentUrlList?contentUrlList.get(0):null;
                //只保存URI
                contentUrl = null!=contentUrl?contentUrl.replace(url, ""):null;
                //contentUrl = contentUrl.replace(url, "");
                objInfoList = (List<Map<String, Object>>) itemInfo.get(0).get("objInfo");
                objInfo = objInfoList.get(0);
                objtype = (Integer) objInfo.get("objType");
                objid = (String) objInfo.get("objId");
                objurl = (String) objInfo.get("objUrl");

                contentMaterialDTO = new ContentMaterialDTO();
                contentMaterialDTO.setContentid(contentId);
                contentMaterialDTO.setName(contentBaseDTO.getTitle() + "的素材");
                contentMaterialDTO.setUpddate(date);
                contentMaterialDTO.setPath(contentUrl);
                contentMaterialDTO.setThumbpath(contentUrl);
                contentMaterialDTO.setLel(OmsConst.CONTENT_MATERIAL_ONE_LEVEL);
                contentMaterialDTO.setOprid(userId);
                contentMaterialDTO1 = contentMaterialManager.insertContentMaterial(contentMaterialDTO);
                matid = contentMaterialDTO1.getMatid();

                ContentPicDTO contentPicDTO = new ContentPicDTO();
                contentPicDTO.setMatid(matid);
                contentPicDTO.setContentid(contentId);
                contentPicDTO.setUpddate(date);
                contentPicDTO.setOprid(userId);
                contentPicDTO.setObjid(objid);
                contentPicDTO.setObjtype(objtype);
                contentPicDTO.setObjurl(objurl);
                contentPicManager.insertContentPic(contentPicDTO);
                break;
            case OmsConst.CONTENT_ORDERPIC:
                //轮播图
                ContentOrderpicDTO contentOrderpicDTO = null;
                int order = 0;
                for (Map<String, Object> item : itemInfo) {
                    contentUrlList = (List<String>) (item.get("contentUrl"));
                    contentUrl = null!=contentUrlList?contentUrlList.get(0):null;
                    contentUrl = null!=contentUrl?contentUrl.replace(url, ""):null;
                    objInfoList = (List<Map<String, Object>>) item.get("objInfo");
                    objInfo = objInfoList.get(0);
                    objtype = (Integer) objInfo.get("objType");
                    objid = (String) objInfo.get("objId");
                    objurl = (String) objInfo.get("objUrl");

                    contentMaterialDTO = new ContentMaterialDTO();
                    contentMaterialDTO.setContentid(contentId);
                    contentMaterialDTO.setName(contentBaseDTO.getTitle() + "的素材");
                    contentMaterialDTO.setUpddate(date);
                    contentMaterialDTO.setPath(contentUrl);
                    contentMaterialDTO.setThumbpath(contentUrl);
                    contentMaterialDTO.setLel(OmsConst.CONTENT_MATERIAL_ONE_LEVEL);
                    contentMaterialDTO.setOprid(userId);
                    contentMaterialDTO1 = contentMaterialManager.insertContentMaterial(contentMaterialDTO);
                    matid = contentMaterialDTO1.getMatid();

                    contentOrderpicDTO = new ContentOrderpicDTO();
                    contentOrderpicDTO.setMatid(matid);
                    contentOrderpicDTO.setContentid(contentId);
                    contentOrderpicDTO.setUpddate(date);
                    contentOrderpicDTO.setOprid(userId);
                    contentOrderpicDTO.setObjid(objid);
                    contentOrderpicDTO.setObjtype(objtype);
                    contentOrderpicDTO.setObjurl(objurl);
                    contentOrderpicDTO.setPlayorder(order++);
                    contentOrderpicManager.insertContentOrderpic(contentOrderpicDTO);
                }
                break;
            case OmsConst.CONTENT_PICSET:
                //推广图集
                // TODO 可能存在问题
                contentUrlList = (List<String>) itemInfo.get(0).get("contentUrl");
                objInfoList = (List<Map<String, Object>>) itemInfo.get(0).get("objInfo");
                objInfo = objInfoList.get(0);
                objtype = (Integer) objInfo.get("objType");
                objid = (String) objInfo.get("objId");
                objurl = (String) objInfo.get("objUrl");
                for (String contentUrlStr : contentUrlList) {
                    contentUrlStr = contentUrlStr.replace(url, "");
                    contentMaterialDTO = new ContentMaterialDTO();
                    contentMaterialDTO.setContentid(contentId);
                    contentMaterialDTO.setName(contentBaseDTO.getTitle() + "的素材");
                    contentMaterialDTO.setUpddate(date);
                    contentMaterialDTO.setPath(contentUrlStr);
                    contentMaterialDTO.setThumbpath(contentUrlStr);
                    contentMaterialDTO.setLel(OmsConst.CONTENT_MATERIAL_ONE_LEVEL);
                    contentMaterialDTO.setOprid(userId);
                    contentMaterialDTO1 = contentMaterialManager.insertContentMaterial(contentMaterialDTO);
                    matid = contentMaterialDTO1.getMatid();

                    ContentPicsetDTO contentPicsetDTO = new ContentPicsetDTO();
                    contentPicsetDTO.setMatid(matid);
                    contentPicsetDTO.setContentid(contentId);
                    contentPicsetDTO.setUpddate(date);
                    contentPicsetDTO.setOprid(userId);
                    contentPicsetDTO.setObjtype(objtype);
                    contentPicsetDTO.setObjid(objid);
                    contentPicsetDTO.setObjurl(objurl);
                    contentPicsetManager.insertContentPicset(contentPicsetDTO);
                }
                break;
            case OmsConst.CONTENT_VIDEO:
                //纯视频
                contentUrlList = (List<String>) (itemInfo.get(0).get("contentUrl"));
                contentThumbPathUrl = null!=contentUrlList?contentUrlList.get(0):null;
                contentThumbPathUrl = null!=contentThumbPathUrl?contentThumbPathUrl.replace(url, ""):null;
                contentPathUrl = null!=contentUrlList?contentUrlList.get(1):null;
                contentPathUrl =  null!=contentPathUrl?contentPathUrl.replace(url, ""):null;
                objInfoList = (List<Map<String, Object>>) itemInfo.get(0).get("objInfo");
                objInfo = objInfoList.get(0);
                objtype = (Integer) objInfo.get("objType");
                objid = (String) objInfo.get("objId");
                objurl = (String) objInfo.get("objUrl");

                contentMaterialDTO = new ContentMaterialDTO();
                contentMaterialDTO.setContentid(contentId);
                contentMaterialDTO.setName(contentBaseDTO.getTitle() + "的素材");
                contentMaterialDTO.setUpddate(date);
                contentMaterialDTO.setPath(contentPathUrl);
                contentMaterialDTO.setThumbpath(contentThumbPathUrl);
                contentMaterialDTO.setLel(OmsConst.CONTENT_MATERIAL_ONE_LEVEL);
                contentMaterialDTO.setOprid(userId);
                contentMaterialDTO1 = contentMaterialManager.insertContentMaterial(contentMaterialDTO);
                matid = contentMaterialDTO1.getMatid();

                ContentVideosDTO contentVideosDTO = new ContentVideosDTO();
                contentVideosDTO.setMatid(matid);
                contentVideosDTO.setContentid(contentId);
                contentVideosDTO.setUpddate(date);
                contentVideosDTO.setHavelv2mat(OmsConst.ContentVedioStatusEnum.NOT_LV2.getCode());
                contentVideosDTO.setOprid(userId);
                contentVideosDTO.setObjid(objid);
                contentVideosDTO.setObjtype(objtype);
                contentVideosDTO.setObjurl(objurl);
                contentVideosManager.insertContentVideosDefaultContent(contentVideosDTO);
                break;
            case OmsConst.CONTENT_VIDEO_REL:
                //关联视频
                contentUrlList = (List<String>) (itemInfo.get(0).get("contentUrl"));
                contentThumbPathUrl = null!=contentUrlList?contentUrlList.get(0):null;
                contentThumbPathUrl = null!=contentThumbPathUrl?contentThumbPathUrl.replace(url, ""):null;
                contentPathUrl = null!=contentUrlList?contentUrlList.get(1):null;
                contentPathUrl = null!=contentPathUrl?contentPathUrl.replace(url, ""):null;
                objInfoList = (List<Map<String, Object>>) itemInfo.get(0).get("objInfo");

                contentMaterialDTO = new ContentMaterialDTO();
                contentMaterialDTO.setContentid(contentId);
                contentMaterialDTO.setName(contentBaseDTO.getTitle() + "的素材");
                contentMaterialDTO.setUpddate(date);
                contentMaterialDTO.setPath(contentPathUrl);
                contentMaterialDTO.setThumbpath(contentThumbPathUrl);
                contentMaterialDTO.setLel(OmsConst.CONTENT_MATERIAL_ONE_LEVEL);
                contentMaterialDTO.setOprid(userId);
                contentMaterialDTO1 = contentMaterialManager.insertContentMaterial(contentMaterialDTO);
                matid = contentMaterialDTO1.getMatid();

                //插入一级视频
                ContentVideosDTO contentVideosDTO1 = new ContentVideosDTO();
                contentVideosDTO1.setMatid(matid);
                contentVideosDTO1.setContentid(contentId);
                contentVideosDTO1.setUpddate(date);
                contentVideosDTO1.setHavelv2mat(OmsConst.ContentVedioStatusEnum.HAVE_LV2.getCode());
                contentVideosDTO1.setOprid(userId);
                ContentVideosDTO contentVideosDTO2 = contentVideosManager.insertContentVideosDefaultContent(contentVideosDTO1);
                Long videoMatid = contentVideosDTO2.getMatid();

                ContentVediolv2DTO contentVediolv2DTO = new ContentVediolv2DTO();
                for (Map<String, Object> objInfoItem : objInfoList) {
                    objtype = (Integer) objInfoItem.get("objType");
                    objid = (String) objInfoItem.get("objId");
                    objurl = (String) objInfoItem.get("objUrl");
                    Integer beginTime = (Integer) objInfoItem.get("beginTime");
                    Integer endTime = (Integer) objInfoItem.get("endTime");

                    //插入内容素材
                    contentMaterialDTO = new ContentMaterialDTO();
                    contentMaterialDTO.setContentid(videoMatid);
                    contentMaterialDTO.setName(contentBaseDTO.getTitle() + "的素材");
                    contentMaterialDTO.setUpddate(date);
                    contentMaterialDTO.setLel(OmsConst.CONTENT_MATERIAL_TWO_LEVEL);
                    contentMaterialDTO.setOprid(userId);
                    contentMaterialDTO.setPath(contentPathUrl);
                    contentMaterialDTO.setThumbpath(contentThumbPathUrl);
                    ContentMaterialDTO contentMaterialDTO2 = contentMaterialManager.insertContentMaterial(contentMaterialDTO);
                    //素材ID
                    Long matidForVediolv2 = contentMaterialDTO2.getMatid();

                    contentVediolv2DTO.setMatid(matidForVediolv2);
                    contentVediolv2DTO.setUpmatid(videoMatid);
                    contentVediolv2DTO.setUpddate(date);
                    contentVediolv2DTO.setOprid(userId);
                    contentVediolv2DTO.setObjtype(objtype);
                    contentVediolv2DTO.setObjid(objid);
                    contentVediolv2DTO.setObjurl(objurl);
                    contentVediolv2DTO.setStartsec(beginTime);
                    contentVediolv2DTO.setEndsec(endTime);
                    contentVediolv2Manager.insertContentVediolv2(contentVediolv2DTO);
                }
                break;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean editContentBase(ContentEditReq contentEditReq, String userId) {
        try {
            //暂时按内容类型不可变更处理
            List<Map<String, Object>> itemInfo = contentEditReq.getItemInfo();
            ContentBaseDTO contentBaseDTO = contentEditReq.getContentBase();
            //创建时间
            Date date = new Date();
            Integer type = contentBaseDTO.getType();
            //内容ID
            Long contentId = contentBaseDTO.getContentId();
            //插入内容基础信息
            contentBaseDTO.setUpdDate(date);
            if (!OmsConst.ContentStatusEnum.NOT_AUDIT.getCode().equals(contentBaseDTO.getStatus())) {
                contentBaseDTO.setStatus(OmsConst.ContentStatusEnum.FOR_AUDIT.getCode());
            }
            operatingPositionBindManager.unbindContentId(contentId.toString());
            contentBaseDTO.setOprId(userId);
            contentBaseManager.updateContentBaseForEdit(contentBaseDTO);

            //删除旧的关联标签
            List<ContentTagDTO> oldContentTags = contentTagManager.queryContentTagByContentId(contentBaseDTO.getContentId());
            for (ContentTagDTO oldContentTagsItem : oldContentTags) {
                contentTagManager.deleteContentTag(oldContentTagsItem);
            }
            List<ContentTagDTO> contentTags = contentEditReq.getContentTagList();

            if (contentTags != null && contentTags.size() > 0) {
                //插入标签内容
                for (ContentTagDTO contentTag : contentTags) {
                    contentTag.setContentId(contentId);
                    contentTag.setOprDate(date);
                    contentTag.setOprId(userId);
                    contentTagManager.insertContentTag(contentTag);
                }
            }

            List<Map<String, Object>> objInfoList = null;
            Map<String, Object> objInfo = null;
            List<String> contentUrlList = null;
            String contentUrl = null;
            String contentThumbPathUrl = null;
            String contentPathUrl = null;
            Integer objtype = null;
            String objid = null;
            String objurl = null;
            ContentMaterialDTO contentMaterialDTO = null;
            ContentMaterialDTO contentMaterialDTO1 = null;
            Long matid = null;
            List<ContentMaterialDTO> oldContentMaterial = null;
            List<Long> contentIds = null;
            ContentVideosDTO contentVideosDefaultContent = null;
            switch (type) {
                case OmsConst.CONTENT_TEXT:
                    //获取软文详细信息
                    List<ContentTextDetailDTO> contentTextDetailDTOList = contentEditReq.getContentTextDetail();
                    //软文
                    //此处需查询
                    ContentTextDTO contentText = contentTextManager.queryContentText(contentId).get(0);
                    contentText.setContentid(contentId);
                    contentText.setUpddate(date);
                    contentText.setOprid(userId);
                    contentTextManager.updateContentText(contentText);
                    Long textId = contentText.getTextid();

                    ContentTextDetailDTO contentTextDetailDTOTemp = new ContentTextDetailDTO();
                    contentTextDetailDTOTemp.setTxtContentId(contentId);
                    contentTextDetailManager.deleteContentTextDetailByContentId(contentTextDetailDTOTemp);
                    for (ContentTextDetailDTO contentTextDetailDTO : contentTextDetailDTOList) {
                        contentTextDetailDTO.setTextId(textId);
                        contentTextDetailDTO.setTxtContentId(contentId);
                        contentTextDetailDTO.setGmtCreat(date);
                        contentTextDetailManager.insertContentTextDetail(contentTextDetailDTO);
                    }
                    break;
                case OmsConst.CONTENT_PIC:
                    //单图
                    contentUrlList = (List<String>) (itemInfo.get(0).get("contentUrl"));
                    contentUrl = null!=contentUrlList?contentUrlList.get(0):null;
                    contentUrl = null!=contentUrl?contentUrl.replace(url, ""):null;
                    objInfoList = (List<Map<String, Object>>) itemInfo.get(0).get("objInfo");
                    objInfo = objInfoList.get(0);
                    objtype = (Integer) objInfo.get("objType");
                    objid = (String) objInfo.get("objId");
                    objurl = (String) objInfo.get("objUrl");
                    contentMaterialDTO = new ContentMaterialDTO();
                    contentMaterialDTO.setContentid(contentId);
                    contentMaterialManager.deleteContentMaterial(contentMaterialDTO);

                    contentMaterialDTO = new ContentMaterialDTO();
                    contentMaterialDTO.setContentid(contentId);
                    contentMaterialDTO.setName(contentBaseDTO.getTitle() + "的素材");
                    contentMaterialDTO.setUpddate(date);
                    contentMaterialDTO.setPath(contentUrl);
                    contentMaterialDTO.setThumbpath(contentUrl);
                    contentMaterialDTO.setLel(OmsConst.CONTENT_MATERIAL_ONE_LEVEL);
                    contentMaterialDTO.setOprid(userId);
                    contentMaterialDTO1 = contentMaterialManager.insertContentMaterial(contentMaterialDTO);
                    matid = contentMaterialDTO1.getMatid();

                    ContentPicDTO contentPic = new ContentPicDTO();
                    contentPic.setContentid(contentId);
                    contentPicManager.deleteContentPic(contentPic);

                    contentPic = new ContentPicDTO();
                    contentPic.setMatid(matid);
                    contentPic.setContentid(contentId);
                    contentPic.setUpddate(date);
                    contentPic.setOprid(userId);
                    contentPic.setObjid(objid);
                    contentPic.setObjtype(objtype);
                    contentPic.setObjurl(objurl);
                    contentPicManager.insertContentPic(contentPic);
                    break;
                case OmsConst.CONTENT_ORDERPIC:
                    //轮播图
                    ContentOrderpicDTO contentOrderpic = null;
                    contentMaterialDTO = new ContentMaterialDTO();
                    contentMaterialDTO.setContentid(contentId);
                    contentMaterialManager.deleteContentMaterial(contentMaterialDTO);

                    contentOrderpic = new ContentOrderpicDTO();
                    contentOrderpic.setContentid(contentId);
                    contentOrderpicManager.deleteContentOrderpic(contentOrderpic);

                    int i = 0;
                    for (Map<String, Object> item : itemInfo) {
                        contentUrlList = (List<String>) (item.get("contentUrl"));
                        contentUrl = null!=contentUrlList?contentUrlList.get(0):null;
                        contentUrl = null!=contentUrl?contentUrl.replace(url, ""):null;
                        objInfoList = (List<Map<String, Object>>) item.get("objInfo");
                        objInfo = objInfoList.get(0);
                        objtype = (Integer) objInfo.get("objType");
                        objid = (String) objInfo.get("objId");
                        objurl = (String) objInfo.get("objUrl");

                        contentMaterialDTO = new ContentMaterialDTO();
                        contentMaterialDTO.setContentid(contentId);
                        contentMaterialDTO.setName(contentBaseDTO.getTitle() + "的素材");
                        contentMaterialDTO.setUpddate(date);
                        contentMaterialDTO.setPath(contentUrl);
                        contentMaterialDTO.setThumbpath(contentUrl);
                        contentMaterialDTO.setLel(OmsConst.CONTENT_MATERIAL_ONE_LEVEL);
                        contentMaterialDTO.setOprid(userId);
                        contentMaterialDTO1 = contentMaterialManager.insertContentMaterial(contentMaterialDTO);
                        matid = contentMaterialDTO1.getMatid();

                        contentOrderpic = new ContentOrderpicDTO();
                        contentOrderpic.setMatid(matid);
                        contentOrderpic.setContentid(contentId);
                        contentOrderpic.setUpddate(date);
                        contentOrderpic.setOprid(userId);
                        contentOrderpic.setObjid(objid);
                        contentOrderpic.setObjtype(objtype);
                        contentOrderpic.setObjurl(objurl);
                        contentOrderpic.setPlayorder(i++);
                        contentOrderpicManager.insertContentOrderpic(contentOrderpic);
                    }
                    break;
                case OmsConst.CONTENT_PICSET:
                    //推广图集
                    contentMaterialDTO = new ContentMaterialDTO();
                    contentMaterialDTO.setContentid(contentId);
                    contentMaterialManager.deleteContentMaterial(contentMaterialDTO);

                    ContentPicsetDTO contentPicset = new ContentPicsetDTO();
                    contentPicset.setContentid(contentId);
                    contentPicsetManager.deleteContentPicset(contentPicset);

                    contentUrlList = (List<String>) itemInfo.get(0).get("contentUrl");
                    objInfoList = (List<Map<String, Object>>) itemInfo.get(0).get("objInfo");
                    objInfo = objInfoList.get(0);
                    objtype = (Integer) objInfo.get("objType");
                    objid = (String) objInfo.get("objId");
                    objurl = (String) objInfo.get("objUrl");
                    for (String contentUrlStr : contentUrlList) {
                        contentMaterialDTO = new ContentMaterialDTO();
                        contentUrlStr = contentUrlStr.replace(url, "");
                        contentMaterialDTO.setContentid(contentId);
                        contentMaterialDTO.setName(contentBaseDTO.getTitle() + "的素材");
                        contentMaterialDTO.setUpddate(date);
                        contentMaterialDTO.setPath(contentUrlStr);
                        contentMaterialDTO.setThumbpath(contentUrlStr);
                        contentMaterialDTO.setLel(OmsConst.CONTENT_MATERIAL_ONE_LEVEL);
                        contentMaterialDTO.setOprid(userId);
                        contentMaterialDTO1 = contentMaterialManager.insertContentMaterial(contentMaterialDTO);
                        matid = contentMaterialDTO1.getMatid();

                        contentPicset = new ContentPicsetDTO();
                        contentPicset.setMatid(matid);
                        contentPicset.setContentid(contentId);
                        contentPicset.setUpddate(date);
                        contentPicset.setOprid(userId);
                        contentPicset.setObjtype(objtype);
                        contentPicset.setObjid(objid);
                        contentPicset.setObjurl(objurl);
                        contentPicsetManager.insertContentPicset(contentPicset);
                    }
                    break;
                case OmsConst.CONTENT_VIDEO:
                    //纯视频
                    contentUrlList = (List<String>) (itemInfo.get(0).get("contentUrl"));
                    contentThumbPathUrl = null!=contentUrlList?contentUrlList.get(0):null;
                    contentThumbPathUrl = null!=contentThumbPathUrl?contentThumbPathUrl.replace(url, ""):null;
                    contentPathUrl = null!=contentUrlList?contentUrlList.get(1):null;
                    contentPathUrl =  null!=contentPathUrl?contentPathUrl.replace(url, ""):null;
                    objInfoList = (List<Map<String, Object>>) itemInfo.get(0).get("objInfo");
                    objInfo = objInfoList.get(0);
                    objtype = (Integer) objInfo.get("objType");
                    objid = (String) objInfo.get("objId");
                    objurl = (String) objInfo.get("objUrl");

                    contentMaterialDTO = new ContentMaterialDTO();
                    contentMaterialDTO.setContentid(contentId);
                    contentMaterialManager.deleteContentMaterial(contentMaterialDTO);

                    contentMaterialDTO.setName(contentBaseDTO.getTitle() + "的素材");
                    contentMaterialDTO.setUpddate(date);
                    contentMaterialDTO.setPath(contentPathUrl);
                    contentMaterialDTO.setThumbpath(contentThumbPathUrl);
                    contentMaterialDTO.setOprid(userId);
                    contentMaterialDTO.setLel(OmsConst.CONTENT_MATERIAL_ONE_LEVEL);
                    contentMaterialDTO1 = contentMaterialManager.insertContentMaterial(contentMaterialDTO);
                    matid = contentMaterialDTO1.getMatid();

                    contentVideosDefaultContent = new ContentVideosDTO();
                    contentVideosDefaultContent.setContentid(contentId);
                    contentVideosManager.deleteContentVideosDefaultContent(contentVideosDefaultContent);

                    contentVideosDefaultContent.setUpddate(date);
                    contentVideosDefaultContent.setOprid(userId);
                    contentVideosDefaultContent.setObjid(objid);
                    contentVideosDefaultContent.setObjtype(objtype);
                    contentVideosDefaultContent.setMatid(matid);
                    contentVideosDefaultContent.setObjurl(objurl);
                    contentVideosDefaultContent.setHavelv2mat(OmsConst.ContentVedioStatusEnum.NOT_LV2.getCode());
                    contentVideosManager.insertContentVideosDefaultContent(contentVideosDefaultContent);
                    break;
                case OmsConst.CONTENT_VIDEO_REL:
                    //关联视频
                    ContentVediolv2DTO contentVediolv2 = null;
                    contentUrlList = (List<String>) (itemInfo.get(0).get("contentUrl"));
                    contentThumbPathUrl = null!=contentUrlList?contentUrlList.get(0):null;
                    contentThumbPathUrl = null!=contentThumbPathUrl?contentThumbPathUrl.replace(url, ""):null;
                    contentPathUrl = null!=contentUrlList?contentUrlList.get(1):null;
                    contentPathUrl =  null!=contentPathUrl?contentPathUrl.replace(url, ""):null;
                    objInfoList = (List<Map<String, Object>>) itemInfo.get(0).get("objInfo");
                    oldContentMaterial = contentMaterialManager.queryContentMaterialList(contentId);
                    for (ContentMaterialDTO oldContentMaterialItem : oldContentMaterial) {
                        contentVediolv2 = new ContentVediolv2DTO();
                        contentVediolv2.setMatid(oldContentMaterialItem.getMatid());
                        contentVediolv2Manager.deleteContentVediolv2(contentVediolv2);
                        contentMaterialManager.deleteContentMaterial(oldContentMaterialItem);
                    }

                    contentVideosDefaultContent = new ContentVideosDTO();
                    contentVideosDefaultContent.setContentid(contentId);
                    contentVideosManager.deleteContentVideosDefaultContent(contentVideosDefaultContent);
                    contentIds = new ArrayList<Long>();
                    contentIds.add(0, contentId);

                    contentMaterialDTO = new ContentMaterialDTO();
                    contentMaterialDTO.setContentid(contentId);
                    contentMaterialDTO.setName(contentBaseDTO.getTitle() + "的素材");
                    contentMaterialDTO.setUpddate(date);
                    contentMaterialDTO.setPath(contentPathUrl);
                    contentMaterialDTO.setThumbpath(contentThumbPathUrl);
                    contentMaterialDTO.setLel(OmsConst.CONTENT_MATERIAL_ONE_LEVEL);
                    contentMaterialDTO.setOprid(userId);
                    contentMaterialDTO1 = contentMaterialManager.insertContentMaterial(contentMaterialDTO);
                    matid = contentMaterialDTO1.getMatid();

                    //插入一级视频
                    contentVideosDefaultContent.setUpddate(date);
                    contentVideosDefaultContent.setOprid(userId);
                    contentVideosDefaultContent.setObjid(objid);
                    contentVideosDefaultContent.setObjtype(objtype);
                    contentVideosDefaultContent.setMatid(matid);
                    contentVideosDefaultContent.setHavelv2mat(OmsConst.ContentVedioStatusEnum.HAVE_LV2.getCode());
                    ContentVideosDTO contentVideosDefaultContent2 = contentVideosManager.insertContentVideosDefaultContent(contentVideosDefaultContent);
                    Long videoMatid = contentVideosDefaultContent2.getMatid();


                    for (Map<String, Object> objInfoItem : objInfoList) {
                        objtype = (Integer) objInfoItem.get("objType");
                        objid = (String) objInfoItem.get("objId");
                        objurl = (String) objInfoItem.get("objUrl");
                        Integer beginTime = (Integer) objInfoItem.get("beginTime");
                        Integer endTime = (Integer) objInfoItem.get("endTime");

                        //插入内容素材
                        contentMaterialDTO = new ContentMaterialDTO();
                        contentMaterialDTO.setContentid(videoMatid);
                        contentMaterialDTO.setName(contentBaseDTO.getTitle() + "的素材");
                        contentMaterialDTO.setUpddate(date);
                        contentMaterialDTO.setLel(OmsConst.CONTENT_MATERIAL_TWO_LEVEL);
                        contentMaterialDTO.setOprid(userId);
                        contentMaterialDTO.setPath(contentPathUrl);
                        contentMaterialDTO.setThumbpath(contentThumbPathUrl);
                        ContentMaterialDTO contentMaterialDTO2 = contentMaterialManager.insertContentMaterial(contentMaterialDTO);
                        //素材ID
                        Long matidForVediolv2 = contentMaterialDTO2.getMatid();
                        contentVediolv2 = new ContentVediolv2DTO();
                        contentVediolv2.setMatid(matidForVediolv2);
                        contentVediolv2.setUpmatid(videoMatid);
                        contentVediolv2.setUpddate(date);
                        contentVediolv2.setOprid(userId);
                        contentVediolv2.setObjtype(objtype);
                        contentVediolv2.setObjid(objid);
                        contentVediolv2.setObjurl(objurl);
                        contentVediolv2.setStartsec(beginTime);
                        contentVediolv2.setEndsec(endTime);
                        contentVediolv2Manager.insertContentVediolv2(contentVediolv2);
                    }
                    break;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 对内容基础信息进行删除操作，将表中该记录的状态更新为已作废/删除
     *
     * @param contentId
     * @return
     */
    @Override
    @Transactional
    public boolean contentBaseStatusChange(Long contentId, Integer status) {
        try {
            ContentBaseDTO contentBaseDTO = contentBaseManager.queryContentBaseById(contentId);
            contentBaseDTO.setStatus(status);
            if (!OmsConst.ContentStatusEnum.HAVE_PUBLISH.getCode().equals(status)) {
                //如果不为已发布 则将contentId从 operatingPositionBind 表中解除
                operatingPositionBindManager.unbindContentId(contentId.toString());
            }
            if (contentBaseManager.updateContentBase(contentBaseDTO) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return
     * @Author Wu.LiangHang
     * @Description
     * @Date 2018/11/8 13:43
     * @Param contentId
     **/
    @Override
    @Transactional
    public boolean addContentPic(String goodsId, String actionType, String userId) throws Exception {
        Date date = new Date();
        switch (actionType) {
            case "add":
                //获取商品信息
                ResultVO<ProdGoodsDetailResp> resultVOGoods = prodGoodsService.queryGoodsDetail(goodsId);
                ProdGoodsDetailResp goodsDetail = resultVOGoods.getResultData();
                ContentBaseDTO contentBase = new ContentBaseDTO();
                contentBase.setCatalogId(1L);//暂定根目录 后面创建好了再修改为单图片目录
                contentBase.setOprId(userId);
                //商品提交后状态为已发布
                contentBase.setStatus(OmsConst.ContentStatusEnum.HAVE_PUBLISH.getCode());
                contentBase.setTitle(goodsDetail.getName());
                contentBase.setDesp(goodsDetail.getSellingPoint());
                contentBase.setType(OmsConst.CONTENT_PIC);//单图片类型
                contentBase.setEffDate(date);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                contentBase.setExpDate(sdf.parse("2199-12-12"));

                ContentAddReq contentAddReq = new ContentAddReq();
                List<Map<String, Object>> itemInfo = new ArrayList<Map<String, Object>>();
                Map<String, Object> map = new HashMap<String, Object>();
                List<String> contentUrlList = new ArrayList<String>();
                String contentUrl = goodsDetail.getDefaultImage();
                contentUrlList.add(0, contentUrl);
                /*String contentUrl = goodsDetail.getRollImage();//使用轮播图作为内容缩略图
                String http = "http.*";
                String[] contentUrlArr = contentUrl.split(",");
                //存储第一个图片URL
                if (!Pattern.matches(http, contentUrlArr[0])) {
                    contentUrl = url + contentUrlArr[0];
                }
                contentUrlList.add(0, contentUrl);*/
                List<Map<String, Object>> objInfoList = new ArrayList<Map<String, Object>>();
                Map<String, Object> objInfo = new HashMap<String, Object>();
                String objId = goodsId;
                Integer objType = 1;//类型为商品
                String objUrl = "/#/goods/" + goodsId;
                String objName = goodsDetail.getName();
                objInfo.put("objType", objType);
                objInfo.put("objId", objId);
                objInfo.put("objUrl", objUrl);
                objInfo.put("objName", objName);
                objInfoList.add(0, objInfo);
                //插入标签
                List<ContentTagDTO> contentTagList = new ArrayList<ContentTagDTO>();
                List<ProdTagsDTO> tagsList = tagsOpenService.getTagsByGoodsId(goodsId);
                int i = 0;
                //去t_tag中查询
                for (ProdTagsDTO tags : tagsList) {
                    if (tagManager.queryTagListByTagName(tags.getTagName()).isEmpty()) {
                        TagDTO tagDTO = new TagDTO();
                        tagDTO.setTagName(tags.getTagName());
                        tagDTO.setTagDesc(tags.getTagName());
                        tagDTO.setTagType(1);
                        tagDTO.setTagColor("#FF7A3B");
                        tagManager.createTag(tagDTO);
                    }
                    List<TagDTO> tagDTOS = tagManager.queryTagListByTagName(tags.getTagName());
                    //一般情况下内容标签不重名
                    TagDTO tagDTO = tagDTOS.get(0);
                    ContentTagDTO contentTagDTO = new ContentTagDTO();
                    contentTagDTO.setTagId(tagDTO.getTagId());
                    contentTagList.add(i++, contentTagDTO);
                }
                map.put("contentUrl", contentUrlList);
                map.put("objInfo", objInfoList);
                itemInfo.add(0, map);
                contentAddReq.setItemInfo(itemInfo);
                contentAddReq.setContentBase(contentBase);
                contentAddReq.setContentTagList(contentTagList);
                this.addContentBase(contentAddReq, userId);
                //发布商品内容
                ContentPublishDTO contentPublishDTO = new ContentPublishDTO();
                contentPublishDTO.setContentid(contentAddReq.getContentBase().getContentId());
                contentPublishDTO.setOprid(userId);
                contentPublishDTO.setExpdate(sdf.parse("2199-12-12"));
                contentPublishDTO.setEffdate(date);
                contentPublishDTO.setPublishdate(date);
                //全渠道全地市
                contentPublishDTO.setWaytype("1,2,3");
                contentPublishDTO.setArea("430100,430200,430400,430600,430700,430900,431100,431300,433100,430300,430500,431000,431200,430800");
                contentPublishManager.createContentPublish(contentPublishDTO);
                break;
            case "delete":
                //通过商品ID查询contentId列表然后将基础内容设为已删除
                List<String> goodsIds = new ArrayList<String>();
                goodsIds.add(goodsId);
                List<ContentPicDTO> contentPicDTOList = contentPicManager.queryContentPicListByObjId(goodsIds);
                for (ContentPicDTO contentPicDTO : contentPicDTOList) {
                    Long contentId = contentPicDTO.getContentid();
                    this.contentBaseStatusChange(contentId, OmsConst.ContentStatusEnum.HAVE_DELETE.getCode());
                }
                break;
        }
        return true;
    }

}