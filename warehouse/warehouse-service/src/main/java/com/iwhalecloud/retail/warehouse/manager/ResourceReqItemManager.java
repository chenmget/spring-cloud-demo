package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceReqItemAddReq;
import com.iwhalecloud.retail.warehouse.entity.ResourceReqItem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.warehouse.mapper.ResourceReqItemMapper;

import java.util.Calendar;
import java.util.List;


@Component
public class ResourceReqItemManager{
    @Resource
    private ResourceReqItemMapper resourceReqItemMapper;
    
    public String insertResourceReqItem(ResourceReqItemAddReq req){
        ResourceReqItem item = new ResourceReqItem();
        BeanUtils.copyProperties(req,item);
        item.setCreateDate(Calendar.getInstance().getTime());
        item.setStatusCd(ResourceConst.VALID);
        item.setStatusDate(Calendar.getInstance().getTime());
        resourceReqItemMapper.insert(item);
        return item.getMktResReqItemId();
    }

    /**
     * 获取申请单项列表
     * @return
     */
    public List<ResourceReqItem> getListResourceReqItem(String reqId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ResourceReqItem.FieldNames.mktResReqId.getTableFieldName(),reqId);
        return resourceReqItemMapper.selectList(queryWrapper);
    }

    /**
     * 获取申请单项的数量总和
     * @param requestId
     * @return
     */
    public Integer getItemQuantity(String requestId){
        return resourceReqItemMapper.getItemQuantity(requestId);
    }

    /**
     * 通过申请单的Id获取产品分类的名称
     * @param requestId
     * @return
     */
    public List<String> getProductCatName(String requestId){
        return resourceReqItemMapper.getProductCatName(requestId);
    }

}
