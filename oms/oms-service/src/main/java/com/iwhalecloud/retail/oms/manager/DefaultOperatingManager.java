package com.iwhalecloud.retail.oms.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.DefaultOperatingDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.DefaultOperatingQueryReq;
import com.iwhalecloud.retail.oms.entity.DefaultOperating;
import com.iwhalecloud.retail.oms.mapper.DefaultOperatingMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class DefaultOperatingManager {
    @Resource
    private DefaultOperatingMapper defaultOperatingMapper;

    /**
     * 查询.
     *
     * @param defaultCategoryId
     * @return
     * @author Ji.kai
     * @date 2018/10/25 15:27
     */
    public List<DefaultOperatingDTO> qryDefaultOperation(String defaultCategoryId)
    {
        return defaultOperatingMapper.qryDefaultOperation(defaultCategoryId);
    }
    /**
     * 查询.
     *
     * @param operatingPositionId
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    public DefaultOperatingDTO getDefaultOperation(String operatingPositionId)
    {
        QueryWrapper<DefaultOperating> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DefaultOperating.FieldNames.operatingPositionId.getTableFieldName(),operatingPositionId);
        DefaultOperating defaultOperating = defaultOperatingMapper.selectOne(queryWrapper);
        if (null == defaultOperating){
            return null;
        }
        DefaultOperatingDTO defaultOperatingDTO = new DefaultOperatingDTO();
        BeanUtils.copyProperties(defaultOperating, defaultOperatingDTO);
        return defaultOperatingDTO;
    }


    /**
     * 云货架运营位模版新增
     * @param defaultOperatingDTO
     * @return
     */
    public int createDefaultOperation(DefaultOperatingDTO defaultOperatingDTO){
        DefaultOperating defaultOperating = new DefaultOperating();
        defaultOperating.setGmtCreate(new Date());
        Long id = System.currentTimeMillis();
        defaultOperating.setOperatingPositionId(id.toString());
        defaultOperating.setId(null);
        BeanUtils.copyProperties(defaultOperatingDTO, defaultOperating);
        return defaultOperatingMapper.insert(defaultOperating);
    }


    /**
     * 云货架运营位模版修改
     * @param defaultOperatingDTO
     * @return
     */
    public int editDefaultOperation(DefaultOperatingDTO defaultOperatingDTO){
        return defaultOperatingMapper.editDefaultOperation(defaultOperatingDTO);
    }

    /**
     * 云货架运营位模版删除
     * @param id
     * @return
     */
    public int deleteDefaultOperation(Long id){
        DefaultOperatingDTO defaultOperatingDTO = new DefaultOperatingDTO();
        defaultOperatingDTO.setId(id);
        return defaultOperatingMapper.deleteById(defaultOperatingDTO);
    }


    public Page<DefaultOperatingDTO> queryoperatingPostionListDTO(DefaultOperatingQueryReq defaultOperatingQueryReq)throws Exception{
        Page<DefaultOperatingDTO> page = new Page<DefaultOperatingDTO>(defaultOperatingQueryReq.getPageNo(), defaultOperatingQueryReq.getPageSize());
        return  defaultOperatingMapper.queryoperatingPostionListDTO(page,defaultOperatingQueryReq);
    }

}
