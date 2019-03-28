package com.iwhalecloud.retail.oms.service.impl.gift;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.response.gift.GiftRespDTO;
import com.iwhalecloud.retail.oms.dto.response.gift.UserGiftExchangeRespDTO;
import com.iwhalecloud.retail.oms.dto.response.gift.UserPointRecordRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.ListUserGiftExchangeReqDTO;
import com.iwhalecloud.retail.oms.dto.resquest.gift.UserGiftExchangeReqDTO;
import com.iwhalecloud.retail.oms.entity.gif.UserGiftExchange;
import com.iwhalecloud.retail.oms.entity.gif.UserPointRecord;
import com.iwhalecloud.retail.oms.manager.GiftManager;
import com.iwhalecloud.retail.oms.manager.UserGiftExchangeManager;
import com.iwhalecloud.retail.oms.manager.UserPointRecordManager;
import com.iwhalecloud.retail.oms.service.gift.UserGiftExchangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Auther: he.sw
 * @Date: 2018/10/26 16:28
 * @Description: 用户奖品兑换
 */
@Service
@Slf4j
public class UserGiftExchangeServiceImpl implements UserGiftExchangeService {

	@Autowired
    private UserGiftExchangeManager userGiftExchangeManager;
    @Autowired
    private UserPointRecordManager userPointRecordManager;
    
    @Autowired
    private GiftManager giftManager;



	@Override
	public Page<UserGiftExchangeRespDTO> listUserPointRecord(ListUserGiftExchangeReqDTO t) {
		return userGiftExchangeManager.listUserGiftExchange(t);
	}


	@Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public synchronized Integer saveUserGiftExchange(UserGiftExchangeReqDTO dto) {
		UserPointRecordRespDTO userNewestRecord = userPointRecordManager.getUserPoint(dto.getUserId());
		GiftRespDTO gift = giftManager.findById(dto.getGiftId());
		// 没找到奖品或是奖品下架
		if (null == gift || 1 == gift.getIsDown()) {
			return -1;
		}
		// 没有积分记录
		if (null == userNewestRecord) {
			return -1;
		}
		Date effDate = gift.getEffDate();
		Date expDate = gift.getExpDate();
		Date now = new Date();
		// 当前时间不在兑奖区
		if (now.before(effDate) || now.after(expDate)) {
			return -1;
		}
		Integer needTotalPoint = dto.getGainAmount()*gift.getNeedPointAmount();
		Integer leftTotalPoint = userNewestRecord.getTotalPoint() - needTotalPoint;
		Integer giftLeftAmount = gift.getGiftLeftAmount() - dto.getGainAmount();
		// 奖品库存不足
		if (giftLeftAmount < 0) {
			return -1;
		}
		// 超过每次可兑换最大数量
		if (gift.getGainAmount() < dto.getGainAmount()) {
			return -1;
		}
		// 积分不够
		if (leftTotalPoint < 0) {
			return -1;
		}

		UserPointRecord userPointRecordDTO = new UserPointRecord();
		userPointRecordDTO.setChangePoint(-needTotalPoint);
		userPointRecordDTO.setUserId(dto.getUserId());
		userPointRecordDTO.setSrcType(1);
		userPointRecordDTO.setCreateDate(now);
		userPointRecordDTO.setTotalPoint(leftTotalPoint);
		userPointRecordDTO.setRemark("兑换奖品");
		Integer insertRecord = userPointRecordManager.insert(userPointRecordDTO);
		// 积分记录插入失败
		if (null == insertRecord || insertRecord < 0) {
			return -1;
		}

		UserGiftExchange userGiftExchange = new UserGiftExchange();
		userGiftExchange.setGainAmount(dto.getGainAmount());
		userGiftExchange.setGiftId(dto.getGiftId());
		userGiftExchange.setGiftName(gift.getGiftName());
		userGiftExchange.setGiftType(gift.getGiftType());
		userGiftExchange.setNeedPointAmount(gift.getNeedPointAmount());
		userGiftExchange.setTotalPoint(leftTotalPoint);
		userGiftExchange.setUserId(dto.getUserId());
		userGiftExchange.setCreateDate(now);

		// 更新奖品
		gift.setGiftLeftAmount(giftLeftAmount);
		gift.setGiftExAmount(gift.getGiftExAmount() + dto.getGainAmount());
		giftManager.updateById(gift);

		Integer insertExchange = userGiftExchangeManager.insert(userGiftExchange);
		return insertExchange;
	}

}
