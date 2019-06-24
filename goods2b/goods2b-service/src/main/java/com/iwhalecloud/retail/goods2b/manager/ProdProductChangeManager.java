package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods2b.common.ProductConst;
import com.iwhalecloud.retail.goods2b.entity.ProdProductChange;
import com.iwhalecloud.retail.goods2b.mapper.ProdProductChangeMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2019/5/14.
 */
@Component
public class ProdProductChangeManager {
    @Resource
    private ProdProductChangeMapper prodProductChangeMapper;

    public Integer insert(ProdProductChange prodProductChange){
        return  prodProductChangeMapper.insert(prodProductChange);
    }


    public Integer delete(List<ProdProductChange> prodProductChanges){
        return  prodProductChangeMapper.deleteBatchIds(prodProductChanges);
    }

    public Integer deleteOne(String changeId){
        return  prodProductChangeMapper.deleteById(changeId);
    }

    public String selectVerNumByProductBaseId(String productBaseId){
        return prodProductChangeMapper.selectVerNumByProductBaseId(productBaseId);
    }

    public Boolean getProductChange(String productBaseId){
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("PRODUCT_BASE_ID",productBaseId);
        wrapper.eq("AUDIT_STATE", ProductConst.AuditStateType.AUDITING.getCode());
        List<ProdProductChange> prodProductChanges = prodProductChangeMapper.selectList(wrapper);
        if(!CollectionUtils.isEmpty(prodProductChanges)){
            return true;
        }
        return false;
    }
}
