package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.*;
import com.iwhalecloud.retail.oms.dto.resquest.CloudShelfPageReq;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudShelfAddReq;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudShelfRequestDTO;
import com.iwhalecloud.retail.oms.entity.CloudShelf;
import com.iwhalecloud.retail.oms.exception.BaseException;
import com.iwhalecloud.retail.oms.manager.*;
import com.iwhalecloud.retail.oms.service.CloudShelfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/15 10:28
 * @Description: 云货架实现类
 */

@Slf4j
@Service
public class CloudShelfServiceImpl implements CloudShelfService {

    @Autowired
    private CloudShelfManager cloudShelfManager;
    @Autowired
    private CloudDeviceManager cloudDeviceManager;
    @Autowired
    private DefaultCategoryManager defaultCategoryManager;

    @Autowired
    private CloudShelfDetailManager cloudShelfDetailManager;

    @Autowired
    private DefaultOperatingManager defaultOperatingManager;

    @Autowired
    private DefaultContentManager defaultContentManager;

    @Autowired
    private OperatingPositionBindManager operationPositionManager;

    @Autowired
    private ShelfDetailManager shelfDetailManager;

    @Autowired
    private ContentBaseManager contentBaseManager;

    @Resource
    private ContentPicManager contentPicManager ;

    @Resource
    private ContentOrderpicManager contentOrderpicManager;

    @Resource
    private ContentPicsetManager contentPicsetManager;

    @Resource
    private ContentVideosManager contentVideosManager;

    @Resource
    private ContentVediolv2Manager contentVediolv2Manager;

    @Resource
    private ContentMaterialManager contentMaterialManager;

    @Resource
    private CloudShelfBindUserManager cloudShelfBindUserManager;

    @Override
    public Page<CloudShelfDTO> queryCloudShelfList(CloudShelfPageReq page) {
        return cloudShelfManager.queryCloudShelfList(page);
    }

    @Override
    public CloudShelfDTO queryCloudShelfListDetails(String num, Boolean isQryDevice) throws ParseException, BaseException{
        CloudShelfDTO cloudShelfDTO = cloudShelfManager.queryCloudShelfListDetails(num, isQryDevice);
        cloudShelfDTO.setCloudShelfBindUsers(cloudShelfBindUserManager.qryByCloudShelfNum(num));
        return cloudShelfDTO;
    }

    @Override
    @Transactional
    public CloudShelfDTO createCloudShelf(CloudShelfAddReq req, String userId) throws ParseException, BaseException {

        CloudShelfDTO cloudShelfDTO = new CloudShelfDTO();
        List<CloudShelfDetailDTO> cloudShelfDetailDTOS = new ArrayList<>();
        cloudShelfDTO.setCloudShelfDetailDTOS(cloudShelfDetailDTOS);

        List<CloudShelf> cloudShelves = cloudShelfManager.getCloudShelfByName(req.getShelfName());
        if (cloudShelves != null && cloudShelves.size() > 0){
            throw new BaseException("云货架名称【" + req.getShelfName() + "】已存在！", "-1");
        }
        //新增云货架基础类
        cloudShelfDTO.setShelfTemplatesNumber(req.getShelfTemplatesNumber());
        Long id = System.currentTimeMillis();
        cloudShelfDTO.setNum(id.toString());
        cloudShelfDTO.setName(req.getShelfName());
        cloudShelfDTO.setIsDeleted(OmsConst.IsDeleted.NOT_DELETED.getCode());
        cloudShelfDTO.setStatus(OmsConst.CloudShelfStatus.USED.getCode());
        cloudShelfDTO.setCreator(userId);
        cloudShelfDTO.setAdscriptionShopId(req.getAdscriptionShopId());
        cloudShelfDTO.setAdscriptionCity(req.getAdscriptionCity());
        cloudShelfDTO.setModifier(userId);
        cloudShelfManager.createCloudShelf(cloudShelfDTO);

        for (String shelfCategoryId : req.getShelfCategoryIds()) {
            //新增云货架栏目相关信息
            CloudShelfDetailDTO cloudShelfDetail = new CloudShelfDetailDTO();
            List<DefaultOperatingDTO> defaultOperations = defaultOperatingManager.qryDefaultOperation(shelfCategoryId);
            if (defaultOperations == null && defaultOperations.size() == 0) {
                throw new BaseException("未能找到类目ID【" + shelfCategoryId + "】下相关运营位信息", "-1");
            }
            for (DefaultOperatingDTO defaultOperation : defaultOperations){
                BeanUtils.copyProperties(defaultOperation, cloudShelfDetail);
                cloudShelfDetail.setId(null);
                cloudShelfDetail.setCloudShelfNumber(cloudShelfDTO.getNum());
                id = System.currentTimeMillis();
                cloudShelfDetail.setOperatingPositionId(id.toString());
                cloudShelfDetail.setDefaultOperatingPositionId(defaultOperation.getOperatingPositionId());
                cloudShelfDetail.setShelfCategoryId(shelfCategoryId);
                cloudShelfDetail.setCreator(userId);
                cloudShelfDetail.setModifier(userId);
                cloudShelfDetail.setIsDeleted(OmsConst.IsDeleted.NOT_DELETED.getCode());
                cloudShelfDetailManager.insert(cloudShelfDetail);

                //根据默认内容 创建内容信息
//            ContentBaseDTO contentBaseDTO = defaultContentManager.CreateContentBase(defaultOperation.getDefaultContentId(), userId);
                //根据默认内容ID获得默认内容对象
                ContentBaseDTO contentBaseDTO = contentBaseManager.queryContentBaseById(NumberUtils.toLong(defaultOperation.getDefaultContentId()));
                //关联ID
                String objIdStr = queryContentBasePersonal(contentBaseDTO.getContentId(),contentBaseDTO.getType());
                //新增云货架栏目关联内容相关信息
                OperatingPositionBindDTO operatingPositionBindDTO = operationPositionManager.createOperatingPositionBindDTO(cloudShelfDetail.getOperatingPositionId(), defaultOperation.getDefaultContentId(), userId,objIdStr,req.getAdscriptionShopId());

                operatingPositionBindDTO.setContentBase(contentBaseDTO);
                cloudShelfDetail.setOperatingPositionBindDTO(operatingPositionBindDTO);
                cloudShelfDetailDTOS.add(cloudShelfDetail);
            }
        }
        if (req.getDevices() != null && req.getDevices().size() > 0) {
            cloudDeviceManager.updateCloudDeviceByNum(req.getDevices(), cloudShelfDTO.getNum());
        }
        List<String> userIds = req.getUserIds();
        if (userIds != null && userIds.size() > 0) {
            for(String bindUserId: userIds){
                CloudShelfBindUserDTO cloudShelfBindUserDTO = new CloudShelfBindUserDTO();
                cloudShelfBindUserDTO.setUserId(bindUserId);
                cloudShelfBindUserDTO.setCreator(userId);
                cloudShelfBindUserDTO.setModifier(userId);
                cloudShelfBindUserDTO.setIsDeleted(OmsConst.IsDeleted.NOT_DELETED.getCode());
                cloudShelfBindUserDTO.setCloudShelfNumber(cloudShelfDTO.getNum());
                cloudShelfBindUserManager.insert(cloudShelfBindUserDTO);
            }
        }
        return cloudShelfDTO;
    }

    @Override
    public int updateCloudShelfStatus(CloudShelfDTO dto) {
        int t = cloudShelfManager.editCloudShelf(dto);
        if (dto.getStatus() == OmsConst.CloudShelfStatus.NO_USED.getCode()){
            // 重置设备关联的云货架
            int updateNum = resetRelShelfNum(dto.getNum());
        }
        return t;
    }

    private int resetRelShelfNum(String shelfNum){
        int updateNum = 0;
        List<CloudDeviceDTO> cloudDeviceDTOList = cloudDeviceManager.queryCloudDeviceBycloudShelfNumber(shelfNum);
        if(cloudDeviceDTOList.size()>0) {
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < cloudDeviceDTOList.size(); i++) {
                list.add(cloudDeviceDTOList.get(i).getNum());
            }
            //重置为空
            updateNum = cloudDeviceManager.updateCloudDeviceByNum(list, "");
        }
        return updateNum;
    }

    @Override
    public int deleteCloudShelf(CloudShelfDTO dto) {
        int t = cloudShelfManager.deleteCloudShelf(dto);
        return t;
    }

    /**
     * 接口逻辑：
     * (1)云货架number,货架名称,状态更新模型cloud_shlef
     * (2)关联设备列表根据
     * 首先通过编码查找已经关联的设备把设备表的关联编码清空，然后根据请求传入的参数 找到对应的设备填上编码
     * 操作number
     * (3)货架类目列表
     * 根据云货架编码查询原本的货架类目信息，对比传入的货架类目列表，进行新增，删除，修改
     *
     * @param cloudShelfRequestDTO
     * @return
     */
    @Override
    @Transactional
    public ResultVO modifyCloudShelfByParam(CloudShelfRequestDTO cloudShelfRequestDTO, String userId) {
        ResultVO resultVO = new ResultVO();
        log.info("CloudShelfServiceImpl modifyCloudShelfByParam cloudShelfRequestDTO={} ", cloudShelfRequestDTO);
        try {
            int modifyReturnNumber = cloudShelfManager.modifyCloudShelfByParam(cloudShelfRequestDTO);
            String num = cloudShelfRequestDTO.getNum();
            Long date = System.currentTimeMillis();
               if (modifyReturnNumber > 0) {
                CloudDeviceDTO deleteCloudDeviceDTO = new CloudDeviceDTO();
                deleteCloudDeviceDTO.setCloudShelfNumber(num);
                List<CloudDeviceDTO> cloudDeviceDTOList = cloudDeviceManager.queryCloudDeviceBycloudShelfNumber(num);
                if(cloudDeviceDTOList.size()>0) {
                    List<String> list = new ArrayList<String>();
                    for (int i = 0; i < cloudDeviceDTOList.size(); i++) {
                        list.add(cloudDeviceDTOList.get(i).getNum());
                    }
                    //重置为空
                    int updateNum = cloudDeviceManager.updateCloudDeviceByNum(list, "");
                }
                        List<String>  cloudDeviceList = cloudShelfRequestDTO.getCloudDevice();
                        if(cloudDeviceList.size()>0){
                            for (int i = 0; i < cloudDeviceList.size(); i++) {
                                CloudDeviceDTO cloudDeviceReqDTO = new CloudDeviceDTO();
                                cloudDeviceReqDTO.setNum(cloudDeviceList.get(i));
                                cloudDeviceReqDTO.setCloudShelfNumber(num);
                                cloudDeviceReqDTO.setGmtCreate(new Date());
                                cloudDeviceReqDTO.setStatus(OmsConst.CloudDeviceStatus.USED.getCode());
                                cloudDeviceReqDTO.setIsDeleted(OmsConst.IsDeleted.NOT_DELETED.getCode());
                                CloudDeviceDTO cloudDeviceDTO = new CloudDeviceDTO();
                                BeanUtils.copyProperties(cloudDeviceReqDTO,cloudDeviceDTO);
                                cloudDeviceManager.editCloudDevice(cloudDeviceDTO);
                            }
                        }

                HashMap<String, Object> map = new HashMap<String, Object>();
                List<ShelfDetailDTO> existCloudShelfList = shelfDetailManager.queryShelfDetailList(num);
                List<DefaultCategoryDTO> defaultCategorys = cloudShelfRequestDTO.getShelfCategory();
                if(defaultCategorys!=null){
                    for (int i = 0; i < defaultCategorys.size(); i++) {
                        if("add".equals(defaultCategorys.get(i).getAction())){
                            CloudShelfDetailDTO cloudShelfDetail = new CloudShelfDetailDTO();
                            String shelfCategoryId  = String.valueOf(defaultCategorys.get(i).getId());
                            List<DefaultOperatingDTO> defaultOperations = defaultOperatingManager.qryDefaultOperation(shelfCategoryId);
                            if (defaultOperations == null && defaultOperations.size() == 0) {
                                throw new BaseException("未能找到类目ID【" + shelfCategoryId + "】下相关运营位信息", "-1");
                            }
                            for (DefaultOperatingDTO defaultOperation : defaultOperations){
                                BeanUtils.copyProperties(defaultOperation, cloudShelfDetail);
                                cloudShelfDetail.setId(null);
                                cloudShelfDetail.setCloudShelfNumber(num);
                                date = System.currentTimeMillis();
                                cloudShelfDetail.setOperatingPositionId(date.toString());
                                cloudShelfDetail.setDefaultOperatingPositionId(defaultOperation.getOperatingPositionId());
                                cloudShelfDetail.setShelfCategoryId(shelfCategoryId);
                                cloudShelfDetail.setCreator("");
                                cloudShelfDetail.setModifier("");
                                cloudShelfDetail.setIsDeleted(OmsConst.IsDeleted.NOT_DELETED.getCode());
                                cloudShelfDetailManager.insert(cloudShelfDetail);
                                //根据默认内容 创建内容信息
//                                ContentBaseDTO contentBaseDTO = defaultContentManager.CreateContentBase(defaultOperation.getDefaultContentId(), "");
                                //根据默认内容ID获得默认内容对象
                                ContentBaseDTO contentBaseDTO = contentBaseManager.queryContentBaseById(NumberUtils.toLong(defaultOperation.getDefaultContentId()));
                                //关联ID
                                String objIdStr = queryContentBasePersonal(contentBaseDTO.getContentId(),contentBaseDTO.getType());
                                CloudShelfDTO cloudShelfDTO = cloudShelfManager.queryCloudShelfByNum(num);
                                //新增云货架栏目关联内容相关信息
                                OperatingPositionBindDTO operatingPositionBindDTO = operationPositionManager.createOperatingPositionBindDTO(cloudShelfDetail.getOperatingPositionId(), contentBaseDTO.getContentId().toString(), "",objIdStr,cloudShelfDTO.getAdscriptionShopId());
                            }
                        }
                        if("delete".equals(defaultCategorys.get(i).getAction())){
                            ShelfDetailDTO shelfDetailDTO = new ShelfDetailDTO();
                            shelfDetailDTO.setCloudShelfNumber(num);
                            shelfDetailDTO.setShelfCategoryId(String.valueOf(defaultCategorys.get(i).getId()));
                            shelfDetailManager.deleteShelfDetail(shelfDetailDTO);
                        }
                    }
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                    resultVO.setResultData("更新成功");
                    resultVO.setResultMsg("success");
                }
                // 更新货架人员中间表
                updateShelfUserRef(cloudShelfRequestDTO, userId);

            }
        } catch (Exception e) {
            log.info("CloudShelfServiceImpl modifyCloudShelfByParam Exception={} ", e);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultData(e.getMessage());
        }
        return resultVO;
    }

    /** 更新货架人员中间表*/
    private void updateShelfUserRef(CloudShelfRequestDTO dto,String userId){
        List<String> userIds = dto.getUserIds();
        if (userIds != null && userIds.size() > 0) {
            int delCount = cloudShelfBindUserManager.deleteByShelfNum(dto.getNum());
            log.info("modifyCloudShelfByParam => updateShelfUserRef, DELETE" + delCount + "rows data.");
            for(String bindUserId: userIds){
                CloudShelfBindUserDTO cloudShelfBindUserDTO = new CloudShelfBindUserDTO();
                cloudShelfBindUserDTO.setUserId(bindUserId);
                cloudShelfBindUserDTO.setCreator(userId);
                cloudShelfBindUserDTO.setModifier(userId);
                cloudShelfBindUserDTO.setIsDeleted(OmsConst.IsDeleted.NOT_DELETED.getCode());
                cloudShelfBindUserDTO.setCloudShelfNumber(dto.getNum());
                cloudShelfBindUserManager.insert(cloudShelfBindUserDTO);
            }
            log.info("modifyCloudShelfByParam => updateShelfUserRef, ADD" + delCount + "rows data.");
        }
    }


    /**
     * 根据内容类型判断关联ID
     * @param contentId
     * @return
     */
    @Override
    public String queryContentBasePersonal(Long contentId,Integer type){
        String  objIdStr = new String();
        Set<String> objIdStrSet = new HashSet<String>();
        if (null == type){
            return null;
        }
        if(OmsConst.ContentTypeEnum.CONTENT_PIC.getCode().equals(String.valueOf(type).trim())){
            List<ContentPicDTO> contentPicDTOS = contentPicManager.queryContentPicList(contentId);
            if(contentPicDTOS.size()>0){
                for (int i = 0; i < contentPicDTOS.size() ; i++) {
                    if(String.valueOf(OmsConst.ContentObjType.GOODS.getCode()).equals(String.valueOf(contentPicDTOS.get(i).getObjtype()))){
//                        objIdStr += contentPicDTOS.get(i).getObjid()+",";
                        objIdStrSet.add(contentPicDTOS.get(i).getObjid());
                    }
                }
            }
        }
        if(OmsConst.ContentTypeEnum.CONTENT_ORDERPIC.getCode().equals(String.valueOf(type).trim())){
            List<ContentOrderpicDTO>  contentOrderpicDTOS = contentOrderpicManager.queryContentOrderPicList(contentId);
            if(contentOrderpicDTOS.size()>0){
                for (int i = 0; i < contentOrderpicDTOS.size() ; i++) {
                    if(String.valueOf(OmsConst.ContentObjType.GOODS.getCode()).equals(String.valueOf(contentOrderpicDTOS.get(i).getObjtype()))){
//                        objIdStr += contentOrderpicDTOS.get(i).getObjid()+",";
                        objIdStrSet.add(contentOrderpicDTOS.get(i).getObjid());
                    }
                }
            }
        }
        if(OmsConst.ContentTypeEnum.CONTENT_PICSET.getCode().equals(String.valueOf(type).trim())){
            List<ContentPicsetDTO> contentPicsetDTOS = contentPicsetManager.queryContentPicsetList(contentId);
            if(contentPicsetDTOS.size()>0){
                for (int i = 0; i < contentPicsetDTOS.size() ; i++) {
                    if(String.valueOf(OmsConst.ContentObjType.GOODS.getCode()).equals(String.valueOf(contentPicsetDTOS.get(i).getObjtype()))){
//                        objIdStr += contentPicsetDTOS.get(i).getObjid()+",";
                        objIdStrSet.add(contentPicsetDTOS.get(i).getObjid());
                    }
                }
            }
        }
        List<Long> upmatids = new ArrayList<>();
        if(OmsConst.ContentTypeEnum.CONTENT_VIDEO.getCode().equals(String.valueOf(type).trim())){
            List<Long> contentIds = new ArrayList<>();
            contentIds.add(contentId);
            List<ContentVideosDTO>  contentVideosDTOS = contentVideosManager.queryContentVideoDefalutList(contentIds);
            if(contentVideosDTOS.size()>0){
                for (int i = 0; i < contentVideosDTOS.size() ; i++) {
                    if(String.valueOf(OmsConst.ContentObjType.GOODS.getCode()).equals(String.valueOf(contentVideosDTOS.get(i).getObjtype()))){
//                        objIdStr += contentVideosDTOS.get(i).getObjid()+",";
                        objIdStrSet.add(contentVideosDTOS.get(i).getObjid());
                        if("1".equals(String.valueOf(contentVideosDTOS.get(i).getHavelv2mat()))){
                            upmatids.add(contentVideosDTOS.get(i).getMatid());
                        }
                    }
                }
            }
        }
        if(OmsConst.ContentTypeEnum.CONTENT_VIDEO_REL.getCode().equals(String.valueOf(type).trim())){
            if(upmatids!=null){
                List<ContentVediolv2DTO>  contentVediolv2s = contentVediolv2Manager.queryContentVediolByUpmatid(upmatids);
                if(contentVediolv2s.size()>0){
                    for (int i = 0; i < contentVediolv2s.size() ; i++) {
                        if(String.valueOf(OmsConst.ContentObjType.GOODS.getCode()).equals(String.valueOf(contentVediolv2s.get(i).getObjtype()))){
//                            objIdStr += contentVediolv2s.get(i).getObjid()+",";
                            objIdStrSet.add(contentVediolv2s.get(i).getObjid());
                        }
                    }
                }
            }
        }
        Iterator<String> it = objIdStrSet.iterator();
        while (it.hasNext()) {
            objIdStr += it.next() +",";
        }
        return objIdStr;
    }

}

