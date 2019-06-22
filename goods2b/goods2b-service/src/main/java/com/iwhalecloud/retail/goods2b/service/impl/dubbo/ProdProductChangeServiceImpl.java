package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods2b.dto.ProdProductChangeDTO;
import com.iwhalecloud.retail.goods2b.entity.ProdProductChange;
import com.iwhalecloud.retail.goods2b.manager.ProdProductChangeDetailManager;
import com.iwhalecloud.retail.goods2b.manager.ProdProductChangeManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProdProductChangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by Administrator on 2019/5/16.
 */
@Slf4j
@Service
public class ProdProductChangeServiceImpl implements ProdProductChangeService {
    @Autowired
    private ProdProductChangeManager prodProductChangeManager;

    @Autowired
    private ProdProductChangeDetailManager prodProductChangeDetailManager;

    public String insert(ProdProductChangeDTO prodProductChangeDTO){
        int i=0;
//        String ver_num = prodProductChangeManager.selectVerNumByProductBaseId(prodProductChangeDTO.getProductBaseId());
        ProdProductChange prodProductChange = new ProdProductChange();
        BeanUtils.copyProperties(prodProductChangeDTO, prodProductChange);
//        prodProductChange.setVerNum(Long.valueOf(ver_num));
        prodProductChange.setCreateDate(new Date());
        i = prodProductChangeManager.insert(prodProductChange);
        String changeId = prodProductChange.getChangeId();
//        if(i==0){
//            return i;
//        }
//        List<ProdProductChangeDetailDTO> prodProductChangeDetailDTOs = prodProductChangeDTO.getProdProductChangeDetailDTOs();
//        List<ProdProductChangeDetail> prodProductChangeDetails = new ArrayList<>();
//        if(CollectionUtils.isNotEmpty(prodProductChangeDetailDTOs)){
//            for(ProdProductChangeDetailDTO prodProductChangeDetailDTO: prodProductChangeDetailDTOs){
//                ProdProductChangeDetail prodProductChangeDetail = new ProdProductChangeDetail();
//                BeanUtils.copyProperties(prodProductChangeDetailDTO, prodProductChangeDetail);
//                prodProductChangeDetail.setVerNum(prodProductChangeDTO.getVerNum());
//                prodProductChangeDetail.setCreateStaff(prodProductChangeDTO.getCreateStaff());
//                prodProductChangeDetail.setCreateDate(new Date());
//                prodProductChangeDetail.setChangeId(changeId);
//                prodProductChangeDetails.add(prodProductChangeDetail);
//            }
//        }
//
//        if(CollectionUtils.isNotEmpty(prodProductChangeDetails)){
//            prodProductChangeDetailManager.batchInsert(prodProductChangeDetails);
//        }

        return changeId;
    }

    public Integer delete(ProdProductChangeDTO prodProductChangeDTO){
        int i=0;
        i = prodProductChangeManager.deleteOne(prodProductChangeDTO.getChangeId());
        if(i==0){
            return i;
        }
        prodProductChangeDetailManager.deleteByChangeId(prodProductChangeDTO.getChangeId());

        return i;
    }

    @Override
    public String getVerNumByProductBaseId(String productBaseId) {
        return prodProductChangeManager.selectVerNumByProductBaseId(productBaseId);
    }

    @Override
    public Boolean getProductChange(String productBaseId) {
        return prodProductChangeManager.getProductChange(productBaseId);
    }
}
