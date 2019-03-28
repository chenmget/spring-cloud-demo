package com.iwhalecloud.retail.system.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.PublicDictDTO;
import com.iwhalecloud.retail.system.entity.PublicDict;
import com.iwhalecloud.retail.system.mapper.PublicDictMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
public class PublicDictManager{
    @Resource
    private PublicDictMapper publicDictMapper;

    /**
     * 根据类型查询 字典列表
     * @param type
     * @return
     */
    @Cacheable(value = SystemConst.CACHE_NAME_SYS_PUBLIC_DICT)
    public List<PublicDictDTO> queryPublicDictListByType(String type){
        QueryWrapper<PublicDict> queryWrapper = new QueryWrapper<PublicDict>();
        queryWrapper.eq(PublicDict.FieldNames.type.getTableFieldName(), type);
        queryWrapper.orderByAsc(PublicDict.FieldNames.sort.getTableFieldName());
        List<PublicDict> resultList =  publicDictMapper.selectList(queryWrapper);
        List<PublicDictDTO> publicDictDTOList = new ArrayList<>();
        for (PublicDict publicDict : resultList){
            PublicDictDTO publicDictDTO = new PublicDictDTO();
            BeanUtils.copyProperties(publicDict, publicDictDTO);
            publicDictDTOList.add(publicDictDTO);
        }
        return publicDictDTOList;
    }


}
