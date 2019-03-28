package com.iwhalecloud.retail.partner.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.PartnerStaffDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffAddReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffDeleteReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffPageReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffUpdateReq;
import com.iwhalecloud.retail.partner.entity.PartnerStaff;
import com.iwhalecloud.retail.partner.mapper.PartnerStaffMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Component
public class PartnerStaffManager {

    @Resource
    PartnerStaffMapper partnerStaffMapper;

    /**
     * 添加一个 店员
     * @param partnerStaffAddReq
     * @return
     */
    public PartnerStaffDTO addPartnerStaff(PartnerStaffAddReq partnerStaffAddReq){
        PartnerStaff partnerStaff = new PartnerStaff();
        BeanUtils.copyProperties(partnerStaffAddReq, partnerStaff);
        partnerStaff.setCreateDate(new Date());
        int resultInt = partnerStaffMapper.insert(partnerStaff);
        if(resultInt > 0){
            PartnerStaffDTO partnerStaffDTO = new PartnerStaffDTO();
            BeanUtils.copyProperties(partnerStaff, partnerStaffDTO);
            return partnerStaffDTO;
        }
        return null;
    }

    public PartnerStaffDTO selectOne(String staffId){
        PartnerStaff partnerStaff = partnerStaffMapper.selectById(staffId);
        if(partnerStaff == null){
            return null;
        }
        PartnerStaffDTO partnerStaffDTO = new PartnerStaffDTO();
        BeanUtils.copyProperties(partnerStaff, partnerStaffDTO);
        return partnerStaffDTO;
    }

    /**
     * 编辑店员信息
     * @param partnerStaffUpdateReq
     * @return
     */
    public int editPartnerStaff(PartnerStaffUpdateReq partnerStaffUpdateReq){
        PartnerStaff partnerStaff = new PartnerStaff();
        BeanUtils.copyProperties(partnerStaffUpdateReq, partnerStaff);
        return partnerStaffMapper.updateById(partnerStaff);
    }

    /**
     * 批量删除 店员
     * @param req  req.getStaffIds()要删除的店员staffId 集合
     * @return
     */
    public int deletePartnerStaff(PartnerStaffDeleteReq req){
        return partnerStaffMapper.deleteBatchIds(req.getStaffIds());
    }

    /**
     * 获取店员列表 （可以根据条件筛选）
     * @param pageReq
     * @return
     */
    public Page<PartnerStaffDTO> getPartnerStaffList(PartnerStaffPageReq pageReq){
        Page<PartnerStaffDTO> page = new Page<PartnerStaffDTO>(pageReq.getPageNo(), pageReq.getPageSize());
        return partnerStaffMapper.qryPartnerStaffList(page, pageReq);
    }

}
