package com.iwhalecloud.retail.warehouse.manager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.DeliveryValidResourceInstItemResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;
import com.iwhalecloud.retail.warehouse.entity.ResourceInst;
import com.iwhalecloud.retail.warehouse.mapper.ResourceInstMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Slf4j
public class ResourceInstManager extends ServiceImpl<ResourceInstMapper, ResourceInst> {
    @Resource
    private ResourceInstMapper resourceInstMapper;

    @Autowired
    private Constant constant;
    @Autowired
    private ResouceStoreManager resouceStoreManager;

    /**
     * 添加串码
     *
     * @param resourceInst
     * @return
     */
    public Integer addResourceInst(ResourceInst resourceInst) {
        return resourceInstMapper.insert(resourceInst);
    }

    /**
     * 更新串码状态
     *
     * @param req
     * @return
     */
    public Integer updateResourceInst(ResourceInstUpdateReq req) {
        Date now = new Date();
        req.setUpdateDate(now);
        req.setStatusDate(now);
        return resourceInstMapper.updateResourceInst(req);
    }

    /**
     * 更新串码状态
     *
     * @param req
     * @return
     */
    public Integer updateResourceInstByIds(AdminResourceInstDelReq req) {
//        修改一定要注意条件 防范全表更新
        if (req.getMktResInstIdList() == null || req.getMktResInstIdList().size() < 1) {
            return 0;
        }
        Date now = new Date();
        req.setUpdateDate(now);
        req.setStatusDate(now);
        return resourceInstMapper.updateResourceInstByIds(req);
    }

    /**
     * 根据查询条件串码实列
     *
     * @param req
     * @return
     */
    public Page<ResourceInstListPageResp> getResourceInstList(ResourceInstListPageReq req) {
        Page<ResourceInstListPageResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        return resourceInstMapper.getResourceInstList(page, req);
    }

    /**
     * 根据条件查询串码实列
     *
     * @param req
     * @return
     */
    public ResourceInstDTO getResourceInst(ResourceInstGetReq req) {
        return resourceInstMapper.getResourceInst(req);
    }

    /**
     * 根据条件查询串码实列
     *
     * @param req
     * @return
     */
    public List<ResourceInstDTO> getResourceInsts(ResourceInstsGetReq req) {
        return resourceInstMapper.getResourceInsts(req);
    }

    /**
     * 根据主键查询
     *
     * @param mktResInstId
     * @return
     */
    public ResourceInstDTO selectById(String mktResInstId) {
        ResourceInst t = resourceInstMapper.selectById(mktResInstId);
        if (null == t) {
            return null;
        }
        ResourceInstDTO dto = new ResourceInstDTO();
        BeanUtils.copyProperties(t, dto);
        return dto;
    }

    /**
     * 根据查询主键集合串码实列
     * @param req
     * @return
     */
    public List<ResourceInstDTO> selectByIds(ResourceInstsGetByIdListAndStoreIdReq req) {
        return resourceInstMapper.selectByIds(req);
    }

    /**
     * 调拨批量查询
     *
     * @param req
     * @return
     */
    public List<ResourceInstListPageResp> getBatch(ResourceInstBatchReq req) {
        return resourceInstMapper.getBatch(req);
    }

    /**
     * 修改实例状态
     *
     * @param req
     * @return
     */
    public int batchUpdateInstState(ResourceInstUpdateReq req) {
        Date now = new Date();
        req.setUpdateDate(now);
        req.setStatusDate(now);
        return resourceInstMapper.batchUpdateInstState(req);
    }

    /**
     * 根据串码查实例列表
     */
    public List<ResourceInstDTO> listInstsByNbr(String nbr) {
        return resourceInstMapper.listInstsByNbr(nbr);
    }

    /**
     * 根据条件查询串码实例
     * @param req
     * @return
     */
    public List<ResourceInstListResp> listResourceInst(ResourceInstListReq req){
        return resourceInstMapper.listResourceInst(req);
    }

    /**
     * 获取主键
     * @return
     */
    public String getPrimaryKey(){
        return resourceInstMapper.getPrimaryKey();
    }

    /**
     * 根据条件查询串码实列
     *
     * @param req
     * @return
     */
    public List<ResourceInstDTO> validResourceInst(ResourceInstsGetReq req) {
        return resourceInstMapper.validResourceInst(req);
    }

    /**
     * 校验发货串码
     * @param req
     * @return
     */
    public DeliveryValidResourceInstItemResp validResourceInst(DeliveryValidResourceInstReq req) {
        List<String> productIds = req.getProductIdList();
        List<String> mktResInstNbrList = req.getMktResInstNbrList();

        DeliveryValidResourceInstItemResp resp = new DeliveryValidResourceInstItemResp();
        ResourceInstsGetReq getReq = new ResourceInstsGetReq();
        getReq.setMktResStoreId(req.getMktResStoreId());
        getReq.setMktResInstNbrs(mktResInstNbrList);
        List<ResourceInstDTO> exixtsNbrInstList = resourceInstMapper.getResourceInsts(getReq);
        // 1、不存在的串码
        List<String> exixtsNbrList = exixtsNbrInstList.stream().map(ResourceInstDTO::getMktResInstNbr).collect(Collectors.toList());
        mktResInstNbrList.removeAll(exixtsNbrList);
        resp.setNotExistsNbrList(mktResInstNbrList);
        // 2、状态不正确的串码
        String avalable = ResourceConst.STATUSCD.AVAILABLE.getCode();
        List<ResourceInstDTO> wrongStatusInst = exixtsNbrInstList.stream().filter(t -> !avalable.equals(t.getStatusCd())).collect(Collectors.toList());
        List<String> wrongStatusNbrList = wrongStatusInst.stream().map(ResourceInstDTO::getMktResInstNbr).collect(Collectors.toList());
        resp.setWrongStatusNbrList(wrongStatusNbrList);
        exixtsNbrInstList.removeAll(wrongStatusInst);
        // 3、串码不属于该订单商品销售的范畴
        List<ResourceInstDTO> productIdNotMatchInst = exixtsNbrInstList.stream().filter(t -> !productIds.contains(t.getMktResId())).collect(Collectors.toList());
        List<String> productIdNotMatchNbrList = productIdNotMatchInst.stream().map(ResourceInstDTO::getMktResInstNbr).collect(Collectors.toList());
        resp.setNotMatchProductIdNbrList(productIdNotMatchNbrList);

        // 查询出来的串码实列除去产品不符合的
        exixtsNbrInstList.removeAll(productIdNotMatchInst);
        exixtsNbrInstList.removeAll(wrongStatusInst);
        // 根据产品维度组装通过校验的数据
        Map<String, List<String>> productIdAndNbrList = new HashMap<>();
        for(ResourceInstDTO dto : exixtsNbrInstList){
            String mktResId = dto.getMktResId();
            if (CollectionUtils.isEmpty(productIdAndNbrList.get(mktResId))) {
                List<String> nbrList = new ArrayList<>();
                nbrList.add(dto.getMktResInstNbr());
                productIdAndNbrList.put(mktResId, nbrList);
            }else{
                List<String> nbrList = productIdAndNbrList.get(mktResId);
                nbrList.add(dto.getMktResInstNbr());
            }
        }
        resp.setProductIdAndNbrList(productIdAndNbrList);
        return resp;
    }

    /**
     * 根据查询条件串码实列(手动分页，插件自带的线程不安全)
     * @return
     */
    public List<ResourceInstListPageResp> getResourceInstListManual(ResourceInstListPageReq req){
        return resourceInstMapper.getResourceInstListManual(req);
    }
}
