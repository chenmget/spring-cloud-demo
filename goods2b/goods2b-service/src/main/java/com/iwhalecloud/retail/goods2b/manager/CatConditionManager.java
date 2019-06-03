package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods2b.dto.CatConditionDTO;
import com.iwhalecloud.retail.goods2b.dto.req.CatConditionListReq;
import com.iwhalecloud.retail.goods2b.entity.CatCondition;
import com.iwhalecloud.retail.goods2b.mapper.CatConditionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
public class CatConditionManager {
    @Resource
    private CatConditionMapper catConditionMapper;

    /**
     * 添加一个 商品类型条件
     *
     * @param entity
     * @return
     */
    public Integer saveCatCondition(CatCondition entity) {
        return catConditionMapper.insert(entity);
    }

    /**
     * 商品类型条件 列表查询
     *
     * @param req
     * @return
     */
    public List<CatConditionDTO> listCatCondition(CatConditionListReq req) {
        QueryWrapper<CatCondition> queryWrapper = new QueryWrapper<CatCondition>();
        if (!StringUtils.isEmpty(req.getTypeId())) {
            queryWrapper.eq(CatCondition.FieldNames.typeId.getTableFieldName(), req.getTypeId());
        }
        if (!StringUtils.isEmpty(req.getRelType())) {
            queryWrapper.eq(CatCondition.FieldNames.relType.getTableFieldName(), req.getRelType());
        }
        if (!StringUtils.isEmpty(req.getRelObjId())) {
            queryWrapper.eq(CatCondition.FieldNames.relObjId.getTableFieldName(), req.getRelObjId());
        }
        if (!StringUtils.isEmpty(req.getRelObjValue())) {
            queryWrapper.eq(CatCondition.FieldNames.relObjValue.getTableFieldName(), req.getRelObjValue());
        }
        List<CatCondition> entityList = catConditionMapper.selectList(queryWrapper);
        List<CatConditionDTO> dtoList = new ArrayList<>();
        for (CatCondition entity : entityList) {
            CatConditionDTO dto = new CatConditionDTO();
            BeanUtils.copyProperties(entity, dto);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
