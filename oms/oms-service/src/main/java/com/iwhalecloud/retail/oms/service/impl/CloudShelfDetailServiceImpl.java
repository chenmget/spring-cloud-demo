package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.*;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.*;
import com.iwhalecloud.retail.oms.entity.CloudShelfDetail;
import com.iwhalecloud.retail.oms.entity.OperatingPositionBind;
import com.iwhalecloud.retail.oms.exception.BaseException;
import com.iwhalecloud.retail.oms.manager.*;
import com.iwhalecloud.retail.oms.service.CloudShelfDetailService;
import com.iwhalecloud.retail.oms.service.CloudShelfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CloudShelfDetailServiceImpl implements CloudShelfDetailService {

    @Autowired
    private CloudShelfDetailManager cloudShelfDetailManager;

    @Autowired
    private ShelfDetailManager shelfDetailManager;

    @Autowired
    private DefaultOperatingManager defaultOperatingManager;

    @Autowired
    private DefaultContentManager defaultContentManager;

    @Autowired
    private ContentBaseManager contentBaseManager;

    @Autowired
    private OperatingPositionBindManager operationPositionManager;

    @Autowired
    private ShelfDetailManager ShelfDetailManager;

    @Autowired
    private CloudShelfManager cloudShelfManager;

    @Autowired
    private CloudShelfService cloudShelfService;

    @Autowired
    private ContentMaterialManager contentMaterialManager;

    @Override
    @Transactional
    public List<CloudShelfDetailDTO> resetCloudShelfDetail(CloudShelfDetailResetReq req, String userId) throws Exception {
        List<CloudShelfDetailDTO> cloudShelfDetailDTOS = new ArrayList<>();
        List<CloudShelfDetail> cloudShelfDetails = cloudShelfDetailManager.qryListByIds(req.getCloudShelfDetailIds());
        for (CloudShelfDetail cloudShelfDetail : cloudShelfDetails){
            //重置云货架栏目相关信息
            DefaultOperatingDTO defaultOperation = defaultOperatingManager.getDefaultOperation(cloudShelfDetail.getDefaultOperatingPositionId());
            if ( null == defaultOperation){
                throw new BaseException("未找到默认运营位ID【"+ cloudShelfDetail.getDefaultOperatingPositionId() +"】的默认运营为", "-1");
            }
            cloudShelfDetail.setOperatingPositionAdscription(defaultOperation.getOperatingPositionAdscription());
            cloudShelfDetail.setOperatingPositionExpiryTime(defaultOperation.getOperatingPositionExpiryTime());
            cloudShelfDetail.setOperatingPositionName(defaultOperation.getOperatingPositionName());
            cloudShelfDetail.setOperatingPositionPlayMode(defaultOperation.getOperatingPositionPlayMode());
            cloudShelfDetail.setOperatingPositionProperty(defaultOperation.getOperatingPositionProperty());
            cloudShelfDetail.setOperatingPositionType(defaultOperation.getOperatingPositionType());
            cloudShelfDetail.setModifier(userId);
            cloudShelfDetail.setGmtModified(new Date());
            cloudShelfDetailManager.update(cloudShelfDetail);
            CloudShelfDetailDTO cloudShelfDetailDTO = new CloudShelfDetailDTO();
            BeanUtils.copyProperties(cloudShelfDetail, cloudShelfDetailDTO);

            //根据默认内容 创建内容信息
//            ContentBaseDTO contentBaseDTO = defaultContentManager.CreateContentBase(defaultOperation.getDefaultContentId(), userId);
            //根据默认内容ID获得默认内容对象
            ContentBaseDTO contentBaseDTO = contentBaseManager.queryContentBaseById(NumberUtils.toLong(defaultOperation.getDefaultContentId()));
            List<ContentMaterialDTO> contentMaterials = contentMaterialManager.queryContentMaterialList(contentBaseDTO.getContentId());
            contentBaseDTO.setContentMaterials(contentMaterials);
            //关联ID
            String objIdStr = cloudShelfService.queryContentBasePersonal(contentBaseDTO.getContentId(),contentBaseDTO.getType());
            // 清除最后一位的逗号
            //objIdStr = cleanObjStr(objIdStr);
            CloudShelfDTO cloudShelf = cloudShelfManager.queryCloudShelfByNum(req.getCloudShelfNumber());
            //重置云货架栏目关联内容相关信息
            operationPositionManager.delete(cloudShelfDetail.getOperatingPositionId());
            OperatingPositionBindDTO operatingPositionBindDTO = operationPositionManager.createOperatingPositionBindDTO(cloudShelfDetail.getOperatingPositionId(),defaultOperation.getDefaultContentId(),userId,objIdStr,cloudShelf.getAdscriptionShopId());

            operatingPositionBindDTO.setContentBase(contentBaseDTO);
            cloudShelfDetailDTO.setOperatingPositionBindDTO(operatingPositionBindDTO);
            cloudShelfDetailDTOS.add(cloudShelfDetailDTO);
        }
        return cloudShelfDetailDTOS;
    }

    // 取出id多余逗号
    private String cleanObjStr(String objStr){
        String rid = ",";
        String subStr = objStr.substring(objStr.length()-1);
        if (subStr.equals(rid)){
            return objStr.substring(0,objStr.length()-1);
        }
        return objStr;
    }


    /**
     * 接口逻辑：
     * 更新云货架详情信息，更新云货架关联内容模型
     * @param cloudShelfDetailModifyReq
     * @return
     */
    @Override
    @Transactional
    public ResultVO modifyCloudShelfDetailByParam(CloudShelfDetailModifyReq cloudShelfDetailModifyReq) {
        ResultVO resultVO = new ResultVO();
        log.info("CloudShelfDetailServiceImpl modifyCloudShelfDetailByParam cloudShelfDetailModifyReq={} ",cloudShelfDetailModifyReq);
        try {
            List<OperatingPositionBindReq>  operatingPositionBindReqs = cloudShelfDetailModifyReq.getOperatingPositionBind();
            if(null != operatingPositionBindReqs && operatingPositionBindReqs.size()>0){
                // 补充商品关联ID add by lin.qi
                for (OperatingPositionBindReq req : operatingPositionBindReqs){
                    if (req.getProductNumber() != null && "".equals(req.getProductNumber())){
                        continue;
                    }
                    ContentBaseDTO contentBaseDTO = contentBaseManager.queryContentBaseById(NumberUtils.toLong(req.getContentNumber()));
                    String objIdStr = cloudShelfService.queryContentBasePersonal(contentBaseDTO.getContentId(),contentBaseDTO.getType());
                    //objIdStr = cleanObjStr(objIdStr);
                    req.setProductNumber(objIdStr);
                }
                operationPositionManager.batchUpdateOperatingPositionBind(operatingPositionBindReqs);
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                resultVO.setResultData("更新成功");
                resultVO.setResultMsg("success");
            }
        }catch (Exception e) {
            log.info("CloudShelfDetailServiceImpl modifyCloudShelfDetailByParam Exception={} ",e);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultData(e.getMessage());
        }
        return resultVO;
    }

    @Override
    @Transactional
    public ResultVO updateCloudShelfDetailByAction(CloudShelfActionReq cloudShelfActionReq) {
        ResultVO resultVO = new ResultVO();
        List<CloudShelfAction> cloudShelfActions = cloudShelfActionReq.getCloudShelfActions();
        List<ShelfDetailDTO> shelfDetailDTOList = new ArrayList<ShelfDetailDTO>();
        if(cloudShelfActions.size()>0){
            for (int i = 0; i < cloudShelfActions.size() ; i++) {
                if("add".equals(cloudShelfActions.get(i).getAction())){
                    CloudShelfDetailDTO cloudShelfDetailDTO = new CloudShelfDetailDTO();
                    cloudShelfDetailDTO.setShelfCategoryId(cloudShelfActions.get(i).getShelfCategoryId());
                    cloudShelfDetailDTO.setCloudShelfNumber(cloudShelfActionReq.getCloudShelfNumber());
                    List<ShelfDetailDTO> shelfDetailDTOS = shelfDetailManager.queryCloudShelfDetailList(cloudShelfDetailDTO);
                    if(shelfDetailDTOS.size()>0){
                        //copy一份最近云货架详情记录
                        ShelfDetailDTO shelfDetailDTO = shelfDetailDTOS.get(0);
                        shelfDetailDTO = shelfDetailManager.insertShelfDetail(shelfDetailDTO);
                        shelfDetailDTOList.add(shelfDetailDTO);
                        //同时插入运营位置
                        OperatingPositionBind operatingPositionBind = new OperatingPositionBind();
                        operatingPositionBind.setIsDeleted(0);
                        operatingPositionBind.setOperatingPositionId(shelfDetailDTO.getOperatingPositionId());
                        operationPositionManager.insert(operatingPositionBind);
                    }
                }
                if("delete".equals(cloudShelfActions.get(i).getAction())){
                    CloudShelfDetail cloudShelfDetail = new CloudShelfDetail();
                    cloudShelfDetail.setId(cloudShelfActions.get(i).getId());
                    cloudShelfDetailManager.delete(cloudShelfDetail);
                }
            }
        }
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(shelfDetailDTOList);
        resultVO.setResultMsg("success");
        return resultVO;
    }

    @Override
    public List<ShelfDetailDTO> queryCloudShelfDetailByProductId(String productId) throws Exception {
        return shelfDetailManager.queryCloudShelfDetailByProductId(productId);
    }

}