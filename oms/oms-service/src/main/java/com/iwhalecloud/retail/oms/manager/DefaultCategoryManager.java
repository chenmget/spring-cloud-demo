package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.entity.DefaultCategory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.oms.mapper.DefaultCategoryMapper;
import java.util.List;


@Component
public class DefaultCategoryManager{
    @Resource
    private DefaultCategoryMapper defaultCategoryMapper;

    public int insertDefaultCatagory(DefaultCategory defaultCategory){
        return defaultCategoryMapper.insertDefaultCatagory(defaultCategory);
    }

    public int updateBatchDefaultCatagory(List<DefaultCategory> list){
        return defaultCategoryMapper.updateBatchDefaultCatagory(list);
    }

    public List<DefaultCategory> queryDefaultCategorys(List<Long> lists){
        return defaultCategoryMapper.queryDefaultCategorys(lists);
    }
    
}
