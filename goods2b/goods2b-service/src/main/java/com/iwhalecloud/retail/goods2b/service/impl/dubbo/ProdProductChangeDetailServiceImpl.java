package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods2b.dto.ProdProductChangeDTO;
import com.iwhalecloud.retail.goods2b.dto.ProdProductChangeDetailDTO;
import com.iwhalecloud.retail.goods2b.entity.ProdProductChangeDetail;
import com.iwhalecloud.retail.goods2b.manager.ProdProductChangeDetailManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProdProductChangeDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/5/16.
 */
@Slf4j
@Service
public class ProdProductChangeDetailServiceImpl implements ProdProductChangeDetailService {
    @Autowired
    private ProdProductChangeDetailManager prodProductChangeDetailManager;

    @Override
    public String insert(ProdProductChangeDetailDTO prodProductChangeDetailDTO) {
        ProdProductChangeDetail prodProductChangeDetail = new ProdProductChangeDetail();
        BeanUtils.copyProperties(prodProductChangeDetailDTO, prodProductChangeDetail);
        prodProductChangeDetailManager.insert(prodProductChangeDetail);
        String changeDetailId = prodProductChangeDetail.getChangeDetailId();
        return changeDetailId;
    }

    @Override
    public Boolean update(ProdProductChangeDetailDTO prodProductChangeDetailDTO) {
        ProdProductChangeDetail prodProductChangeDetail = new ProdProductChangeDetail();
        BeanUtils.copyProperties(prodProductChangeDetailDTO, prodProductChangeDetail);
        return prodProductChangeDetailManager.updateById(prodProductChangeDetail);
    }

    @Override
    public Integer batchInsert(ProdProductChangeDTO prodProductChangeDTO) {
        int i =0;
        List<ProdProductChangeDetailDTO> prodProductChangeDetailDTOs = prodProductChangeDTO.getProdProductChangeDetailDTOs();
        List<ProdProductChangeDetail> prodProductChangeDetails = new ArrayList<>();
        if(!CollectionUtils.isEmpty(prodProductChangeDetailDTOs)){
            for(ProdProductChangeDetailDTO prodProductChangeDetailDTO:prodProductChangeDetailDTOs){
                ProdProductChangeDetail prodProductChangeDetail = new ProdProductChangeDetail();
                BeanUtils.copyProperties(prodProductChangeDetailDTO, prodProductChangeDetail);
                prodProductChangeDetail.setCreateDate(new Date());
                prodProductChangeDetail.setVerNum(prodProductChangeDTO.getVerNum());
                prodProductChangeDetail.setCreateStaff(prodProductChangeDTO.getCreateStaff());
                prodProductChangeDetail.setChangeId(prodProductChangeDTO.getChangeId());
                prodProductChangeDetails.add(prodProductChangeDetail);
            }
            i = prodProductChangeDetailManager.batchInsert(prodProductChangeDetails) ;
        }

        return i;
    }

    @Override
    public List<ProdProductChangeDetailDTO> getProductChangeDetail(ProdProductChangeDetailDTO prodProductChangeDetailDTO) {
        List<ProdProductChangeDetailDTO> result = new ArrayList<>();
        List<ProdProductChangeDetail> prodProductChangeDetails =
                prodProductChangeDetailManager.getProdProductChangeDetail(prodProductChangeDetailDTO);
        if(!CollectionUtils.isEmpty(prodProductChangeDetails)){
            for(ProdProductChangeDetail prodProductChangeDetail1:prodProductChangeDetails){
                ProdProductChangeDetailDTO prodProductChangeDetailDTO1 = new ProdProductChangeDetailDTO();
                BeanUtils.copyProperties(prodProductChangeDetail1, prodProductChangeDetailDTO1);
                result.add(prodProductChangeDetailDTO1);
            }
        }

        return result;
    }


}
