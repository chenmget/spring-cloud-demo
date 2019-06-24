package com.iwhalecloud.retail.partner.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.partner.dto.MerchantTempDTO;
import com.iwhalecloud.retail.partner.entity.MerchantTemp;
import com.iwhalecloud.retail.partner.mapper.MerchantTempMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class MerchantTempManager extends ServiceImpl<MerchantTempMapper, MerchantTemp>{
    @Resource
    private MerchantTempMapper merchantTempMapper;

    /**
     * 批量添加数据到临时表
     *
     * @param merchantTempList
     * @return
     */
//    public Integer batchInsert(List<MerchantTemp> merchantTempList) {
//        return merchantTempMapper.batchInsert(merchantTempList);
//    }

    /**
     * 查询临时表数据
     *
     * @param merchantTempDTO
     * @return
     */
    public List<MerchantTemp> queryMerchantTemp(MerchantTempDTO merchantTempDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(MerchantTemp.FieldNames.status.getTableFieldName(), merchantTempDTO.getStatus());
        queryWrapper.eq(MerchantTemp.FieldNames.patch.getTableFieldName(), merchantTempDTO.getPatch());
        return merchantTempMapper.selectList(queryWrapper);
    }

    /**
     * 更新临时表
     * @param merchantTemp
     * @return
     */
    public int update(MerchantTemp merchantTemp) {
        return merchantTempMapper.updateById(merchantTemp);
    }

    /**
     * 删除临时表的数据（六天前）
     * @return
     */
    public int deleteMerchantTempData() {
        return merchantTempMapper.deleteMerchantTempData("7");
    }

}
