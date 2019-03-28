package com.iwhalecloud.retail.partner.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.partner.dto.BusinessEntityTempDTO;
import com.iwhalecloud.retail.partner.entity.BusinessEntityTemp;
import com.iwhalecloud.retail.partner.mapper.BusinessEntityTempMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class BusinessEntityTempManager extends ServiceImpl<BusinessEntityTempMapper, BusinessEntityTemp>{
    @Resource
    private BusinessEntityTempMapper businessEntityTempMapper;

    /**
     * 批量添加数据到临时表
     *
     * @param businessEntityTempList
     * @return
     */
//    public Integer batchInsert(List<BusinessEntityTemp> businessEntityTempList) {
//        return businessEntityTempMapper.batchInsert(businessEntityTempList);
//    }

    /**
     * 查询临时表数据
     *
     * @param businessEntityTempDTO
     * @return
     */
    public List<BusinessEntityTemp> queryBusinessEntityTemp(BusinessEntityTempDTO businessEntityTempDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(BusinessEntityTemp.FieldNames.status.getTableFieldName(), businessEntityTempDTO.getStatus());
        queryWrapper.eq(BusinessEntityTemp.FieldNames.patch.getTableFieldName(), businessEntityTempDTO.getPatch());
        return businessEntityTempMapper.selectList(queryWrapper);
    }

    /**
     * 更新临时表
     * @param businessEntityTemp
     * @return
     */
    public int update(BusinessEntityTemp businessEntityTemp) {
        return businessEntityTempMapper.updateById(businessEntityTemp);
    }

    /**
     * 删除临时表的数据（六天前）
     * @return
     */
    public int deleteBusinessEntityTempData() {
        return businessEntityTempMapper.deleteBusinessEntityTempData("7");
    }

}
