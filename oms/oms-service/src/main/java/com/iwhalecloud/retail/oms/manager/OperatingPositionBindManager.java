package com.iwhalecloud.retail.oms.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.BindContentDTO;
import com.iwhalecloud.retail.oms.dto.BindProductDTO;
import com.iwhalecloud.retail.oms.dto.ContentMaterialDTO;
import com.iwhalecloud.retail.oms.dto.OperatingPositionBindDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.OperatingPositionBindReq;
import com.iwhalecloud.retail.oms.entity.OperatingPositionBind;
import com.iwhalecloud.retail.oms.mapper.CloudShelfMapper;
import com.iwhalecloud.retail.oms.mapper.OperatingPositionBindMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/22 00:13
 * @Description:
 */

@Component
public class OperatingPositionBindManager {

    @Resource
    private OperatingPositionBindMapper operatingPositionBindMapper;

    @Resource
    private CloudShelfMapper cloudShelfMapper;

    public int createBindProduct(BindProductDTO dto) {
        int t = operatingPositionBindMapper.createBindProduct(dto);
        return t;
    }

    public int createBindContent(BindContentDTO dto) {
        int t = operatingPositionBindMapper.createBindContent(dto);
        return t;
    }

    /**
     * 新增.
     *
     * @param operatingPositionBind
     * @return
     * @author Ji.kai
     * @date 2018/10/31 15:27
     */
    public int insert(OperatingPositionBind operatingPositionBind) {
        operatingPositionBind.setGmtCreate(new Date());
        operatingPositionBind.setGmtModified(new Date());
        return operatingPositionBindMapper.insert(operatingPositionBind);
    }

    /**
     * 新增云货架栏目关联内容相关信息.
     *
     * @param operatingPositionId
     * @param contentId
     * @param userId
     * @return
     * @author Ji.kai
     * @date 2018/10/31 15:27
     */
    public OperatingPositionBindDTO createOperatingPositionBindDTO(String operatingPositionId, String contentId, String userId,String objIdStr,String adscriptionShopId) {
        OperatingPositionBind operatingPositionBind = new OperatingPositionBind();
        operatingPositionBind.setOperatingPositionId(operatingPositionId);
        operatingPositionBind.setContentNumber(contentId);
        operatingPositionBind.setCreator(userId);
        operatingPositionBind.setModifier(userId);
        operatingPositionBind.setIsDeleted(OmsConst.IsDeleted.NOT_DELETED.getCode());
        operatingPositionBind.setProductNumber(objIdStr);
        operatingPositionBind.setAdscriptionShopId(adscriptionShopId);
        insert(operatingPositionBind);
        OperatingPositionBindDTO operatingPositionBindDTO = new OperatingPositionBindDTO();
        BeanUtils.copyProperties(operatingPositionBind, operatingPositionBindDTO);
        // 查出附带素材 add by lin.qi
        long contentIdL = Long.parseLong(contentId);
        List<ContentMaterialDTO> contentMaterialDTO = cloudShelfMapper.queryCloudShelfListDetails4(contentIdL);
        if (contentMaterialDTO.size() > 0) {
            operatingPositionBindDTO.setContentMaterials(contentMaterialDTO);
        } else {
            operatingPositionBindDTO.setContentMaterials(null);
        }
        return operatingPositionBindDTO;
    }

    /**
     * 失效.
     *
     * @param operatingPositionId
     * @return
     * @author Ji.kai
     * @date 2018/10/31 15:27
     */
    public int delete(String operatingPositionId) {
        return operatingPositionBindMapper.setIsDeleted(operatingPositionId);
    }

    /**
     * 批量更新运营位关联信息
     * @param operatingPositionBindReqs
     * @return
     */
    public int batchUpdateOperatingPositionBind(List<OperatingPositionBindReq> operatingPositionBindReqs){
        return operatingPositionBindMapper.batchUpdateOperatingPositionBind(operatingPositionBindReqs);
    }

    /**
     * 根据门店Id查询列表
     * @param adscriptionShopId
     * @return
     * @author Ji.kai
     * @date 2018/11/8 15:27
     */
    public List<OperatingPositionBind> getByAdscriptionShopId(String adscriptionShopId){
        QueryWrapper<OperatingPositionBind> qw = new QueryWrapper<>();
        qw.eq(OperatingPositionBind.FieldNames.adscriptionShopId.getTableFieldName(), adscriptionShopId);
        return operatingPositionBindMapper.selectList(qw);
    }

    //该表中ContentNumber置为空
    public int unbindContentId(String contentId){
        return operatingPositionBindMapper.unbindContentId(contentId);
    }

}

