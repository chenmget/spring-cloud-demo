
package com.iwhalecloud.retail.rights.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.common.ResultCodeEnum;
import com.iwhalecloud.retail.rights.common.RightsConst;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import com.iwhalecloud.retail.rights.dto.OrderCouponDTO;
import com.iwhalecloud.retail.rights.dto.OrderItemWithCouponDTO;
import com.iwhalecloud.retail.rights.dto.SelectedCouponDTO;
import com.iwhalecloud.retail.rights.dto.request.*;
import com.iwhalecloud.retail.rights.dto.response.*;
import com.iwhalecloud.retail.rights.entity.*;
import com.iwhalecloud.retail.rights.handler.CouponInstHandler;
import com.iwhalecloud.retail.rights.manager.*;
import com.iwhalecloud.retail.rights.model.MktResCouponModel;
import com.iwhalecloud.retail.rights.service.CouponInstService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class CouponInstServiceImpl implements CouponInstService {

    @Autowired
    private CouponInstManager couponInstManager;

    @Autowired
    private MktResCouponManager mktResCouponManager;

    @Autowired
    private CouponEffExpRuleManager couponEffExpRuleManager;

    @Autowired
    private MktResourceManager mktResourceManager;

    @Autowired
    private CouponSupplyRuleManager couponSupplyRuleManager;

    @Autowired
    private CouponInstProvRecManager couponInstProvRecManager;

    @Autowired
    private CouponApplyObjectManager couponApplyObjectManager;

    @Autowired
    private CouponDiscountRuleManager couponDiscountRuleManager;

    @Autowired
    private CouponInstUseRecManager couponInstUseRecManager;

    @Autowired
	CouponInstHandler couponInstHandler;


    /**
     * 权益实例入库
     * @param dto
     * @return
     * @throws ParseException
     */
	@Override
	@Transactional
	public ResultVO<List<CouponInst>> inputRights(InputRightsRequestDTO dto) throws ParseException {
		String rightsId = dto.getRightsId();
		ResultVO<List<CouponInst>> resultVo = new ResultVO<List<CouponInst>>();
		resultVo.setResultMsg("权益入库成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		QueryMktResCouponRespDTO mktResCoupon = mktResCouponManager.queryMktResCoupon(rightsId);
		//不存在的权益
		if (null == mktResCoupon) {
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
			resultVo.setResultMsg("权益"+rightsId+"不存在");
			return resultVo;
		}
		Long beginNum = dto.getBeginNum();
		Integer inventoryNum = dto.getInventoryNum();
		// 起始编码和入库数量不是非必传字段，没传的起始编码取数据库最大的
		if (null == beginNum) {
			Long biggestInstNbr = couponInstManager.queryBiggestInstNbr();
			biggestInstNbr = biggestInstNbr == null ? 0 : biggestInstNbr;
			beginNum = couponInstManager.queryBiggestInstNbr() + 1;
		}
		if (null == inventoryNum) {
			inventoryNum = RightsStatusConsts.DEFAUT_INVENTORY_NUM;
		}
		for (Long i = beginNum; i < (beginNum + inventoryNum); i++) {
			// 入参没有传，此时去数据库查的最大的编码，比这个更大的数据库肯定还没有
			if (null == dto.getBeginNum()) {
				break;
			}
			// 编号是否已经存在,存在终止入库
			CheckCouponInstRespDTO ifExist = couponInstManager.checkByInstNbr(String.valueOf(i));
			if (null != ifExist) {
				resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
				resultVo.setResultMsg("权益实例"+i+"已存在");
				return resultVo;
			}
		}
		// 读取COUPON_EFF_EXP_RULE表，如果有指定生失效时间，以该生失效时间为主；如果没有指定，则生效时间为当前时间
		CommonQueryByMktResIdReqDTO commonQuery = new CommonQueryByMktResIdReqDTO();
		commonQuery.setMktResId(rightsId);
		Page<CouponEffExpRuleRespDTO> effExpRules = couponEffExpRuleManager.queryCouponEffExpRule(commonQuery);
		CouponEffExpRuleRespDTO effExpRule = effExpRules.getRecords().size() == 0 ? null : effExpRules.getRecords().get(0);
		Date effDate;
		Date expDate;
		Date now = new Date();
		if (null != effExpRule && null != effExpRule.getEffDate()) {
			effDate = effExpRule.getEffDate();
		}else {
			effDate = now;
		}
		if (null != effExpRule && null != effExpRule.getExpDate()) {
			expDate = effExpRule.getExpDate();
		}else {
			String stringDate = "2099-01-01";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			expDate = sdf.parse(stringDate);
		}
		List<CouponInst> couponInstList = new ArrayList<CouponInst>(inventoryNum);
		for (Long i = beginNum; i < (beginNum + inventoryNum); i++) {
			String resNbr = String.valueOf(i);
			//权益实例
			CouponInst couponInst = new CouponInst();
			couponInst.setCouponInstNbr(resNbr);
			couponInst.setMktResId(rightsId);
			couponInst.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNOBTAIN);
			couponInst.setStatusDate(now);
			couponInst.setCreateStaff(dto.getGmtCreate());
			couponInst.setUpdateStaff(dto.getGmtCreate());
			couponInst.setCreateDate(now);
			couponInst.setUpdateDate(now);
			couponInst.setEffDate(effDate);
			couponInst.setExpDate(expDate);
			couponInstList.add(couponInst);
//			couponInstManager.insertCouponInst(couponInst);
		}
		couponInstManager.insertBatchCouponInst(couponInstList);
		return resultVo;
	}

	/**
     * 权益领取
     * @param dto
     * @return
     */
	@Override
	@Transactional
	public ResultVO<StringBuilder> saveRights(SaveRightsRequestDTO dto) {
		String mktResId = dto.getMktResId();
		Long supplyNum = dto.getSupplyNum();
		String custNum = dto.getCustNum();
		ResultVO<StringBuilder> resultVo = new ResultVO<StringBuilder>();
		resultVo.setResultMsg("优惠券领取成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		// 查询COUPON_SUPPLY_RULE获得权益领取规则,判断当前时间是否在领取时间
		CommonQueryByMktResIdReqDTO commonQuery = new CommonQueryByMktResIdReqDTO();
		commonQuery.setMktResId(mktResId);
		Page<CouponSupplyRuleRespDTO> couponSupplyRules = couponSupplyRuleManager.queryCouponSupplyRule(commonQuery);
		if (supplyNum == null || 0 == supplyNum) {
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
			resultVo.setResultMsg("请输入正确的优惠券领取数量");
			return resultVo;
		}
		if (supplyNum == null || 0 == supplyNum) {
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
			resultVo.setResultMsg("请输入正确的优惠券领取数量");
			return resultVo;
		}
		if (null == couponSupplyRules || couponSupplyRules.getRecords().size() == 0) {
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
			resultVo.setResultMsg("优惠券领取规则"+mktResId+"不存在");
			return resultVo;
		}
		CouponSupplyRuleRespDTO couponSupplyRule = couponSupplyRules.getRecords().get(0);
		Date now = new Date();
		if (now.after(couponSupplyRule.getEndTime()) || now.before(couponSupplyRule.getBeginTime())) {
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
			resultVo.setResultMsg("优惠券不在领取时间范围");
			return resultVo;
		}

		QueryCouponInstProvRecNumReqDTO queryDTO = new QueryCouponInstProvRecNumReqDTO();
		queryDTO.setMktResId(mktResId);
		queryDTO.setCustNum(custNum);
		String cycleType = couponSupplyRule.getCycleType();
		if (RightsStatusConsts.CYCLE_TYPE_WEEK.equals(cycleType)) {
//			queryDTO.setTimePeriod("iw");
			queryDTO.setTimePeriodWeek(true);
		}else if (RightsStatusConsts.CYCLE_TYPE_MONTH.equals(cycleType)) {
//			queryDTO.setTimePeriod("mm");
			queryDTO.setTimePeriodMonth(true);
		}
		// 领取次数通过COUPON_INST_PROV_REC优惠券实例发放记录表进行统计
		Long ruleSupplyNum= couponSupplyRule.getSupplyNum() == null ? 0 : couponSupplyRule.getSupplyNum();
		Long maxNum= couponSupplyRule.getMaxNum() == null ? 0 : couponSupplyRule.getMaxNum();
		Long haveObtainNum = couponInstProvRecManager.queryCouponInstProvRecNum(queryDTO);
		if (null == haveObtainNum) {
			haveObtainNum = 0L;
		}
		if (ruleSupplyNum < supplyNum) {
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
			resultVo.setResultMsg("超过单次优惠券领取领取数量"+ruleSupplyNum);
			return resultVo;
		}
		if (maxNum < (haveObtainNum + supplyNum)) {
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
			resultVo.setResultMsg("超过优惠券领取最大数量"+maxNum);
			return resultVo;
		}

		// 根据权益标识在COUPON_INST中获得未分配的实例，更改状态
		GetUnuseCouponInstRequestDTO getUnuseCouponInstRequestDTO = new GetUnuseCouponInstRequestDTO();
		getUnuseCouponInstRequestDTO.setMktResId(mktResId);
		getUnuseCouponInstRequestDTO.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNOBTAIN);
		getUnuseCouponInstRequestDTO.setSupplyNum(supplyNum + 1);
		List<CouponInst> couponInsts = couponInstManager.queryByMktResId(getUnuseCouponInstRequestDTO);

		if (couponInsts == null || couponInsts.isEmpty() || couponInsts.size() < supplyNum) {
			Integer num = couponInsts == null?0:couponInsts.size();
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
			resultVo.setResultMsg("优惠券库存数"+num+"小于领取数量"+supplyNum);
			return resultVo;
		}

		StringBuilder couponInstIds = new StringBuilder();
		for (int i = 0; i < dto.getSupplyNum(); i++) {
			CouponInst couponInst = couponInsts.get(i);
			String couponInstId = couponInst.getCouponInstId();
			couponInst.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNUSED);
			couponInst.setCustAcctId(custNum);
			couponInst.setUpdateDate(now);
			couponInstManager.updateCouponInst(couponInst);

			CouponInstProvRec couponInstProvRec = new CouponInstProvRec();
			couponInstProvRec.setCouponInstId(couponInstId);
			// 发放记录 provObjId如果是发放对象，数据类型不一致
			couponInstProvRec.setProvObjId(custNum);
			couponInstProvRec.setCreateDate(now);
			couponInstProvRec.setUpdateDate(now);
			couponInstProvRec.setProvDate(now);
			couponInstProvRec.setStatusDate(now);
			couponInstProvRec.setProvDesc("优惠券领取");
			// 创建人没传，数据库不能为空暂时设-1
			couponInstProvRec.setCreateStaff("-1");
			couponInstProvRec.setUpdateStaff("-1");
			couponInstProvRec.setProvChannelType(dto.getProvChannelType());
			couponInstProvRec.setProvObjType(RightsStatusConsts.PROVOBJ_TYPE_CUS);
			couponInstProvRec.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNUSED);
			couponInstProvRecManager.insertCouponInstProvRec(couponInstProvRec);
			if (i == (dto.getSupplyNum()-1)) {
				couponInstIds.append(couponInstId);
			}else {
				couponInstIds.append(couponInstId).append(RightsStatusConsts.INST_ID_SEPARATOR);
			}
		}
		resultVo.setResultData(couponInstIds);
		return resultVo;
	}

	 /**
     *  权益核销校验
     * @param dto
     * @return
     */
	@Override
	@Transactional
	public ResultVO<CheckRightsResponseDTO> checkRights(CheckRightsRequestDTO dto) {
		ResultVO<CheckRightsResponseDTO> resultVo = new ResultVO<CheckRightsResponseDTO>();
		resultVo.setResultMsg("优惠券校验成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		String custNum = dto.getCustNum();
		String couponInstId = dto.getCouponInstId();
		// 没传实例id，查询用户领取未用的优惠券
		String [] couponInstIdArray = null;
		List<QueryCouponInstRespDTO> instList = null;
		Integer number = 0;
		if (StringUtils.isBlank(couponInstId)) {
			QueryCouponInstReqDTO queryCouponInstReqDTO = new QueryCouponInstReqDTO();
			queryCouponInstReqDTO.setCustNum(custNum);
			queryCouponInstReqDTO.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNUSED);
			instList = couponInstManager.queryEffInstByCustNum(queryCouponInstReqDTO);
			number = instList.size();
		}else {
			couponInstIdArray = dto.getCouponInstId().split(RightsStatusConsts.INST_ID_SEPARATOR);
			number = couponInstIdArray.length;
		}
		// 存放校验通过的优惠券实例个数
		Map<String, List<String>> instNum = new HashMap<String, List<String>>(number);
		// 存放通过的可以混用的优惠券实例
		JSONObject mixInst = new JSONObject();
		// 存放通过的不可以混用的优惠券实例
		JSONObject notMixInst = new JSONObject();
		Double orderPrice = dto.getOrderPrice();
		// 对优惠券券核销时规则校验，根据COUPON_EFF_EXP_RULE规则判断券实例是否有效，在领取的时候就校验了时间，校验实例自身的时间
		CommonQueryByMktResIdReqDTO commonQuery = new CommonQueryByMktResIdReqDTO();
		for (int i = 0; i < number; i++) {
			String couponInstIdStr = null;
			String mktResId = null;
			if (!StringUtils.isBlank(couponInstId)) {
				couponInstIdStr = couponInstIdArray[i];
				ResultVO<CouponInst> vaildInst = validInst(couponInstIdStr, custNum);
				if (!ResultCodeEnum.SUCCESS.getCode().equals(vaildInst.getResultCode())) {
					resultVo.setResultMsg(vaildInst.getResultMsg());
					resultVo.setResultCode(vaildInst.getResultCode());
					return resultVo;
				}
				CouponInst inst = (CouponInst)vaildInst.getResultData();
				mktResId = inst.getMktResId();
			}else {
				// 没传优惠券ID，根据客户号从数据库查出来的都是可用的优惠券不用再校验
				mktResId = instList.get(i).getMktResId();
				couponInstIdStr = instList.get(i).getCouponInstId();
				commonQuery.setMktResId(mktResId);
			}

			// 查询抵扣规则COUPON_DISCOUNT_RULE
			commonQuery.setMktResId(mktResId);
			Page<CouponDiscountRuleRespDTO> disountRules = couponDiscountRuleManager.queryCouponDiscountRule(commonQuery);
			CouponDiscountRuleRespDTO disountRule = disountRules.getRecords().size() == 0 ? null : disountRules.getRecords().get(0);

			// 规则表无记录
			if (null == disountRule) {
				resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
				resultVo.setResultMsg("兑换规则没找到");
				return resultVo;
			}

			// 判断当前订单金额是否满足抵扣要求
			Double ruleAmount = disountRule.getRuleAmount();
			if (orderPrice < ruleAmount) {
				resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
				resultVo.setResultMsg("订单金额必须满"+ruleAmount+"才能兑换");
				return resultVo;
			}

			// 不同一种优惠券是否允许混用
			String mixUseFlag = disountRule.getMixUseFlag();
			if (RightsStatusConsts.MIXUSE_FLAG_NO.equals(mixUseFlag)) {
				notMixInst.put(couponInstIdStr, disountRule);
			}else {
				mixInst.put(couponInstIdStr, disountRule);
			}
			List<String> instIds = instNum.get(mktResId);
			if (null == instIds || instIds.isEmpty()) {
				instIds = new ArrayList<String>(number);
			}
			if (instIds.contains(couponInstIdStr)) {
				resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
				resultVo.setResultMsg("请勿使用相同的优惠券"+couponInstIdStr);
				return resultVo;
			}
			instIds.add(couponInstIdStr);
			instNum.put(mktResId, instIds);
		}
		// 空集校验
		CheckRightsResponseDTO calculateMix;
		Double mixDiscountPrice = 0D;
		if (!mixInst.isEmpty()) {
			calculateMix = calculateMix(mixInst, instNum, orderPrice);
			mixDiscountPrice = calculateMix.getDiscountPrice();
		}else {
			calculateMix = new CheckRightsResponseDTO();
		}
		CheckRightsResponseDTO calculateNotMix;
		Double notMixDiscountPrice = 0D;
		if (!notMixInst.isEmpty()) {
			calculateNotMix =calculateNotMix(notMixInst, instNum, orderPrice);
			notMixDiscountPrice = calculateNotMix.getDiscountPrice();
		}else {
			calculateNotMix = new CheckRightsResponseDTO();
		}
		CheckRightsResponseDTO finalCheckRight = mixDiscountPrice > notMixDiscountPrice ? calculateMix:calculateNotMix;
		//去除优惠券id、优惠券名称串后面的逗号
		String couponInstIdStr = finalCheckRight.getCouponInstId();
		String mktResName = finalCheckRight.getMktResName();
		if (null != couponInstIdStr && couponInstIdStr.endsWith(RightsStatusConsts.INST_ID_SEPARATOR)) {
			int lastIndex= couponInstIdStr.lastIndexOf(RightsStatusConsts.INST_ID_SEPARATOR);
			couponInstIdStr = couponInstIdStr.substring(0, lastIndex);
		}
		if (null != mktResName && mktResName.endsWith(RightsStatusConsts.INST_ID_SEPARATOR)) {
			int lastIndex= mktResName.lastIndexOf(RightsStatusConsts.INST_ID_SEPARATOR);
			mktResName = mktResName.substring(0, lastIndex);
		}
		finalCheckRight.setCouponInstId(couponInstIdStr);
		finalCheckRight.setMktResName(mktResName);
		resultVo.setResultData(finalCheckRight);
		return resultVo;
	}

	/**
	 * 校验优惠券基本信息
	 * @param couponId
	 * @param custNum
	 * @return
	 */
	private ResultVO<CouponInst> validInst(String couponId, String custNum){
		ResultVO<CouponInst> resultVo = new ResultVO<CouponInst>();
		resultVo.setResultMsg("优惠券校验成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());

		if (StringUtils.isBlank(couponId)) {
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
			resultVo.setResultMsg("优惠券标识不能为空");
			return resultVo;
		}
		CouponInst inst = couponInstManager.selectById(couponId, custNum);
		if (null == inst) {
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
			resultVo.setResultMsg("优惠券实例"+couponId+"不存在");
			return resultVo;
		}
		String mktResId = inst.getMktResId();
		String custAcctId = inst.getCustAcctId();
		if (!custNum.equals(custAcctId)) {
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
			resultVo.setResultMsg("优惠券实例"+couponId+"的不是"+custNum+"所有");
			return resultVo;
		}
		Date now = new Date();
		if (now.before(inst.getEffDate()) || now.after(inst.getExpDate())) {
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
			resultVo.setResultMsg("优惠券实例"+couponId+"不在有效期");
			return resultVo;
		}
		if (!RightsStatusConsts.RIGHTS_STATUS_UNUSED.equals(inst.getStatusCd())) {
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
			resultVo.setResultMsg("优惠券实例"+couponId+"状态不对");
			return resultVo;
		}

		// 查询适用对象COUPON_APPLY_OBJECT，判断电子券是否适用，适用对象是实例里的赠送客户
		CommonQueryByMktResIdReqDTO commonQuery = new CommonQueryByMktResIdReqDTO();
		commonQuery.setMktResId(mktResId);
		Page<CouponApplyObjectRespDTO> applyObjects = couponApplyObjectManager.queryCouponAppyObject(commonQuery);
		CouponApplyObjectRespDTO applyObject = applyObjects.getRecords().size() == 0? null:applyObjects.getRecords().get(0);

		if (null != applyObject && custAcctId.equals(applyObject.getObjId())) {
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
			resultVo.setResultMsg("优惠券适用对象不正确");
			return resultVo;
		}
		resultVo.setResultData(inst);
		return resultVo;
	}


	/**
	 * 计算能混合使用优惠券的优惠金额
	 * @param mixInst
	 * @param instNum
	 * @param orderPrice
	 * @return
	 */
	private CheckRightsResponseDTO calculateMix(JSONObject mixInst, Map<String, List<String>> instNum, Double orderPrice) {
		Double discountPrice = 0D;
		StringBuilder couponInstId = new StringBuilder();
		StringBuilder mktResNames = new StringBuilder();
		List<Map<String, CouponDiscountRuleRespDTO>> sortDiscountRate = sortDiscountRate(mixInst);
		// 同一种优惠券去重用
		List<String> reDuplicate = new ArrayList<String>();
		for (int i = 0; i < sortDiscountRate.size(); i++) {
			Map<String, CouponDiscountRuleRespDTO> map = sortDiscountRate.get(i);
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String couponInstIdStr = iterator.next();
				CouponDiscountRuleRespDTO rule = map.get(couponInstIdStr);
				String mktResId = rule.getMktResId();
				Double ruleAmount = rule.getRuleAmount();
				String reuseFlag = rule.getReuseFlag();
				String mktResName = rule.getMktResName();

				// 有多张相同的优惠券，第一张的时候已经计算过，直接跳过
				if (reDuplicate.contains(mktResId)) {
					break;
				}

				int maxNum = 1;
				if (RightsStatusConsts.REUSE_FLAG_NO.equals(reuseFlag)) {
					// 优惠金额累加不能大于订单金额
					if (orderPrice < (discountPrice + rule.getDiscountValue())) {
						break;
					}
					discountPrice += rule.getDiscountValue();
					couponInstId.append(couponInstIdStr).append(RightsStatusConsts.INST_ID_SEPARATOR);
					mktResNames.append(mktResName).append(RightsStatusConsts.INST_ID_SEPARATOR);
				}else {
					// 订单总价/基数金额，获取最多能使用的优惠券数量(取整)
					maxNum = (int)(orderPrice/ruleAmount);
					// 优惠券实例的个数
					Integer num = instNum.get(mktResId).size();
					maxNum = maxNum > num ? num : maxNum;
					// 优惠券数量*每张优惠券的优惠金额
					Double maxValue = maxNum * rule.getDiscountValue();
					// 此处算出来的最大折扣金额超过了规定金额，按规定金额，并计算数量
					if (rule.getMaxValue() < maxValue) {
						maxNum = (int)(rule.getMaxValue()/rule.getDiscountValue());
						maxValue = maxNum * rule.getDiscountValue();
					}
					// 优惠金额累加不能大于订单金额
					if (orderPrice < (maxValue + discountPrice)) {
						maxNum = (int)((orderPrice - discountPrice)/rule.getDiscountValue());
						maxValue = maxNum * rule.getDiscountValue();
					}
					// 单种优惠金额不能大于订单金额
					if (orderPrice < maxValue) {
						maxNum = (int)(orderPrice/rule.getDiscountValue());
						maxValue = maxNum * rule.getDiscountValue();
					}
					// 算出来的最大优惠金额对比表里规定的最大优惠金额，不能超过表里规定的
					discountPrice +=  maxValue;
					// 在此处就应该把计算到的实例id记下来
					for (int j = 0; j < maxNum; j++) {
						couponInstId.append(instNum.get(mktResId).get(j)).append(RightsStatusConsts.INST_ID_SEPARATOR);
						mktResNames.append(mktResName).append(RightsStatusConsts.INST_ID_SEPARATOR);
					}
				}
				reDuplicate.add(mktResId);
			}
			// 可兑换金额小于0时，后面的优惠券不需要再兑换

		}
		CheckRightsResponseDTO dto = new CheckRightsResponseDTO();
		dto.setCouponInstId(couponInstId.toString());
		dto.setMktResName(mktResNames.toString());
		dto.setDiscountPrice(discountPrice);
		return dto;
	}


	/**
	 * 按优惠率大小降序排序并
	 * @param mixInst
	 * @return
	 */
	private List<Map<String, CouponDiscountRuleRespDTO>> sortDiscountRate(JSONObject mixInst){
		// 最大优惠率，每次把最大的优惠率赋值给它
		Double biggestDiscountRate = 0D;
		// 这个集合存放优惠率，按优惠率大小降序存放
		List<Map<String, CouponDiscountRuleRespDTO>> sortDiscountRate = new ArrayList<Map<String, CouponDiscountRuleRespDTO>>();
		Iterator<String> iterator = mixInst.keySet().iterator();
		while (iterator.hasNext()) {
			String couponInstIdStr = iterator.next();
			CouponDiscountRuleRespDTO disountRule = (CouponDiscountRuleRespDTO)mixInst.get(couponInstIdStr);
			// 优惠率，这个数据越大优惠金额越大
			Double discountRate = disountRule.getDiscountValue()/disountRule.getRuleAmount();
			// 最大优惠率放首位
			Map<String, CouponDiscountRuleRespDTO> data1 = new HashMap<String, CouponDiscountRuleRespDTO>();
			data1.put(couponInstIdStr, disountRule);
			if (discountRate > biggestDiscountRate) {
				biggestDiscountRate = discountRate;
				sortDiscountRate.add(0, data1);
				continue;
			}

			// 比最大优惠率小，则遍历已排好序的集合，找到第一个比他小元素并放在它的位置，后面的元素依次后移
			Integer replaceIndex = sortDiscountRate.size();
			for (int i = 0; i < sortDiscountRate.size(); i++) {
				Map<String, CouponDiscountRuleRespDTO> data2 = sortDiscountRate.get(i);
				Iterator<String> iterator2 = data2.keySet().iterator();
				// 遍历，这里其实只有一个数据
				CouponDiscountRuleRespDTO rule = null;
				while (iterator2.hasNext()){
					String couponInstIdStr2 = iterator2.next();
					rule = data2.get(couponInstIdStr2);
				}
				Double discountRate2 = rule.getDiscountValue()/rule.getRuleAmount();
				if (discountRate > discountRate2) {
					replaceIndex = i;
					break;
				}
			}
			// 比集合里的所有都小，放最后
			sortDiscountRate.add(replaceIndex, data1);
		}
		return sortDiscountRate;
	}

	/**
	 * 计算不能混合使用优惠券的优惠金额
	 * @param notMixInst
	 * @param instNum
	 * @param orderPrice
	 * @return
	 */
	private CheckRightsResponseDTO calculateNotMix(JSONObject notMixInst, Map<String, List<String>> instNum, Double orderPrice) {
		Double discountPrice = 0D;
		StringBuilder couponInstId = null;
		StringBuilder mktResNames = null;
		// 同一种优惠券去重用
		List<String> reDuplicate = new ArrayList<String>();
		Iterator<String> iterator = notMixInst.keySet().iterator();
		while (iterator.hasNext()) {
			String couponInstIdStr = iterator.next();
			CouponDiscountRuleRespDTO disountRule = (CouponDiscountRuleRespDTO)notMixInst.get(couponInstIdStr);

			// 同一种优惠券是否允许叠加，不允许叠加只能使用1张，允许叠加不能超过折扣额DISCOUNT_VALUE
			String reuseFlag = disountRule.getReuseFlag();
			String mktResId = disountRule.getMktResId();
			String mktResName = disountRule.getMktResName();
			// 有多张相同的优惠券，第一张的时候已经计算过，直接跳过
			if (reDuplicate.contains(mktResId)) {
				break;
			}
			if (RightsStatusConsts.REUSE_FLAG_NO.equals(reuseFlag)) {
				Double discountValue = disountRule.getDiscountValue();
				// 每次循环取优惠金额最大值
				if (discountValue > discountPrice) {
					discountPrice = discountValue;
					couponInstId = new StringBuilder(couponInstIdStr);
					mktResNames = new StringBuilder(mktResName);
				}
			}else {
				// 优惠券实例的个数
				Integer num = instNum.get(disountRule.getMktResId()).size();
				// 订单总价/基数金额，获取最多能使用的优惠券数量
				int maxNum = (int)(orderPrice/disountRule.getRuleAmount());
				maxNum = maxNum > num ? num : maxNum;
				// 优惠券数量*每张优惠券的优惠金额
				Double maxValue = maxNum * disountRule.getDiscountValue();

				// 算出来的最大优惠金额对比表里规定的最大优惠金额，不能超过表里规定的
				if (disountRule.getMaxValue() < maxValue) {
					maxNum = (int)(disountRule.getMaxValue()/disountRule.getDiscountValue());
					maxValue = maxNum * disountRule.getDiscountValue();
				}
				// 算出来的最大优惠金额不能超过订单金额
				if (orderPrice < maxValue) {
					maxNum = (int)(orderPrice/disountRule.getDiscountValue());
					maxValue = maxNum * disountRule.getDiscountValue();
				}
				Boolean isBigest = false;
				// 每次循环取优惠金额最大值
				if (discountPrice < maxValue) {
					discountPrice = maxValue;
					isBigest = true;
				}
				// 拼接INST_ID
				if (isBigest) {
					List<String> list = instNum.get(disountRule.getMktResId());
					couponInstId = new StringBuilder();
					mktResNames = new StringBuilder();
					for (int i = 0; i < maxNum; i++) {
						couponInstId.append(list.get(i)).append(RightsStatusConsts.INST_ID_SEPARATOR);
						mktResNames.append(mktResName).append(RightsStatusConsts.INST_ID_SEPARATOR);
					}
				}
			}
			reDuplicate.add(mktResId);
		}
		CheckRightsResponseDTO dto = new CheckRightsResponseDTO();
		dto.setCouponInstId(couponInstId.toString());
		dto.setMktResName(mktResNames.toString());
		dto.setDiscountPrice(discountPrice);
		return dto;
	}


	/**
     * 权益状态变更
     * @param dto
     * @return
     */
	@Override
//	@Transactional
	public ResultVO<Integer> userights(UserightsRequestDTO dto) {
		ResultVO<Integer> resultVo = new ResultVO<Integer>();
		resultVo.setResultMsg("核销成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());

		String [] couponInstIds = dto.getCouponInstId().split(RightsStatusConsts.INST_ID_SEPARATOR);
		String createStaff = dto.getCreateStaff();
		String orderId = dto.getOrderId();
		String custNum = dto.getCustNum();
		String statusCd = dto.getStatusCd();
		Date now = new Date();
		Integer successNum = 0;
		for (int i = 0; i < couponInstIds.length; i++) {
			String couponInstId = couponInstIds[i];

			// 更新COUPON_INST
			CouponInst inst = couponInstManager.selectById(couponInstId, custNum);
			// 传过来的客户编号不是领取优惠券记录的客户编号
			if (null == inst) {
				resultVo.setResultMsg("优惠券核"+couponInstId+"不存在");
				resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
				return resultVo;
			}
			if (!custNum.equals(inst.getCustAcctId())) {
				resultVo.setResultMsg("优惠券核"+couponInstId+"不属于用户"+custNum);
				resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
				return resultVo;
			}
			if (RightsStatusConsts.RIGHTS_STATUS_USED.equals(inst.getStatusCd())) {
				resultVo.setResultMsg("优惠券已使用");
				resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
				return resultVo;
			}
			inst.setStatusCd(statusCd);
			inst.setStatusDate(now);
			inst.setUpdateDate(now);
			inst.setUpdateStaff(createStaff);
			successNum += couponInstManager.updateCouponInst(inst);

			// 订单提交/返销时记录电子券核销日志COUPON_INST_USE_REC
			CouponInstUseRec couponInstUseRec = new CouponInstUseRec();
			couponInstUseRec.setCouponInstId(couponInstId);
			couponInstUseRec.setUseOrderId(orderId);
			couponInstUseRec.setUseObjId(String.valueOf(inst.getCustAcctId()));
			couponInstUseRec.setCreateDate(now);
			couponInstUseRec.setCreateStaff(createStaff);
			couponInstUseRec.setUpdateStaff(createStaff);
			couponInstUseRec.setUseObjType(RightsStatusConsts.PROVOBJ_TYPE_CUS);
			couponInstUseRec.setStatusCd(statusCd);
			couponInstUseRec.setStatusDate(now);
			couponInstUseRec.setUpdateDate(now);
			couponInstUseRec.setUseDate(now);
			couponInstUseRec.setUseDesc("权益状态改变");
			couponInstUseRecManager.insertCouponInstProvRec(couponInstUseRec);

		}
		resultVo.setResultData(successNum);
		return resultVo;
	}

	/**
     * 权益实例查询
     * @param req
     * @return
     */
	@Override
	public Page<QueryCouponInstRespDTO> queryCouponinst(
			QueryCouponInstPageReq req) {
		return couponInstManager.queryCouponinst(req);
	}

	@Override
	public ResultVO<OrderUseCouponResp> OrderUseCoupon(OrderUseCouponReq req) {
		log.info("开始计算优惠券的抵扣额，入参为{}", req.toString());
		List<OrderUseCouponDTO> orderUseCouponDTOS = new ArrayList<>();
		// 订单项列表
		List<CouponOrderItemDTO> orderItems = req.getOrderItems();
		// 产品与优惠券的关联关系
		List<ProductOrderItemDTO> productOrderItemDTOS = req.getProductOrderItemDTOS();
		// 使用的优惠券实例列表
		List<String> instIds = new ArrayList<>();
		for (ProductOrderItemDTO itemDTO: productOrderItemDTOS) {
			instIds.add(itemDTO.getCouponInstId());
		}
		// 获取优惠券信息
		ResultVO<List<MktResCouponModel>> mktResMessage = couponInstHandler.getMktResMessage(instIds);
		List<MktResCouponModel> mktResCouponModels = mktResMessage.getResultData();

		for (MktResCouponModel model: mktResCouponModels) {
			OrderUseCouponDTO orderUseCouponDTO = new OrderUseCouponDTO();
			for (CouponOrderItemDTO orderItemDTO: orderItems) {
				orderUseCouponDTO.setGoodsId(orderItemDTO.getGoodsId());
				orderUseCouponDTO.setProductId(orderItemDTO.getProductId());
				orderUseCouponDTO.setOrderItemId(orderItemDTO.getOrderItemId());
				orderUseCouponDTO.setNum(orderItemDTO.getNum());
				orderUseCouponDTO.setPrice(orderItemDTO.getPrice());
				String productId = orderItemDTO.getProductId();
				for (ProductOrderItemDTO itemDTO: productOrderItemDTOS) {
					String couponInstId = itemDTO.getCouponInstId();
					if (productId.equals(itemDTO.getProductId())) {
						if (couponInstId.equals(model.getCouponInstId())) {
							CouponDiscountRule discountRule = model.getDiscountRule();
							if (Objects.isNull(discountRule)) {
								return ResultVO.error("未找到优惠券的抵扣规则");
							}
							// 抵扣条件
							Double ruleAmount = discountRule.getRuleAmount();
							// 抵扣下限
							Double minValue = discountRule.getMinValue();
							// 抵扣金额
							Double discountValue = discountRule.getDiscountValue();
							// 计算抵扣额
							double itemCount = orderItemDTO.getNum() * orderItemDTO.getPrice();
							if (itemCount < ruleAmount) {
								return ResultVO.error("优惠券《" + model.getMktResName() + "》未满足抵扣条件");
							}
							double discount = discountValue;
							double afterPrice = itemCount - discount;
							if (afterPrice < minValue) {
								discount = itemCount - minValue;
							}
							orderUseCouponDTO.setMktResId(model.getMktResId());
							orderUseCouponDTO.setCouponInstId(model.getCouponInstId());
							orderUseCouponDTO.setMktResName(model.getMktResName());
							orderUseCouponDTO.setMktResNbr(model.getMktResNbr());
							orderUseCouponDTO.setDiscount(discount);
							break;
						}
					}
				}
				if (Objects.isNull(orderUseCouponDTO.getDiscount())) {
					orderUseCouponDTO.setDiscount(0.00);
				}
				orderUseCouponDTOS.add(orderUseCouponDTO);
			}
		}
		OrderUseCouponResp resp = new OrderUseCouponResp();
		resp.setCouponDTOList(orderUseCouponDTOS);
		log.info("计算优惠券的抵扣额结束，返参为{}", resp.toString());
		return ResultVO.success(resp);
    }

	/**
	 * 订单优惠券列表查询
	 */
	@Override
	public ResultVO<OrderCouponListResp> orderCouponList(OrderCouponListReq req) {
		log.info("CouponInstServiceImpl.orderCouponList(), input: OrderCouponListReq={} ", JSON.toJSONString(req));

		OrderCouponListResp resp = new OrderCouponListResp(); // 返回

		// 1、获取必要数据 集合
		// 获取用户所有的  优惠券实例详情列表
		QueryCouponInstReqDTO queryCouponInstReqDTO = new QueryCouponInstReqDTO();
		queryCouponInstReqDTO.setCustNum(req.getUserMerchantId()); // 用买家的商家ID
		queryCouponInstReqDTO.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNUSED);
		List<CouponInstDetailDTO> allCouponInst = couponInstManager.listCouponInstDetail(queryCouponInstReqDTO);

		// 数据判空处理
		if (!CollectionUtils.isEmpty(allCouponInst)) {
			for (CouponInstDetailDTO couponInstDetailDTO : allCouponInst) {
				// 判断必要数据 抵扣上限 下限  抵扣额 抵扣条件 是否为空
				if (Objects.isNull(couponInstDetailDTO.getMinValue())) {
					couponInstDetailDTO.setMinValue(0.0);
				}
				if (Objects.isNull(couponInstDetailDTO.getMaxValue())) {
					couponInstDetailDTO.setMaxValue(0.0);
				}
				if (Objects.isNull(couponInstDetailDTO.getRuleAmount())) {
					couponInstDetailDTO.setRuleAmount(0.0);
				}
				if (Objects.isNull(couponInstDetailDTO.getDiscountValue())) {
					couponInstDetailDTO.setDiscountValue(0.0);
				}
			}
		}
		if (!CollectionUtils.isEmpty(req.getOrderItemList())) {
			for (CouponOrderItemDTO couponOrderItemDTO : req.getOrderItemList()) {
				// 判断必要数据 价格 直减价格 已优惠金额  购买数量 是否为空
				if (Objects.isNull(couponOrderItemDTO.getDiscount())) {
					couponOrderItemDTO.setDiscount(0.0);
				}
				if (Objects.isNull(couponOrderItemDTO.getPrice())) {
					couponOrderItemDTO.setPrice(0.0);
				}
				if (Objects.isNull(couponOrderItemDTO.getPriceDiscount())) {
					couponOrderItemDTO.setPriceDiscount(0.0);
				}
				if (Objects.isNull(couponOrderItemDTO.getNum())) {
					couponOrderItemDTO.setNum(0);
				}
			}
		}


		// 2、订单数据  已选优惠券  数据处理（主要处理订单列表，那些已经绑定优惠券的重新算出优惠金额）
		List<OrderItemWithCouponDTO> orderItemWithCouponList = handleOrderList(req, allCouponInst);


		// 3、对优惠券校验 (判断是否有效 、设置是否选中 ...)
		List<OrderCouponDTO> orderCouponList = handleCouponList(req, orderItemWithCouponList, allCouponInst);

		resp.setOrderItemList(orderItemWithCouponList);
		resp.setCouponList(orderCouponList);
		log.info("CouponInstServiceImpl.orderCouponList(), output: resp={} ", JSON.toJSONString(resp));
		return ResultVO.success(resp);
	}

	/**
	 * 优惠券数据处理
	 * @param req
	 * @param orderItemWithCouponList
	 * @param allCouponInst  优惠券实例列表
	 * @return
	 */
	private List<OrderCouponDTO> handleCouponList(OrderCouponListReq req, List<OrderItemWithCouponDTO> orderItemWithCouponList, List<CouponInstDetailDTO> allCouponInst) {
		List<OrderCouponDTO> respList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(req.getOrderItemList())
				|| CollectionUtils.isEmpty(allCouponInst)) {
			log.info("订单项 或 优惠券实例 集合不能为空");
			return respList;
		}

		if (CollectionUtils.isEmpty(req.getSelectedCouponList())) {
			req.setSelectedCouponList(Lists.newArrayList());
		}

		boolean hasSelectCoupon = false; // 已经选中有优惠券
		boolean hasNotMixUseFlag = false; // 已选中的优惠券中  是否有 不可混用 标示的券
		// 已选中的优惠券中 有 不可混用 标示的券ID集合（主要判断可以叠加使用的券）
//		List<String> hasNotMixUseFlagMktResIdList = Lists.newArrayList();
        String hasNotMixUseFlagMktResId = "";

		// 第一轮循环 主要处理 已选中的优惠券（设置优惠券已选中状态 和 对应订单）
		for (CouponInstDetailDTO couponInstDetailDTO : allCouponInst) {
			OrderCouponDTO newOrderCoupon = new OrderCouponDTO(); // 目标对象
			newOrderCoupon.setCouponInst(couponInstDetailDTO); // 保存优惠券信息

			// 循环已选中的优惠券实例列表
			for (SelectedCouponDTO selectedCouponDTO : req.getSelectedCouponList()) {
				if (StringUtils.equals(selectedCouponDTO.getCouponInstId(), couponInstDetailDTO.getCouponInstId())) {
					hasSelectCoupon = true;
					if (StringUtils.equals(couponInstDetailDTO.getMixUseFlag(), RightsStatusConsts.MIXUSE_FLAG_NO)) {
						log.info("有不可混用标识的优惠券实例ID:{},优惠券ID：{}", couponInstDetailDTO.getCouponInstId(), couponInstDetailDTO.getMktResId());
						hasNotMixUseFlag = true; // 有 不可混用 标示的券
						// 保存 有 不可混用 标示的券id
						hasNotMixUseFlagMktResId = couponInstDetailDTO.getMktResId();
//						hasNotMixUseFlagMktResIdList.add(couponInstDetailDTO.getMktResId());
					}
					newOrderCoupon.setIsValid(true);
					newOrderCoupon.setIsSelected(true);
					// 循环订单项
					for (CouponOrderItemDTO couponOrderItemDTO : req.getOrderItemList()) {
						// 设置选中优惠券 对应的订单
						if (StringUtils.equals(selectedCouponDTO.getOrderItemId(), couponOrderItemDTO.getOrderItemId())) {
							newOrderCoupon.setBindOrderItem(couponOrderItemDTO);
						}
					}
				}
			}
			respList.add(newOrderCoupon);
		}

		// 第二轮循环 主要处理校验 优惠券的有效性 （除已选中的 校验有效性）
		log.info("开始校验 优惠券的有效性.....start");
		for (OrderCouponDTO orderCouponDTO : respList) {

			// 当前优惠券实例对象
			CouponInstDetailDTO curCouponInst = orderCouponDTO.getCouponInst();

			if (orderCouponDTO.getIsSelected()) {
				log.info("优惠券实例ID:{},优惠券ID：{},已经被选中，不用再进行判断操作", curCouponInst.getCouponInstId(), curCouponInst.getMktResId());
				// 已经选中的 不用处理校验
				continue;
			}
			if (hasNotMixUseFlag) {
				// 已选中有不可 混合使用的券 除相同券并且是可叠加可能有效，其他的默认无效
				log.info("已经有不可混用标识的优惠券ID：{}", hasNotMixUseFlagMktResId);
//				if (hasNotMixUseFlagMktResIdList.contains(orderCouponDTO.getCouponInst().getMktResId())) {
//				}
				if (!StringUtils.equals(hasNotMixUseFlagMktResId, curCouponInst.getMktResId())) {
					log.info("因为已经选中有不可混用标识的优惠券ID：{}，所以当前的优惠券实例ID:{},优惠券ID：{},对订单无效", hasNotMixUseFlagMktResId, curCouponInst.getCouponInstId(), curCouponInst.getMktResId());
					continue;
				}
			}


            // 判断是否有规则ID（没有  设置无效）
            if (StringUtils.isEmpty(curCouponInst.getDiscountRuleId())) {
//                orderCouponDTO.setIsValid(false);
				log.info("优惠券实例{}没有规则ID", curCouponInst.getCouponInstId());
				continue;
            }

            // 当前优惠券实例对象
//            CouponInstDetailDTO couponInst = orderCouponDTO.getCouponInst();

			if (StringUtils.equals(curCouponInst.getMixUseFlag(), RightsStatusConsts.MIXUSE_FLAG_NO)
					&& hasSelectCoupon) {
				if (!StringUtils.equals(hasNotMixUseFlagMktResId, curCouponInst.getMktResId())) {
					log.info("已经选中有优惠券实例，并且当前优惠券实例{}，优惠券ID：{}是不可混用的", curCouponInst.getCouponInstId(), curCouponInst.getMktResId());
					continue;
				}
			}

			// 获取当前优惠券 对应的 适用对象列表
			CouponApplyObjectListReq couponApplyObjectListReq = new CouponApplyObjectListReq();
			couponApplyObjectListReq.setMktResId(curCouponInst.getMktResId());
			List<CouponApplyObject> couponApplyObjectList = couponApplyObjectManager.listCouponApplyObject(couponApplyObjectListReq);
			List<String> objIdList = Lists.newArrayList();
			couponApplyObjectList.forEach(couponApplyObject -> {
			    objIdList.add(couponApplyObject.getObjId());
            });


//暂时只校验  平台券
			if (StringUtils.equals(curCouponInst.getCouponType(), RightsConst.MktResCouponType.PLATFORM.getType())) {
				log.info("优惠券实例{}优惠券ID{}的优惠券类型 是平台类型优惠券", curCouponInst.getCouponInstId(), curCouponInst.getMktResId());

				// a、 平台券  （暂时没有, 应该是 判断总订单金额满足抵扣规则后  直接设置有效）
//				if (isConformRules(couponInst.getMktResId(), allCouponDiscountRuleList, req.getOrderItems())) {
//					// 判断金额 满足  设置该优惠券对整个订单有效 （不针对某个订单项）
//					orderCouponDTO.setIsValid(true);
//				}
//			} else if (StringUtils.equals(couponInst.getCouponType(), RightsConst.MktResCouponType.MERCHANT.getType())) {
//				// b、 商家券  （暂时没有 待完善）
////				if (!CollectionUtils.isEmpty(req.getOrderItems())
////						&& !CollectionUtils.isEmpty(objIdList)) {
////					req.getOrderItems().forEach(orderItem -> {
////						if (objIdList.contains(req.getMerchantId())) {
////							// 设置该优惠券对商家有效 不针对订单
////							orderCouponDTO.setIsValid(true);
////						}
////					});
////				}
//			} else if (StringUtils.equals(couponInst.getCouponType(), RightsConst.MktResCouponType.PRODUCT.getType())) {

				// c、产品券  循环订单项里面对 产品ID（商品ID 暂时没有用到）是否在对象ID集合里面
                // 循环订单项 判断 产品ID 在不在适用对象列表
                for (OrderItemWithCouponDTO orderItem : orderItemWithCouponList) {
                    if (objIdList.contains(orderItem.getProductId())) {
                        // 在适用对象列表
						// 规则校验
                        if (isConformRules(curCouponInst, orderItem)) {
							log.info("优惠券实例{}优惠券ID{} 满足订单项{}", curCouponInst.getCouponInstId(), curCouponInst.getMktResId(), orderItem.getOrderItemId());

							// 判断金额 满足  设置该优惠券对订单有效，并保存对应订单项
                            orderCouponDTO.setIsValid(true);
                            CouponOrderItemDTO couponOrderItemDTO = new CouponOrderItemDTO();
                            BeanUtils.copyProperties(orderItem, couponOrderItemDTO);
                            if (CollectionUtils.isEmpty(orderCouponDTO.getApplyOrderItemList())) {
								orderCouponDTO.setApplyOrderItemList(Lists.newArrayList(couponOrderItemDTO));
							} else {
                            	orderCouponDTO.getApplyOrderItemList().add(couponOrderItemDTO);
							}
                        }
                    }
                }
			}
		}
		log.info("结束校验 优惠券的有效性.....end");

		return respList;
	}

	/**
	 * 订单数据初步处理
	 * @param req
	 * @param allCouponInst  优惠券实例列表
	 * @return
	 */
	private List<OrderItemWithCouponDTO> handleOrderList(OrderCouponListReq req, List<CouponInstDetailDTO> allCouponInst) {
		List<OrderItemWithCouponDTO> respList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(req.getOrderItemList())
				|| CollectionUtils.isEmpty(allCouponInst)) {
			log.info("订单项 或 优惠券实例 集合不能为空");
			return respList;
		}

		if (CollectionUtils.isEmpty(req.getSelectedCouponList())) {
			log.info("已选中的优惠券列表为空");
			req.setSelectedCouponList(Lists.newArrayList());
		}

		Iterator<SelectedCouponDTO> selectedCouponDTOIterator = req.getSelectedCouponList().iterator();
		// 循环订单项列表  更新订单项已绑定优惠券 后的信息（优惠金额  优惠券信息）
		req.getOrderItemList().forEach(couponOrderItemDTO -> {
			OrderItemWithCouponDTO newOrderItem = new OrderItemWithCouponDTO();
			BeanUtils.copyProperties(couponOrderItemDTO, newOrderItem);

			// 设置 抵扣上限  下限
			newOrderItem.setMaxValue(0.0);
			newOrderItem.setMinValue(0.0);

			// 循环已 选中的优惠券列表
			while (selectedCouponDTOIterator.hasNext()) {
				SelectedCouponDTO selectedCoupon = selectedCouponDTOIterator.next();
				if (selectedCoupon.getOrderItemId() == null) {
					// 没有
					log.info("已选中的优惠实例{}没有对应的订单项，可能是平台优惠券", selectedCoupon.getCouponInstId());
					continue;
				}
				if (StringUtils.equals(couponOrderItemDTO.getOrderItemId(), selectedCoupon.getOrderItemId())) {
					// 当前订单项ID  和  已选中的优惠券 对应的订单项ID 相同  计算订单项的优惠金额 并保存优惠券信息
					for (int j = 0; j < allCouponInst.size(); j++) {
						CouponInstDetailDTO currentCouponInst = allCouponInst.get(j);
						// 该订单已绑定优惠券 累加减免金额
						if (StringUtils.equals(selectedCoupon.getCouponInstId(), currentCouponInst.getCouponInstId())) {
							//  优惠金额
							Double discount = calculateOrderDiscount(currentCouponInst, newOrderItem); // 减免金额

							if (discount > 0) {
								// 更新 抵扣 上限  下限 (  <= 0 是第一次更新  直接赋值）
								if (newOrderItem.getMaxValue() > currentCouponInst.getMaxValue()
										|| newOrderItem.getMaxValue() <= 0) {
									newOrderItem.setMaxValue(currentCouponInst.getMaxValue());
								}
								if (newOrderItem.getMinValue() < currentCouponInst.getMinValue()
										|| newOrderItem.getMinValue() <= 0) {
									newOrderItem.setMinValue(currentCouponInst.getMinValue());
								}

								// 保存订单项  绑定的优惠券信息
								if (CollectionUtils.isEmpty(newOrderItem.getBingCouponList())) {
									newOrderItem.setBingCouponList(Lists.newArrayList(currentCouponInst));
								} else {
									newOrderItem.getBingCouponList().add(currentCouponInst);
								}
								// 更新 保存 已优惠总金额
								newOrderItem.setDiscount(discount + newOrderItem.getDiscount());
								log.info("订单项{} 已绑定优惠券实例ID{}优惠券ID{}", newOrderItem.getOrderItemId(), currentCouponInst.getCouponInstId(),currentCouponInst.getMktResId());
							} else {
								// 从订单 优惠券 绑定列表中  移除数据
								selectedCouponDTOIterator.remove();
								log.info("订单项{} 绑定的优惠券实例ID{}优惠券ID{} 不满足条件,去除绑定关系", newOrderItem.getOrderItemId(), currentCouponInst.getCouponInstId(), currentCouponInst.getMktResId());
							}
						}
					}
				}
			}

//			for (int i = 0; i < req.getSelectedCouponList().size(); i++) {
//				SelectedCouponDTO selectedCoupon = req.getSelectedCouponList().get(i);
//
//				if (selectedCoupon.getOrderItemId() == null) {
//					// 没有
//					log.info("已选中的优惠实例{}没有对应的订单项，可能是平台优惠券", selectedCoupon.getCouponInstId());
//					continue;
//				}
//
//				if (StringUtils.equals(couponOrderItemDTO.getOrderItemId(), selectedCoupon.getOrderItemId())) {
//					// 当前订单项ID  和  已选中的优惠券 对应的订单项ID 相同  计算订单项的优惠金额 并保存优惠券信息
//					for (int j = 0; j < allCouponInst.size(); j++) {
//
//						CouponInstDetailDTO currentCouponInst = allCouponInst.get(j);
//
//						// 该订单已绑定优惠券 累加减免金额
//						if (StringUtils.equals(selectedCoupon.getCouponInstId(), currentCouponInst.getCouponInstId())) {
//
//							log.info("订单项{} 绑定的优惠券实例ID{}优惠券ID{} 不满足条件", orderItem.getOrderItemId(), coupon.getCouponInstId(), coupon.getMktResId(), currentDiscount);
//
//							Double discount = calculateOrderDiscount(); // 减免金额
//
//							// 累加  优惠金额
//							discount += currentCouponInst.getDiscountValue();
//
//							// 保存订单项  绑定的优惠券信息
//							if (CollectionUtils.isEmpty(newOrderItem.getBingCouponList())) {
//								newOrderItem.setBingCouponList(Lists.newArrayList(currentCouponInst));
//							} else {
//								newOrderItem.getBingCouponList().add(currentCouponInst);
//							}
//							log.info("订单项{} 已绑定优惠券实例ID{}优惠券ID{}", newOrderItem.getOrderItemId(), currentCouponInst.getCouponInstId(),currentCouponInst.getMktResId());
//						}
//					}
//				}
//			}

//			newOrderItem.setDiscount(discount);
			log.info("订单项{} 已经优惠 {}", newOrderItem.getOrderItemId(), newOrderItem.getDiscount());
			respList.add(newOrderItem); // 保存信息
		});


		return respList;
	}

	/**
	 * 计算 优惠券 对订单的 减免金额
	 * @param coupon  优惠券实例详情
	 * @param orderItem  订单项
	 * @return 减免金额  小于等于0 表示不满足条件
	 */
	private Double calculateOrderDiscount(CouponInstDetailDTO coupon, OrderItemWithCouponDTO orderItem) {

		// 抵扣上限 （已经优惠的金额 + 当前订单的优惠金额 要 小于此值 才能用优惠券）
		Double maxValue = orderItem.getMaxValue(); // 取已选中的优惠券列表中最小的
		// 抵扣下限 （最终订单项金额要 大于等于 此值）
		Double minValue = orderItem.getMinValue(); // 取已选中的优惠券列表中最大的

		// 更新 抵扣 上限  下限 (  <= 0 是第一次更新  直接赋值）
		if (maxValue > coupon.getMaxValue() || maxValue <= 0) {
			maxValue = coupon.getMaxValue();
		}
		if (minValue < coupon.getMinValue() || minValue <= 0) {
			minValue = coupon.getMinValue();
		}

		// 订单总额
		Double orderAmount = (orderItem.getPrice() - orderItem.getPriceDiscount()) * orderItem.getNum();
		// 当前订单已经优惠的金额
		Double orderDiscount = orderItem.getDiscount();
		// 当前优惠券 优惠金额
		Double currentDiscount = coupon.getDiscountValue();
		// 结果 优惠金额
		Double resultDiscount = 0.0;

		// 不满足条件 直接返回
		if (orderAmount - orderDiscount < coupon.getRuleAmount()
				|| orderDiscount + currentDiscount > maxValue
				|| orderAmount - orderDiscount - currentDiscount < minValue) {
			return resultDiscount;
		}

		// 判断是否可以循环使用
		if (StringUtils.equals(coupon.getRecycleFlag(), RightsConst.CommonYesOrNo.YES.getCode())) {
			// 循环
			int times = 0; // 可以循环多少次
			Double t = ((orderAmount - orderDiscount) / coupon.getRuleAmount());
			times = t.intValue();
			if (times > 0) {
				for (int i = 0; i < times; i++) {
					if (orderAmount - orderDiscount >= coupon.getRuleAmount()
							&& orderDiscount + resultDiscount + currentDiscount <= maxValue
							&& orderAmount - orderDiscount - resultDiscount - currentDiscount >= minValue) {
						// 满足条件  累加优惠金额
						resultDiscount += currentDiscount;
					} else {
						// 不满足 退出循环
						break;
					}
				}
			}
			log.info("订单项{} 绑定的 可循环 优惠券实例ID{}优惠券ID{} 优惠的金额为：{}", orderItem.getOrderItemId(), coupon.getCouponInstId(), coupon.getMktResId(), resultDiscount);
		} else {
			// 非循环
			resultDiscount = currentDiscount;
			log.info("订单项{} 绑定的 非可循环 优惠券实例ID{}优惠券ID{} 优惠的金额为：{}", orderItem.getOrderItemId(), coupon.getCouponInstId(), coupon.getMktResId(), currentDiscount);
		}

		return resultDiscount;
	}

    /**
     * 计算订单金额是否满足 优惠券实例的抵扣规则
     * @param couponInstDetailDTO 优惠券实例
     * @param orderItemWithCouponDTO 订单项
     * @return
     */
	private boolean isConformRules(CouponInstDetailDTO couponInstDetailDTO, OrderItemWithCouponDTO orderItemWithCouponDTO) {
		boolean isValid = false;

		// 抵扣上限 （已经优惠的金额 + 当前订单的优惠金额 要 小于此值 才能用优惠券）
		Double maxValue = couponInstDetailDTO.getMaxValue(); // 取已选中的优惠券列表中最小的
		// 抵扣下限 （最终订单项金额要 大于等于 此值）
		Double minValue = couponInstDetailDTO.getMinValue(); // 取已选中的优惠券列表中最大的

		// 判断该订单是否有 已经相同的优惠券 （有就判断是否可以叠加使用）
        if (!CollectionUtils.isEmpty(orderItemWithCouponDTO.getBingCouponList())) {
            for (CouponInstDetailDTO bindCouponInst: orderItemWithCouponDTO.getBingCouponList()) {

            	// 更新 抵扣 上限  下限
				if (maxValue > bindCouponInst.getMaxValue()) {
					maxValue = bindCouponInst.getMaxValue();
				}
				if (minValue < bindCouponInst.getMinValue()) {
					minValue = bindCouponInst.getMinValue();
				}

            	if (StringUtils.equals(couponInstDetailDTO.getMktResId(), bindCouponInst.getMktResId())) {
                	if (StringUtils.equals(bindCouponInst.getReuseFlag(), RightsStatusConsts.REUSE_FLAG_NO)) {
                        // 不能叠加使用
						log.info("优惠实例ID{} 优惠券ID{}不能叠加使用", bindCouponInst.getCouponInstId(), bindCouponInst.getMktResId());
//						return false;
						return isValid;
                    }
                }
            }
        }

		// 1、计算是否满足
		Double orderCount = 0.00; // 订单总金额

        // 产品价格
        Double price = orderItemWithCouponDTO.getPrice();
        // 产品价格直减金额
        Double priceDiscount = orderItemWithCouponDTO.getPriceDiscount();

        orderCount = (price - priceDiscount) * orderItemWithCouponDTO.getNum();

        Double orderDiscount = orderItemWithCouponDTO.getDiscount(); // 当前订单已经优惠啦多少钱（已经选中有优惠券的才不为0）

		// 抵扣条件 （订单额大于此值 才能用优惠券）
		Double ruleAmount = couponInstDetailDTO.getRuleAmount();
		// 抵扣金额 （当前优惠券的 优惠金额）
		Double discountValue = couponInstDetailDTO.getDiscountValue();
//        // 抵扣上限 （已经优惠的金额 + 当前订单的优惠金额 要 小于此值 才能用优惠券）
//		Double maxValue = couponInstDetailDTO.getMaxValue();
//		// 抵扣下限 （最终订单项金额要 大于等于 此值）
//		Double minValue = couponInstDetailDTO.getMinValue();

		// 订单金额 - 已经优惠的金额  是否 大于 抵扣条件
		isValid = orderCount - orderItemWithCouponDTO.getDiscount() >= ruleAmount;
		if (!isValid) {
			// 订单金额不满足
			log.info("订单金额{}不能满足优惠实例{}的抵扣条件{}", orderCount, couponInstDetailDTO.getCouponInstId(), ruleAmount);
			return false;
		}
		// 已经优惠的金额 + 当前订单的优惠金额 是否小于 最小的抵扣上限
		isValid = orderDiscount + discountValue <= maxValue;
		if (!isValid) {
			// 订单总优惠金额
			log.info("订单总优惠金额{}不能满足优惠实例{}的最小的抵扣上限{}", orderDiscount + discountValue, couponInstDetailDTO.getCouponInstId(), maxValue);
			return false;
		}

		// 订单金额 - 已经优惠的金额 - 当前订单的优惠金额 是否大于 最大的抵扣下限
		isValid = orderCount - orderDiscount - discountValue >= minValue;
		if (!isValid) {
			// 订单金额不满足
			log.info("订单金额{}减去优惠金额后不能满足优惠实例{}的最大的抵扣下限{}", orderCount, couponInstDetailDTO.getCouponInstId(), minValue);
			return false;
		}

		isValid = true;
		return isValid;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@Async
	public ResultVO autoPushCoupon(AutoPushCouponReq autoPushCouponReq) {
		log.info("CouponInstServiceImpl.autoPushCoupon --> autoPushCouponReq={}",JSON.toJSON(autoPushCouponReq));
		List<String> merchantIds = autoPushCouponReq.getMerchantIds();
		List<MktResCoupon> mktResCoupons = mktResCouponManager.queryCouponByActId(autoPushCouponReq.getMarketingActivityId());
		if (mktResCoupons.size() <= 0) {
			return ResultVO.success("该活动不存在优惠券，无需推送");
		}
		try {
			for (MktResCoupon mktResCoupon : mktResCoupons) {
				CouponSupplyRuleRespDTO couponSupplyRuleRespDTO = couponSupplyRuleManager.queryCouponSupplyRuleOne(mktResCoupon.getMktResId());
				Long maxNum = couponSupplyRuleRespDTO.getMaxNum();
				Long supplyNum = couponSupplyRuleRespDTO.getSupplyNum();
				if (maxNum < supplyNum * merchantIds.size()) {
					throw new Exception(mktResCoupon.getMktResId()+"存在优惠券的总数小于参与商家的数量");
				}
				CouponEffExpRuleRespDTO couponEffExpRuleRespDTO = couponEffExpRuleManager.queryCouponEffExpRuleOne(mktResCoupon.getMktResId());
				CouponDiscountRuleRespDTO couponDiscountRuleRespDTO = couponDiscountRuleManager.queryCouponDiscountRuleOne(mktResCoupon.getMktResId());
				for (int i = 0; i < supplyNum; i++) {
					for (String merchantId : merchantIds) {
						/**推送优惠券*/
						CouponInst couponInst = new CouponInst();
						Long biggestInstNbr = couponInstManager.queryBiggestInstNbr();
						biggestInstNbr = biggestInstNbr == null ? 0 : biggestInstNbr;
						couponInst.setCouponInstNbr(String.valueOf(biggestInstNbr + 1));
						couponInst.setMktResId(mktResCoupon.getMktResId());
						couponInst.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNUSED);
						couponInst.setStatusDate(new Date());
						/**自动推送的创建人为-1*/
						couponInst.setCreateStaff("-1");
						couponInst.setUpdateStaff("-1");
						couponInst.setCreateDate(new Date());
						couponInst.setUpdateDate(new Date());
						couponInst.setEffDate(couponEffExpRuleRespDTO.getEffDate());
						couponInst.setExpDate(couponEffExpRuleRespDTO.getExpDate());
						couponInst.setCustAcctId(merchantId);
						couponInst.setCouponAmount(couponDiscountRuleRespDTO.getDiscountValue().longValue());
						couponInstManager.insertCouponInst(couponInst);

						/**优惠券领取记录*/
						String couponInstId = couponInst.getCouponInstId();
						CouponInstProvRec couponInstProvRec = new CouponInstProvRec();
						couponInstProvRec.setCouponInstId(couponInstId);
						couponInstProvRec.setProvObjId(merchantId);
						couponInstProvRec.setCreateDate(new Date());
						couponInstProvRec.setUpdateDate(new Date());
						couponInstProvRec.setProvDate(new Date());
						couponInstProvRec.setStatusDate(new Date());
						couponInstProvRec.setProvDesc("主动推送优惠券发放");
						couponInstProvRec.setCreateStaff("-1");
						couponInstProvRec.setUpdateStaff("-1");
						couponInstProvRec.setProvChannelType("1000");
						couponInstProvRec.setProvObjType(RightsStatusConsts.PROVOBJ_TYPE_CUS);
						couponInstProvRec.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNUSED);
						couponInstProvRecManager.insertCouponInstProvRec(couponInstProvRec);
					}
				}
			}
		} catch (Exception e) {
			return ResultVO.error("存在优惠券的数量小于商家的数量，无法自动推送");
		}
		return ResultVO.success();
	}

	@Override
//	@Transactional(rollbackFor = Exception.class)
	public ResultVO receiveRights(SaveRightsRequestDTO saveRightsRequestDTO) {
		log.info("CouponInstServiceImpl receiveRights saveRightsRequestDTO={}",JSON.toJSON(saveRightsRequestDTO));
		Long supplyNum = saveRightsRequestDTO.getSupplyNum();
		if (supplyNum == null || 0 == supplyNum) {
			return ResultVO.error("领取数量不可用");
		}
		CouponSupplyRule couponSupplyRule = couponSupplyRuleManager.selectOneCouponSupplyRule(saveRightsRequestDTO.getMktResId());
		log.info("CouponInstServiceImpl receiveRights couponSupplyRuleManager.selectOneCouponSupplyRule--> couponSupplyRule={}",JSON.toJSON(couponSupplyRule));
		if (couponSupplyRule == null) {
			return ResultVO.error("优惠券领取规则不存在");
		}
		//判断领取时间可用
		if (new Date().after(couponSupplyRule.getEndTime()) || new Date().before(couponSupplyRule.getBeginTime())) {
			return ResultVO.error("优惠券不在领取时间范围");
		}
		//当优惠券领取限制为是的时候，判断可领取数量是否可用
		if(RightsConst.LimitFlg.LIMIT_FLG_1.getType().equals(couponSupplyRule.getSupplyLimitFlg()) && couponSupplyRule.getSupplyNum()<supplyNum){
			return ResultVO.error("领取数量大于领取规则可领取数量");
		}
		//当优惠券总量限制为否的时候，判断券总量是否可用
		if(RightsConst.LimitFlg.LIMIT_FLG_1.getType().equals(couponSupplyRule.getNumLimitFlg())){
			Integer sum = couponInstManager.queryAreadyReceiveNum(saveRightsRequestDTO.getMktResId());
			if (sum + supplyNum > couponSupplyRule.getMaxNum()) {
				return ResultVO.error("优惠券库存不足");
			}
		}
		//当优惠券领取总量限制为否的时候，判断领取总量是否可用
		if (RightsConst.LimitFlg.LIMIT_FLG_1.getType().equals(couponSupplyRule.getSupplyLimitFlg())) {
			Integer receiveNum = couponInstManager.queryCustAreadyReceiveNum(saveRightsRequestDTO.getMktResId(), saveRightsRequestDTO.getCustNum());
			if (couponSupplyRule.getSupplyNum() < receiveNum + supplyNum) {
				return ResultVO.error("用户领取数量达到领取上线");
			}
		}
		CouponEffExpRuleRespDTO couponEffExpRuleRespDTO = couponEffExpRuleManager.queryCouponEffExpRuleOne(saveRightsRequestDTO.getMktResId());
		log.info("CouponInstServiceImpl receiveRights couponEffExpRuleManager.queryCouponEffExpRuleOne--> couponEffExpRuleRespDTO={}",JSON.toJSON(couponEffExpRuleRespDTO));
		if(couponEffExpRuleRespDTO == null){
			return ResultVO.error("优惠券生失效规则不存在");
		}
		CouponDiscountRuleRespDTO couponDiscountRuleRespDTO = couponDiscountRuleManager.queryCouponDiscountRuleOne(saveRightsRequestDTO.getMktResId());
		log.info("CouponInstServiceImpl receiveRights couponDiscountRuleManager.queryCouponDiscountRuleOne--> couponDiscountRuleRespDTO={}",JSON.toJSON(couponDiscountRuleRespDTO));
		if(couponDiscountRuleRespDTO == null){
			return ResultVO.error("优惠券抵扣规则不存在");
		}
		for (int i = 0; i < supplyNum; i++) {
			//领取实例
			CouponInst couponInst = new CouponInst();
			Long biggestInstNbr = couponInstManager.queryBiggestInstNbr();
			biggestInstNbr = biggestInstNbr == null ? 0 : biggestInstNbr;
			couponInst.setCouponInstNbr(String.valueOf(biggestInstNbr + 1));
			couponInst.setMktResId(saveRightsRequestDTO.getMktResId());
			couponInst.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNUSED);
			couponInst.setStatusDate(new Date());
			couponInst.setCreateStaff(saveRightsRequestDTO.getCustNum());
			couponInst.setUpdateStaff(saveRightsRequestDTO.getCustNum());
			couponInst.setCreateDate(new Date());
			couponInst.setUpdateDate(new Date());
			couponInst.setEffDate(couponEffExpRuleRespDTO.getEffDate());
			couponInst.setExpDate(couponEffExpRuleRespDTO.getExpDate());
			couponInst.setCustAcctId(saveRightsRequestDTO.getCustNum());
			couponInst.setCouponAmount(couponDiscountRuleRespDTO.getDiscountValue().longValue());
			couponInstManager.insertCouponInst(couponInst);
			//优惠券领取记录
			CouponInstProvRec couponInstProvRec = new CouponInstProvRec();
			couponInstProvRec.setCouponInstId(couponInst.getCouponInstId());
			couponInstProvRec.setProvObjId(saveRightsRequestDTO.getCustNum());
			couponInstProvRec.setCreateDate(new Date());
			couponInstProvRec.setUpdateDate(new Date());
			couponInstProvRec.setProvDate(new Date());
			couponInstProvRec.setStatusDate(new Date());
			couponInstProvRec.setProvDesc("领取优惠券");
			couponInstProvRec.setCreateStaff(saveRightsRequestDTO.getCustNum());
			couponInstProvRec.setUpdateStaff(saveRightsRequestDTO.getCustNum());
			couponInstProvRec.setProvChannelType(saveRightsRequestDTO.getProvChannelType());
			couponInstProvRec.setProvObjType(RightsStatusConsts.PROVOBJ_TYPE_CUS);
			couponInstProvRec.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNUSED);
			couponInstProvRecManager.insertCouponInstProvRec(couponInstProvRec);
		}
		return ResultVO.success("领取成功");
	}
}