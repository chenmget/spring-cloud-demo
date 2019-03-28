package com.iwhalecloud.retail.oms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.response.gift.GiftRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListGiftReqDTO;
import com.iwhalecloud.retail.oms.entity.gif.Gift;


/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 奖品增删改
 */
@Mapper
public interface GiftMapper extends BaseMapper<Gift> {

    /**
     * 查询优惠券
     * @param page
     * @param tGiftDTO
     * @return
     */
    Page<GiftRespDTO> listGift(Page<ListGiftReqDTO> page, @Param("req") ListGiftReqDTO tGiftDTO);


    /**
     * 主键查询
     * @param giftId
     * @return
     */
    GiftRespDTO getById(Long giftId);
}
