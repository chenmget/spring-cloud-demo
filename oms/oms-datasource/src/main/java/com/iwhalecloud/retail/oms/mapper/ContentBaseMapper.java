package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.ContentBaseDTO;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentBaseByObjTypePageReq;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentBasePTPageReq;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentBasePageReq;
import com.iwhalecloud.retail.oms.entity.ContentBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ContentBaseMapper
 * @author autoCreate
 */
@Mapper
public interface ContentBaseMapper extends BaseMapper<ContentBase>{

    /**
     * 根据在页面上选择的目录、内容类型、内容状态、标签以及输入的关键字等查询内容
     * @param page
     * @param pageReq
     * @return
     * @author Ji.kai
     * @date 2018/10/29 15:27
     */
    Page<ContentBaseDTO> qryContentBasePageList(Page<ContentBaseDTO> page, @Param("pageReq") ContentBasePageReq pageReq);

    /**
     * @Author Wu.LiangHang
     * @Description  
     * @Date 2018/11/13 19:23
     * @Param
     * @return 
     **/
    Page<ContentBaseDTO> qryContentBasePicsetOrTextPageList(Page<ContentBaseDTO> page, @Param("pageReq") ContentBasePTPageReq pageReq);
    
    /**
     * 根据条件查询内容列表
     * @param contentBaseDTO
     * @return
     * @author Ji.kai
     * @date 2018/10/29 15:27
     */
    List<ContentBaseDTO> qryContentBaseList(ContentBaseDTO contentBaseDTO);

    /**
     * 查询内容基础信息
     * @param contentId
     * @return
     */
    List<ContentBaseDTO> queryContentBase(@Param("contentId") Long contentId);

    /**
     * 查询失效内容列表
     * @param days
     * @return
     * @author Ji.kai
     * @date 2018/10/30 15:27
     */
    List<ContentBaseDTO> getExpContentBases(@Param("days") int days);

    /**
     * 查询失效内容列表
     * @param
     * @return
     * @author Ji.kai
     * @date 2018/11/2 15:27
     */
    int invalidateContentBase();

    /**
     * 更新内容信息
     * @param contentBaseDTO
     * @return
     */
    int updateContentBase(ContentBaseDTO contentBaseDTO);

    /**
     * 查询条件根据对象类型 内容列表分页查询接口
     * @param pageReq
     * @return
     * @author Ji.kai
     * @date 2018/11/7 15:27
     */
    Page<ContentBaseDTO> qryContentBaseByObjTypePageList(Page<ContentBaseDTO> page, @Param("pageReq") ContentBaseByObjTypePageReq pageReq);
    /**
     * 查询轮播图 相关 内容列表分页查询接口
     * @param pageReq
     * @return
     * @author Ji.kai
     * @date 2018/11/7 15:27
     */
    //Page<ContentBaseDTO> qryContentBaseByOrderPicPageList(Page<ContentBaseDTO> page, @Param("pageReq") ContentBaseByObjTypePageReq pageReq);

}