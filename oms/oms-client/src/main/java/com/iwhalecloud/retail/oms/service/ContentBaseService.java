package com.iwhalecloud.retail.oms.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.ContentBaseDTO;
import com.iwhalecloud.retail.oms.dto.ContentChkhisDTO;
import com.iwhalecloud.retail.oms.dto.response.content.ContentIdLIstResp;
import com.iwhalecloud.retail.oms.dto.resquest.content.*;

import java.util.List;

public interface ContentBaseService{


    /**
     * 根据在页面上选择的目录、内容类型、内容状态、标签以及输入的关键字等查询内容
     * @param page
     * @return
     * @author Ji.kai
     * @date 2018/10/29 15:27
     */
    Page<ContentBaseDTO> qryContentBasePageList(ContentBasePageReq page);

    /**
     * @Author Wu.LiangHang
     * @Description
     * @Date 2018/11/13 19:23
     * @Param
     * @return
     **/
    Page<ContentBaseDTO> qryContentBasePicsetOrTextPageList(ContentBasePTPageReq page);

    /**
     * 将内容名称、内容说明信息等插入内容基础信息表
     * @param contentAddReq,userId
     * @return
     */
    boolean addContentBase(ContentAddReq contentAddReq, String userId) ;

    /**
     * 对内容基础信息的表中该记录的状态进行更新
     * @param contentId,status
     * @return
     */
    boolean contentBaseStatusChange(Long contentId,Integer status) ;

    /**
     * 对内容基础信息表以及对应的相关素材表的数据
     * @param contentEditReq,userId
     * @return
     */
    boolean editContentBase(ContentEditReq contentEditReq, String userId) ;

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
     * @param contentBaseDTO
     * @return
     */
    ContentBaseDTO queryContentBase(ContentBaseDTO contentBaseDTO);

    /**
     * 失效前提醒
     * @param
     * @return
     * @author Ji.kai
     * @date 2018/11/1 15:27
     */
    List<ContentBaseDTO> remindBeforeInvalidate();

    /**
     * 内容失效
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
    int updateContentBase(ContentBaseDTO contentBaseDTO, ContentChkhisDTO contentChkhiDto);

    /**
     * @Author Wu.LiangHang
     * @Description
     * @Date 2018/11/8 13:43
     * @Param contentId
     * @return
     **/
    boolean addContentPic(String goodsId,  String actionType, String userId) throws Exception;

    /**
     * 查询条件根据对象类型 内容列表分页查询接口.
     * @param page
     * @return
     * @author Ji.kai
     * @date 2018/11/7 15:27
     */
    Page<ContentBaseDTO> qryContentBaseByObjTypePageList(ContentBaseByObjTypePageReq page);

    /**
     * 根据商品ID集合查询对应的内容ID
     * @param productIds
     * @return
     */
    ContentIdLIstResp queryContentIdList(List<String> productIds);

}