package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.goods2b.dto.ProdProductChangeDetailDTO;
import com.iwhalecloud.retail.goods2b.entity.ProdProductChangeDetail;
import com.iwhalecloud.retail.goods2b.mapper.ProdProductChangeDetailMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2019/5/14.
 */
@Component
public class ProdProductChangeDetailManager extends ServiceImpl<ProdProductChangeDetailMapper, ProdProductChangeDetail> {
    @Resource
    private ProdProductChangeDetailMapper prodProductChangeDetailMapper;

    public Integer insert(ProdProductChangeDetail prodProductChangeDetail){
        return prodProductChangeDetailMapper.insert(prodProductChangeDetail);
    }

    public Integer batchInsert(List<ProdProductChangeDetail> prodProductChangeDetails){
        Integer i = 0;
        for(ProdProductChangeDetail prodProductChangeDetail : prodProductChangeDetails){
           i+=  prodProductChangeDetailMapper.insert(prodProductChangeDetail);
        }
        return i;
    }

    public Integer delete(List<String> changeDetailIds){
        return prodProductChangeDetailMapper.deleteBatchIds(changeDetailIds);
    }

    public Integer update(List<ProdProductChangeDetail> prodProductChangeDetails){
        Integer i = 0;
        for(ProdProductChangeDetail prodProductChangeDetail : prodProductChangeDetails){
            i+=prodProductChangeDetailMapper.updateById(prodProductChangeDetail);
        }
        return i;
    }

    public Integer deleteByChangeId(String changeId){
        UpdateWrapper<ProdProductChangeDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("CHANGE_ID",changeId);
        return prodProductChangeDetailMapper.delete(updateWrapper);
    }

    public List<ProdProductChangeDetail> getProdProductChangeDetail(ProdProductChangeDetailDTO prodProductChangeDetail){
        QueryWrapper wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(prodProductChangeDetail.getTableName())) {
            wrapper.eq("TABLE_NAME", prodProductChangeDetail.getTableName());
        }
        if(StringUtils.isNotBlank(prodProductChangeDetail.getChangeDetailId())) {
            wrapper.eq("CHANGE_DETAIL_ID", prodProductChangeDetail.getChangeDetailId());
        }
        if(StringUtils.isNotBlank(prodProductChangeDetail.getChangeId())) {
            wrapper.eq("CHANGE_ID", prodProductChangeDetail.getChangeId());
        }
        if(prodProductChangeDetail.getVerNum()>1) {
            wrapper.eq("VER_NUM", prodProductChangeDetail.getVerNum());
        }
        if(StringUtils.isNotBlank(prodProductChangeDetail.getChangeField())) {
            wrapper.eq("CHANGE_FIELD", prodProductChangeDetail.getChangeField());
        }
        if(StringUtils.isNotBlank(prodProductChangeDetail.getOperType())) {
            wrapper.eq("OPER_TYPE", prodProductChangeDetail.getOperType());
        }
        if(StringUtils.isNotBlank(prodProductChangeDetail.getOldValue())) {
            wrapper.eq("OLD_VALUE", prodProductChangeDetail.getOldValue());
        }
        if(StringUtils.isNotBlank(prodProductChangeDetail.getNewValue())) {
            wrapper.eq("NEW_VALUE", prodProductChangeDetail.getNewValue());
        }
        if(StringUtils.isNotBlank(prodProductChangeDetail.getKeyValue())) {
            wrapper.eq("KEY_VALUE", prodProductChangeDetail.getKeyValue());
        }else if(!CollectionUtils.isEmpty(prodProductChangeDetail.getChangeFields())) {
            wrapper.in("KEY_VALUE", prodProductChangeDetail.getChangeFields());
        }

        return prodProductChangeDetailMapper.selectList(wrapper);
    }
}
