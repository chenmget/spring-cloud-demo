package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.CloudShelfDetailDTO;
import com.iwhalecloud.retail.oms.dto.ShelfTemplatesDetailDTO;
import com.iwhalecloud.retail.oms.entity.CloudShelfDetail;
import com.iwhalecloud.retail.oms.entity.ShelfTemplatesDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.oms.mapper.ShelfTemplatesDetailMapper;

import java.util.Date;


@Component
public class ShelfTemplatesDetailManager{
    @Resource
    private ShelfTemplatesDetailMapper shelfTemplatesDetailMapper;

    /**
     * 新增.
     *
     * @param operatingPositionId
     * @param defCategoryId
     * @param tempNumber
     * @param userId
     * @return
     * @author Ji.kai
     * @date 2018/11/13 15:27
     */
    public ShelfTemplatesDetailDTO insert(String operatingPositionId, String defCategoryId, String tempNumber, String userId) {
        ShelfTemplatesDetail shelfTemplatesDetail = new ShelfTemplatesDetail();
        shelfTemplatesDetail.setGmtCreate(new Date());
        shelfTemplatesDetail.setGmtModified(new Date());
        shelfTemplatesDetail.setOperatingPositionId(operatingPositionId);
        shelfTemplatesDetail.setDefCategoryId(defCategoryId);
        shelfTemplatesDetail.setShelfTemplatesNumber(tempNumber);
        shelfTemplatesDetail.setCreator(userId);
        shelfTemplatesDetail.setModifier(userId);
        shelfTemplatesDetail.setIsDeleted(OmsConst.IsDeleted.NOT_DELETED.getCode());
        shelfTemplatesDetailMapper.insert(shelfTemplatesDetail);
        ShelfTemplatesDetailDTO shelfTemplatesDetailDTO = new ShelfTemplatesDetailDTO();
        BeanUtils.copyProperties(shelfTemplatesDetail, shelfTemplatesDetailDTO);
        return shelfTemplatesDetailDTO;
    }
    /**
     * 删除.
     *
     * @param id
     * @return
     * @author Ji.kai
     * @date 2018/11/13 15:27
     */
    public int delete(Long id) {
        return shelfTemplatesDetailMapper.deleteById(id);
    }

}
