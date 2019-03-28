package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.CloudShelfDetailDTO;
import com.iwhalecloud.retail.oms.dto.ShelfDetailDTO;
import com.iwhalecloud.retail.oms.dto.ShelfDetailUpdateDTO;
import com.iwhalecloud.retail.oms.entity.CloudShelfDetail;
import com.iwhalecloud.retail.oms.mapper.ShelfDetailMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/23 22:49
 * @Description:
 */

@Component
public class ShelfDetailManager {
    @Resource
    private ShelfDetailMapper shelfDetailMapper;

    public int createShelfDetail(ShelfDetailDTO dto) {
        if(dto.getGmtCreate()==null){
            dto.setGmtCreate(new Date());
        }
        if(dto.getGmtModified()==null){
            dto.setGmtModified(new Date());
        }
        dto.setId(null);
        CloudShelfDetail total = new CloudShelfDetail();
        BeanUtils.copyProperties(dto, total);
        int t = shelfDetailMapper.insert(total);
        return t;
    }

    public ShelfDetailDTO insertShelfDetail(ShelfDetailDTO dto) {
        if(dto.getGmtCreate()==null){
            dto.setGmtCreate(new Date());
        }
        if(dto.getGmtModified()==null){
            dto.setGmtModified(new Date());
        }
        dto.setId(null);
        CloudShelfDetail total = new CloudShelfDetail();
        BeanUtils.copyProperties(dto, total);
        if(total.getOperatingPositionSequence()!=null){
            Integer newOperatingPositionSequence = total.getOperatingPositionSequence()+1;
            total.setOperatingPositionSequence(newOperatingPositionSequence);
            dto.setOperatingPositionSequence(newOperatingPositionSequence);
            Long id = System.currentTimeMillis();
            total.setOperatingPositionId(id.toString());
            dto.setOperatingPositionId(id.toString());
        }
        int t = shelfDetailMapper.insert(total);
        BeanUtils.copyProperties(total, dto);
        return dto;
    }

    public int editShelfDetail(ShelfDetailUpdateDTO dto) {
        int t = shelfDetailMapper.editShelfDetail(dto);
        return t;
    }

    public int deleteShelfDetail(ShelfDetailDTO dto) {
        int t = shelfDetailMapper.deleteShelfDetail(dto);
        return t;
    }

    public List<ShelfDetailDTO> queryCloudShelfDetailProductList(HashMap<String, Object> map) {
        return shelfDetailMapper.queryCloudShelfDetailProductList(map);
    }

    public List<ShelfDetailDTO> queryCloudShelfDetailContentList(HashMap<String, Object> map) {
        return shelfDetailMapper.queryCloudShelfDetailContentList(map);
    }

    public List<ShelfDetailDTO>  queryShelfDetailList(String cloudShelfNumber){
        return shelfDetailMapper.queryShelfDetailList(cloudShelfNumber);
    }

    public int batchUpdateShelfDetail(List<CloudShelfDetailDTO> list){
        return shelfDetailMapper.batchUpdateShelfDetai(list);
    }

    public List<ShelfDetailDTO> queryCloudShelfDetailList(CloudShelfDetailDTO cloudShelfDetailDTO) {
        return shelfDetailMapper.queryCloudShelfDetailList(cloudShelfDetailDTO);
    }

    public List<ShelfDetailDTO> queryCloudShelfDetailByProductId(String productId){
        return shelfDetailMapper.queryCloudShelfDetailByProductId(productId);
    }

}

