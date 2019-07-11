package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDetailDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstsTrackDetailGetReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstTrackDetailListResp;
import com.iwhalecloud.retail.warehouse.entity.ResouceInstTrackDetail;
import com.iwhalecloud.retail.warehouse.mapper.ResouceInstTrackDetailMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class ResouceInstTrackDetailManager extends ServiceImpl<ResouceInstTrackDetailMapper, ResouceInstTrackDetail> {

    @Resource
    private ResouceInstTrackDetailMapper resouceInstTrackDetailMapper;

    public int saveResouceInstTrackDetail(ResouceInstTrackDetailDTO resouceInstTrackDTO) {
        ResouceInstTrackDetail resouceInstTrackDetail = new ResouceInstTrackDetail();
        BeanUtils.copyProperties(resouceInstTrackDTO, resouceInstTrackDetail);
        return resouceInstTrackDetailMapper.insert(resouceInstTrackDetail);
    }

    /**
     * 通过串码查轨迹明细
     * @param req
     * @return
     */
    public List<ResourceInstTrackDetailListResp> getResourceInstTrackDetailByNbr(ResourceInstsTrackDetailGetReq req) {
        return resouceInstTrackDetailMapper.getResourceInstTrackDetailByNbr(req);
    }

    /**
     * 通过串码查轨厂商仓库ID（第一条明细数据是厂商录入串码）
     * @param mktResInstNbr
     * @return
     */
    public String getMerchantStoreId(String mktResInstNbr) {
        return resouceInstTrackDetailMapper.getMerchantStoreId(mktResInstNbr);
    }
}
