package com.iwhalecloud.retail.oms.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.CloudShelfDTO;
import com.iwhalecloud.retail.oms.dto.resquest.CloudShelfPageReq;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudShelfAddReq;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudShelfRequestDTO;
import com.iwhalecloud.retail.oms.exception.BaseException;

import java.text.ParseException;

/**
 * @author lin.wh
 * @date 2018/10/12 15:10
 * @Description: 云货架
 */

public interface CloudShelfService {

    /**
     * 查询
     *
     * @param page
     * @return
     */
    Page<CloudShelfDTO> queryCloudShelfList(CloudShelfPageReq page);

    /**
     * 查询详情
     *
     * @param num
     * @return
     */
    CloudShelfDTO queryCloudShelfListDetails(String num, Boolean isQryDevice) throws ParseException, BaseException;

    /**
     * 添加
     *
     * @param req
     * @return
     */
    CloudShelfDTO createCloudShelf(CloudShelfAddReq req, String userId) throws ParseException, BaseException;

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    int updateCloudShelfStatus(CloudShelfDTO dto);

    /**
     * 删除
     *
     * @param dto
     * @return
     */
    int deleteCloudShelf(CloudShelfDTO dto);

    /**
     * @param cloudShelfRequestDTO
     * @return
     */
    ResultVO modifyCloudShelfByParam(CloudShelfRequestDTO cloudShelfRequestDTO, String userId);

    /**
     * 根据内容类型判断关联ID
     * @param contentId
     * @param type
     * @return
     */
    public String queryContentBasePersonal(Long contentId,Integer type);

}
