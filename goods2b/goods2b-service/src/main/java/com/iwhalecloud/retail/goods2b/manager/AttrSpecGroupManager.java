package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods2b.dto.AttrSpecGroupDTO;
import com.iwhalecloud.retail.goods2b.entity.AttrSpecGroup;
import com.iwhalecloud.retail.goods2b.mapper.AttrSpecGroupMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public class AttrSpecGroupManager {
    @Resource
    private AttrSpecGroupMapper attrSpecGroupMapper;

    public int addAttrSpecGroup(AttrSpecGroupDTO entity){
        AttrSpecGroup attrSpecGroup = new AttrSpecGroup();
        BeanUtils.copyProperties(entity,attrSpecGroup);
        return attrSpecGroupMapper.insert(attrSpecGroup);
    }

    public int deleteAttrSpecGroup(String id){
        return attrSpecGroupMapper.deleteById(id);
    }

    public int updateAttrSpecGroup(AttrSpecGroupDTO entity){
        AttrSpecGroup attrSpecGroup = new AttrSpecGroup();
        BeanUtils.copyProperties(entity,attrSpecGroup);
        return attrSpecGroupMapper.updateById(attrSpecGroup);
    }

    public List<AttrSpecGroupDTO> listByCondition(AttrSpecGroupDTO condition) {
        QueryWrapper queryWrapper = genBasicQueryWrapper(condition);
        List<AttrSpecGroup> entityList = attrSpecGroupMapper.selectList(queryWrapper);
        List<AttrSpecGroupDTO> dtoList = new ArrayList<>();
        for (AttrSpecGroup entity : entityList){
            AttrSpecGroupDTO dto = new AttrSpecGroupDTO();
            BeanUtils.copyProperties(entity,dto);
            dtoList.add(dto);
        }
        return dtoList;
    }

    private QueryWrapper genBasicQueryWrapper(AttrSpecGroupDTO condition){
        QueryWrapper queryWrapper = new QueryWrapper();
        if (null == condition){
            return queryWrapper;
        }
        if (!StringUtils.isEmpty(condition.getAttrGroupId())){
            queryWrapper.eq(AttrSpecGroup.FieldNames.attrGroupId.getTableFieldName(),condition.getAttrGroupId());
        }
        if (!StringUtils.isEmpty(condition.getStatusCd())){
            queryWrapper.eq(AttrSpecGroup.FieldNames.statusCd.getTableFieldName(),condition.getStatusCd());
        }
        return queryWrapper;
    }

}
