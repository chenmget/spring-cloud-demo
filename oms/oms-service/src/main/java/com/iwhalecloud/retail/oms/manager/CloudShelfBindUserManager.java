package com.iwhalecloud.retail.oms.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.CloudShelfBindUserDTO;
import com.iwhalecloud.retail.oms.dto.CloudShelfDTO;
import com.iwhalecloud.retail.oms.entity.CloudShelfBindUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.oms.mapper.CloudShelfBindUserMapper;

import java.util.*;


@Component
public class CloudShelfBindUserManager{
    @Resource
    private CloudShelfBindUserMapper cloudShelfBindUserMapper;

    /**
     * 新增云货架绑定用户关联关系
     *
     * @param dto
     * @return
     * @author Ji.kai
     * @date 2018/11/19 15:27
     */
    public CloudShelfBindUserDTO insert(CloudShelfBindUserDTO dto) {
        dto.setGmtCreate(new Date());
        dto.setGmtModified(new Date());
        CloudShelfBindUser cloudShelf = new CloudShelfBindUser();
        BeanUtils.copyProperties(dto, cloudShelf);
        cloudShelfBindUserMapper.insert(cloudShelf);
        dto.setId(cloudShelf.getId());
        return dto;
    }

    /**
     * 根据云货架编码查询云货架绑定用户关联关系
     *
     * @param cloudShelfNum
     * @return
     * @author Ji.kai
     * @date 2018/11/19 15:27
     */
    public List<CloudShelfBindUserDTO> qryByCloudShelfNum(String cloudShelfNum) {
        List<CloudShelfBindUserDTO> cloudShelfBindUserDTOs = new ArrayList<>();
        QueryWrapper<CloudShelfBindUser> qw = new QueryWrapper<>();
        qw.eq(CloudShelfBindUser.FieldNames.cloudShelfNumber.getTableFieldName(), cloudShelfNum);
        qw.eq(CloudShelfBindUser.FieldNames.isDeleted.getTableFieldName(), OmsConst.IsDeleted.NOT_DELETED.getCode());
        List<CloudShelfBindUser> cloudShelfBindUsers = cloudShelfBindUserMapper.selectList(qw);
        for (CloudShelfBindUser cloudShelfBindUser : cloudShelfBindUsers){
            CloudShelfBindUserDTO cloudShelfBindUserDTO = new CloudShelfBindUserDTO();
            BeanUtils.copyProperties(cloudShelfBindUser, cloudShelfBindUserDTO);
            cloudShelfBindUserDTOs.add(cloudShelfBindUserDTO);
        }
        return cloudShelfBindUserDTOs;
    }

    public int deleteByShelfNum(String num){
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("CLOUD_SHELF_NUMBER", num);
        int delCount = cloudShelfBindUserMapper.deleteByMap(columnMap);
        return delCount;
    }

}
