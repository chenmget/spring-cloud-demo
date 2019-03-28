package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.ContentBaseDTO;
import com.iwhalecloud.retail.oms.dto.ContentMaterialDTO;
import com.iwhalecloud.retail.oms.dto.ContentVedioctrDTO;
import com.iwhalecloud.retail.oms.dto.ContentVediolv2DTO;
import com.iwhalecloud.retail.oms.dto.ContentVideosDTO;
import com.iwhalecloud.retail.oms.dto.response.content.ContentVedioctrQryResp;
import com.iwhalecloud.retail.oms.dto.response.content.ContentVedioctrSeqResp;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentVedioctrAddReq;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentVedioctrSeqReq;
import com.iwhalecloud.retail.oms.entity.ContentVedioctr;
import com.iwhalecloud.retail.oms.exception.BaseException;
import com.iwhalecloud.retail.oms.manager.ContentBaseManager;
import com.iwhalecloud.retail.oms.manager.ContentMaterialManager;
import com.iwhalecloud.retail.oms.manager.ContentVedioctrManager;
import com.iwhalecloud.retail.oms.manager.ContentVediolv2Manager;
import com.iwhalecloud.retail.oms.manager.ContentVideosManager;
import com.iwhalecloud.retail.oms.service.ContentVedioctrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ContentVedioctrServiceImpl implements ContentVedioctrService {

    @Autowired
    private ContentVedioctrManager contentVedioctrManager;

    @Autowired
    private ContentBaseManager contentBaseManager;

    @Autowired
    private ContentMaterialManager contentMaterialManager;

    @Autowired
    private ContentVediolv2Manager contentVediolv2Manager;

    @Autowired
    private ContentVideosManager contentVideosManager;

    @Override
    @Transactional
    public Boolean addContentVedioctr(ContentVedioctrAddReq contentVedioctrAddReq) throws BaseException {
        if (contentVedioctrAddReq.getPlaybackTime() < 1){
            throw new BaseException("启动播放时长不能为0", "-1");
        }
        contentVedioctrManager.display(contentVedioctrAddReq.getStorageNum());
        if(contentVedioctrAddReq.getContentVedioctrSeqs() == null || contentVedioctrAddReq.getContentVedioctrSeqs().size() == 0){
            ContentVedioctrDTO contentVedioctrDTO = new ContentVedioctrDTO();
            Long id = System.currentTimeMillis();
            contentVedioctrDTO.setPlaybackId(id);
            contentVedioctrDTO.setPlaybackTime(contentVedioctrAddReq.getPlaybackTime());
            contentVedioctrDTO.setStorageNum(contentVedioctrAddReq.getStorageNum());
            contentVedioctrDTO.setIsPlayback(OmsConst.IsPlayback.NOT_PLAYBACK.getCode());
            contentVedioctrManager.insert(contentVedioctrDTO);
        }else{
            for (ContentVedioctrSeqReq contentVedioctrSeqReq: contentVedioctrAddReq.getContentVedioctrSeqs()){
                ContentVedioctrDTO contentVedioctrDTO = new ContentVedioctrDTO();
                contentVedioctrDTO.setContentId(contentVedioctrSeqReq.getContentId());
                contentVedioctrDTO.setPlaybackSequence(contentVedioctrSeqReq.getPlaybackSequence());
                Long id = System.currentTimeMillis();
                contentVedioctrDTO.setPlaybackId(id);
                contentVedioctrDTO.setPlaybackTime(contentVedioctrAddReq.getPlaybackTime());
                contentVedioctrDTO.setStorageNum(contentVedioctrAddReq.getStorageNum());
                contentVedioctrDTO.setIsPlayback(OmsConst.IsPlayback.HAVE_PLAYBACK.getCode());
                contentVedioctrManager.insert(contentVedioctrDTO);
            }
        }
        return true;
    }

    @Override
    public ContentVedioctrQryResp qryContentVedioctr(String storageNum) throws BaseException {
        List<ContentVedioctr> contentVedioctrs = contentVedioctrManager.selectByStorageNum(storageNum, OmsConst.IsPlayback.HAVE_PLAYBACK.getCode());
        ContentVedioctrQryResp contentVedioctrQryResp = new ContentVedioctrQryResp();
        if (contentVedioctrs == null || contentVedioctrs.size() ==0){
            contentVedioctrs = contentVedioctrManager.selectByStorageNum(storageNum, OmsConst.IsPlayback.NOT_PLAYBACK.getCode());
            contentVedioctrQryResp.setStorageNum(storageNum);
            if (contentVedioctrs != null && contentVedioctrs.size() > 0) {
                contentVedioctrQryResp.setPlaybackTime(contentVedioctrs.get(0).getPlaybackTime());
            }
            return contentVedioctrQryResp;
        }
        contentVedioctrQryResp.setStorageNum(storageNum);
        contentVedioctrQryResp.setPlaybackTime(contentVedioctrs.get(0).getPlaybackTime());
        List<ContentVedioctrSeqResp> contentVedioctrSeqResps = new ArrayList<>();
        contentVedioctrQryResp.setContentVedioctrSeqs(contentVedioctrSeqResps);
        for(ContentVedioctr contentVedioctr : contentVedioctrs){
            ContentVedioctrSeqResp contentVedioctrSeqResp = new ContentVedioctrSeqResp();
            contentVedioctrSeqResp.setContentId(contentVedioctr.getContentId());
            contentVedioctrSeqResp.setPlaybackSequence(contentVedioctr.getPlaybackSequence());
            //根据内容ID获得内容对象
            ContentBaseDTO contentBaseDTO = new ContentBaseDTO();
            contentBaseDTO.setContentId(contentVedioctr.getContentId());
            contentBaseDTO = contentBaseManager.queryContentBase(contentBaseDTO);
            //if(contentBaseDTO != null && OmsConst.ContentStatusEnum.HAVE_PUBLISH.equals(contentBaseDTO.getStatus())){
            if(contentBaseDTO != null && (OmsConst.ContentStatusEnum.HAVE_PUBLISH.getCode()==contentBaseDTO.getStatus())){
                contentVedioctrSeqResp.setContentBase(contentBaseDTO);

                List<ContentMaterialDTO> contentMaterials = contentMaterialManager.queryContentMaterialList(contentVedioctr.getContentId());

                if(contentMaterials!=null){
                    contentVedioctrSeqResp.setContentMaterials(contentMaterials);
                }

                Integer type = contentBaseDTO.getType();
                //类型 4：纯视频 5：关联视频
                if(OmsConst.ContentTypeEnum.CONTENT_VIDEO.getCode().equals(String.valueOf(type).trim())){
                    List<Long> contentIds = new ArrayList<>();
                    contentIds.add(contentVedioctr.getContentId());
                    List<ContentVideosDTO> contentVideos = contentVideosManager.queryContentVideoDefalutList(contentIds);
                    contentVedioctrSeqResp.setContentVedios(contentVideos);
                }

                if(OmsConst.ContentTypeEnum.CONTENT_VIDEO_REL.getCode().equals(String.valueOf(type).trim())) {
                    List<Long> contentIds = new ArrayList<>();
                    contentIds.add(contentVedioctr.getContentId());
                    List<ContentVideosDTO> contentVideos = contentVideosManager.queryContentVideoDefalutList(contentIds);
                    List<Long> upmatids = new ArrayList<>();
                    for (ContentVideosDTO contentVideosDTO :contentVideos) {
                        if (OmsConst.ContentVedioStatusEnum.HAVE_LV2.getCode().equals(contentVideosDTO.getHavelv2mat())) {
                            upmatids.add(contentVideosDTO.getMatid());
                        }
                    }
                    if (upmatids != null) {
                        List<ContentVediolv2DTO> contentVediolv2s = contentVediolv2Manager.queryContentVediolByUpmatid(upmatids);
                        contentVedioctrSeqResp.setContentVediolv2s(contentVediolv2s);
                    }
                }
            }
            contentVedioctrSeqResps.add(contentVedioctrSeqResp);
        }
        return contentVedioctrQryResp;
    }
}