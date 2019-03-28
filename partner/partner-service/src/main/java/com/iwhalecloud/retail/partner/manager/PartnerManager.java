package com.iwhalecloud.retail.partner.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.PartnerDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerGetReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerPageReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierReq;
import com.iwhalecloud.retail.partner.entity.Partner;
import com.iwhalecloud.retail.partner.mapper.PartnerMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class PartnerManager{
    @Resource
    private PartnerMapper partnerMapper;

    public Page<PartnerDTO> querySupplyRel(SupplierReq supplierReq){
        Page<PartnerDTO> supplierDTOPage = new Page<PartnerDTO>(supplierReq.getPageNo(),supplierReq.getPageSize());
        supplierDTOPage = partnerMapper.querySupplyRel(supplierDTOPage,supplierReq);
        List<PartnerDTO> pageRecords = supplierDTOPage.getRecords();
        if(pageRecords.size()>0){
            for (int i = 0; i <pageRecords.size() ; i++) {
                pageRecords.get(i).setSupplierId(supplierReq.getSupplierId());
            }
        }
        return  supplierDTOPage;
    }


   public List<PartnerDTO> getPartnerListByShopId(String partnerShopId){
       Map<String, Object> map = new HashMap<>();
       map.put("partner_shop_id", partnerShopId);
       List<Partner> list = partnerMapper.selectByMap(map);
       List<PartnerDTO> resultList = new ArrayList<>();
       for (Partner partner : list){
           PartnerDTO partnerDTO = new PartnerDTO();
           BeanUtils.copyProperties(partner, partnerDTO);
           resultList.add(partnerDTO);
       }
       return resultList;
   }

    /**
     * 根据在页面上选择的代理商名称，状态查询代理商列表
     * @param pageReq
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    public Page<PartnerDTO> pagePartner(PartnerPageReq pageReq) {
        Page<PartnerDTO> page = new Page<PartnerDTO>(pageReq.getPageNo(), pageReq.getPageSize());
        if (null != pageReq.getStates()
                && pageReq.getStates().size() == 0) {
            pageReq.setStates(null);
        }
        page = partnerMapper.pagePartner(page, pageReq);
        return page;
    }

    public PartnerDTO getPartner(PartnerGetReq req){
        QueryWrapper<Partner> queryWrapper = new QueryWrapper<Partner>();
        if(!StringUtils.isEmpty(req.getPartnerId())){
            queryWrapper.eq(Partner.FieldNames.partnerId.getTableFieldName(), req.getPartnerId());
        }
        if(!StringUtils.isEmpty(req.getPartnerCode())){
            queryWrapper.eq(Partner.FieldNames.partnerCode.getTableFieldName(), req.getPartnerCode());
        }
        List<Partner> partnerList = partnerMapper.selectList(queryWrapper);
        PartnerDTO partnerDTO = new PartnerDTO();
        if(CollectionUtils.isEmpty(partnerList)){
            return null;
        }
        BeanUtils.copyProperties(partnerList.get(0), partnerDTO);
        return partnerDTO;
    }

}
