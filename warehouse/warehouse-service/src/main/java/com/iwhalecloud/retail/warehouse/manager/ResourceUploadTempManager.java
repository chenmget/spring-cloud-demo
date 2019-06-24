package com.iwhalecloud.retail.warehouse.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceUploadTempDelReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceUploadTempListPageReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceUploadTempListResp;
import com.iwhalecloud.retail.warehouse.entity.ResouceUploadTemp;
import com.iwhalecloud.retail.warehouse.mapper.ResourceUploadTempMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
@Slf4j
public class ResourceUploadTempManager  extends ServiceImpl<ResourceUploadTempMapper, ResouceUploadTemp> {
    @Resource
    private ResourceUploadTempMapper resourceUploadTempMapper;
    

    /**
     * 查询校验串码分页
     * @param req
     * @return
     */
    public Page<ResourceUploadTempListResp> listResourceUploadTemp(ResourceUploadTempListPageReq req){
        Page<ResourceUploadTempListResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        return resourceUploadTempMapper.listResourceUploadTemp(page, req);
    }

    /**
     * 提交删除临时表
     * @param req
     * @return
     */
    public Integer delResourceUploadTemp(ResourceUploadTempDelReq req){
        Integer num = resourceUploadTempMapper.delResourceUploadTemp(req);
        log.info("resourceUploadTempMapper.delResourceUploadTemp req={}, num={}", JSON.toJSONString(req), num);
        return num;
    }

    /**
     * 查询总数
     * @param req
     * @return
     */
    public Integer countTotal(ResourceUploadTempDelReq req){
        return resourceUploadTempMapper.countTotal(req);
    }

    /**
     * 查询校验串码分页
     * @param req
     * @return
     */
    public List<ResourceUploadTempListResp> executorlistResourceUploadTemp(ResourceUploadTempListPageReq req){
        return resourceUploadTempMapper.executorlistResourceUploadTemp(req);
    }

    /**
     * 查询校验串码不分页
     * @param req
     * @return
     */
    public List<ResourceUploadTempListResp> listResourceUpload(ResourceUploadTempListPageReq req){
        return resourceUploadTempMapper.listResourceUploadTemp(req);
    }

}
