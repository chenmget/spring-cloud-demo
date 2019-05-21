package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;
import com.iwhalecloud.retail.warehouse.entity.ResourceInst;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author autoCreate
 * @Class: ResourceInstMapper
 */
@Mapper
public interface ResourceInstMapper extends BaseMapper<ResourceInst> {

    /**
     * 更新串码状态
     *
     * @param req
     * @return
     */
    public Integer updateResourceInst(ResourceInstUpdateReq req);

    /**
     * 更新串码状态
     *
     * @param req
     * @return
     */
    public Integer updateResourceInstByIds(AdminResourceInstDelReq req);

    /**
     * 根据查询条件串码实列
     *
     * @param page
     * @param req
     * @return
     */
    public Page<ResourceInstListPageResp> getResourceInstList(Page<ResourceInstListPageResp> page, @Param("req") ResourceInstListPageReq req);

    /**
     * 根据查询条件串码实列
     *
     * @param req
     * @return
     */
    public ResourceInstDTO getResourceInst(ResourceInstGetReq req);

    /**
     * 根据查询条件串码实列
     *
     * @param req
     * @return
     */
    public List<ResourceInstDTO> getResourceInsts(ResourceInstsGetReq req);

    /**
     * 根据查询主键集合串码实列
     *
     * @param req
     * @return
     */
    public List<ResourceInstDTO> selectByIds(ResourceInstsGetByIdListAndStoreIdReq req);

    /**
     * 根据串码查询串码实列列表
     *
     * @param nbr
     * @return
     */
    public List<ResourceInstDTO> listInstsByNbr(@Param("nbr") String nbr);

    /**
     * 调拨批量查询
     *
     * @param req
     * @return
     */
    public List<ResourceInstListPageResp> getBatch(ResourceInstBatchReq req);

    /**
     * 批量修改状态
     *
     * @param req
     * @return
     */
    int batchUpdateInstState(@Param("req") ResourceInstUpdateReq req);

    /**
     * 根据条件查询串码实例
     *
     * @param req
     * @return
     */
    List<ResourceInstListResp> listResourceInst(@Param("req") ResourceInstListReq req);

    /**
     * 获取主键
     *
     * @return
     */
    String getPrimaryKey();

    /**
     * 所有地市，不包含全网
     *
     * @return
     */
    List<String> findAllLanID();

    /**
     * 串码数据
     *
     * @param landId
     * @return
     */
    List<String> findMKTInfoByLadId(@Param("lanId") String landId, @Param("brand") String brand, @Param("scd") String ops,
                                    @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    String findCfValueByCfId(@Param("cofStr") String cofStr);

    int updateCfValueByCfId(@Param("cofStr") String cofStr, @Param("dateStr") String dateStr);

    int initConfig(@Param("endDate") String endDate);

    /**
     * 根据条件校验串码实列
     * @param req
     * @return
     */
    List<ResourceInstDTO> validResourceInst(ResourceInstsGetReq req);

}