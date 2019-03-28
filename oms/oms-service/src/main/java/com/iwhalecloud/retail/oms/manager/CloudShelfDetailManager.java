package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.CloudShelfDetailDTO;
import com.iwhalecloud.retail.oms.entity.CloudShelfDetail;
import com.iwhalecloud.retail.oms.mapper.CloudShelfDetailMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class CloudShelfDetailManager {
    @Resource
    private CloudShelfDetailMapper cloudShelfDetailMapper;

    /**
     * 新增.
     *
     * @param dto
     * @return
     * @author Ji.kai
     * @date 2018/10/31 15:27
     */
    public int insert(CloudShelfDetailDTO dto) {
        dto.setGmtCreate(new Date());
        dto.setGmtModified(new Date());
        CloudShelfDetail cloudShelfDetail = new CloudShelfDetail();
        BeanUtils.copyProperties(dto, cloudShelfDetail);
        int t = cloudShelfDetailMapper.insert(cloudShelfDetail);
        dto.setId(cloudShelfDetail.getId());
        return t;
    }

    /**
     * 新增.
     *
     * @param cloudShelfDetail
     * @return
     * @author Ji.kai
     * @date 2018/10/31 15:27
     */
    public int update(CloudShelfDetail cloudShelfDetail) {
        int t = cloudShelfDetailMapper.updateById(cloudShelfDetail);
        return t;
    }

    /**
     * 删除.
     *
     * @param cloudShelfDetail
     * @return
     * @author Ji.kai
     * @date 2018/10/31 15:27
     */
    public int delete(CloudShelfDetail cloudShelfDetail) {
        int t = cloudShelfDetailMapper.deleteById(cloudShelfDetail);
        return t;
    }

    /**
     * 根据Id集合获得货架栏目列表.
     *
     * @param cloudShelfDetailIds
     * @return
     * @author Ji.kai
     * @date 2018/11/1 15:27
     */
    public List<CloudShelfDetail> qryListByIds(List<Long> cloudShelfDetailIds) {
        List<CloudShelfDetail> cloudShelfDetails = cloudShelfDetailMapper.selectBatchIds(cloudShelfDetailIds);
        return cloudShelfDetails;
    }


    
}
