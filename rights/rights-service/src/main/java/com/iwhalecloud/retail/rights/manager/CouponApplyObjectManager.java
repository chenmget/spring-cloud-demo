package com.iwhalecloud.retail.rights.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.rights.common.RightsConst;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import com.iwhalecloud.retail.rights.dto.request.CommonQueryByMktResIdReqDTO;
import com.iwhalecloud.retail.rights.dto.request.CouponApplyObjectGetReq;
import com.iwhalecloud.retail.rights.dto.request.CouponApplyObjectListReq;
import com.iwhalecloud.retail.rights.dto.request.QueryCouponByProductAndActivityIdReq;
import com.iwhalecloud.retail.rights.dto.response.CouponApplyObjectRespDTO;
import com.iwhalecloud.retail.rights.entity.CouponApplyObject;
import com.iwhalecloud.retail.rights.mapper.CouponApplyObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class CouponApplyObjectManager extends ServiceImpl<CouponApplyObjectMapper,CouponApplyObject> {
    @Resource
    private CouponApplyObjectMapper couponApplyObjectMapper;
    
    
    /**
	 * 根据权益主键查询权益使用规则
	 * @param dto
	 * @return
	 */
    public Page<CouponApplyObjectRespDTO> queryCouponAppyObject(CommonQueryByMktResIdReqDTO dto){
    	Page<CommonQueryByMktResIdReqDTO> page = new Page<CommonQueryByMktResIdReqDTO>(dto.getPageNo(), dto.getPageSize());
    	return couponApplyObjectMapper.queryCouponAppyObject(page, dto);
    }

	/**
	 * 根据条件查询  优惠适用对象
	 * @param req
	 * @return
	 */
	public CouponApplyObject getCouponApplyObject(CouponApplyObjectGetReq req) {
		QueryWrapper queryWrapper = new QueryWrapper();
		boolean hasParam = false;
		if (!StringUtils.isEmpty(req.getApplyObjectId())) {
			hasParam = true;
			queryWrapper.eq(CouponApplyObject.FieldNames.applyObjectId.getTableFieldName(), req.getApplyObjectId());
		}
		if (!StringUtils.isEmpty(req.getMktResId())) {
			hasParam = true;
			queryWrapper.eq(CouponApplyObject.FieldNames.mktResId.getTableFieldName(), req.getMktResId());
		}
		if (!CollectionUtils.isEmpty(req.getObjIdList())) {
			hasParam = true;
			queryWrapper.in(CouponApplyObject.FieldNames.objId.getTableFieldName(), req.getObjIdList());
		}
		if (!hasParam) {
			// 避免查整表
			return null;
		}
		List<CouponApplyObject> list = couponApplyObjectMapper.selectList(queryWrapper);

		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据条件查询  优惠适用对象列表
	 * @param req
	 * @return
	 */
	public List<CouponApplyObject> listCouponApplyObject(CouponApplyObjectListReq req) {
		QueryWrapper queryWrapper = new QueryWrapper();
		boolean hasParam = false;
		if (!StringUtils.isEmpty(req.getMktResId())) {
			hasParam = true;
			queryWrapper.eq(CouponApplyObject.FieldNames.mktResId.getTableFieldName(), req.getMktResId());
		}
		if (!CollectionUtils.isEmpty(req.getObjIdList())) {
			hasParam = true;
			queryWrapper.in(CouponApplyObject.FieldNames.objId.getTableFieldName(), req.getObjIdList());
		}
		if (!hasParam) {
			// 避免查整表
			return null;
		}
		return couponApplyObjectMapper.selectList(queryWrapper);
	}

    /**
     * 根据优惠券id查询适用对象
     * @param mktResId
     * @return
     */
    public List<CouponApplyObject> queryCouponApplyObject(List<String> mktResId){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.in("MKT_RES_ID",mktResId);
        queryWrapper.eq("STATUS_CD", 1000);
        List<CouponApplyObject> couponApplyObjectList = couponApplyObjectMapper.selectList(queryWrapper);
        return couponApplyObjectList;
    }

	/**
	 * 删除优惠应用对象
	 * @param mktResId
	 * @param objId
	 * @return
	 */
	public Integer deleteCouponApplyObject(String  mktResId,String objId){
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(CouponApplyObject.FieldNames.mktResId.getTableFieldName(),mktResId);
		queryWrapper.eq(CouponApplyObject.FieldNames.objId.getTableFieldName(), objId);
		queryWrapper.eq(CouponApplyObject.FieldNames.objType.getTableFieldName(), RightsConst.CouponApplyObjType.PRODUCT.getType());
		return couponApplyObjectMapper.delete(queryWrapper);
	}

	/**
	 * 新增优惠券使用对象
	 * @param couponApplyObject
	 * @return
	 */
	public Integer addCouponApplyObject(CouponApplyObject couponApplyObject){
		couponApplyObject.setCreateDate(new Date());
		return couponApplyObjectMapper.insert(couponApplyObject);
	}

	public List<CouponApplyObject> queryCouponApplyObjectByCondition(QueryCouponByProductAndActivityIdReq req){
		QueryWrapper queryWrapper = new QueryWrapper<>();
		queryWrapper.in(CouponApplyObject.FieldNames.mktResId.getTableFieldName(),req.getMktResIds());
		queryWrapper.eq(CouponApplyObject.FieldNames.statusCd.getTableFieldName(), RightsStatusConsts.RIGHTS_STATUS_UNOBTAIN);
		queryWrapper.eq(CouponApplyObject.FieldNames.objId.getTableFieldName(), req.getProductId());
		queryWrapper.eq(CouponApplyObject.FieldNames.objType.getTableFieldName(), RightsConst.CouponApplyObjType.PRODUCT.getType());
		return couponApplyObjectMapper.selectList(queryWrapper);
	}


	/**
	 * 根据活动删除配置的优惠券适用对象
	 * @param mktResIds
	 * @return
	 */
	public Integer deleteCouponApplyObjectByAct(List<String> mktResIds){
		QueryWrapper<CouponApplyObject> queryWrapper = new QueryWrapper<>();
		queryWrapper.in(CouponApplyObject.FieldNames.mktResId.getTableFieldName(),mktResIds);
		queryWrapper.eq(CouponApplyObject.FieldNames.objType.getTableFieldName(),RightsConst.CouponApplyObjType.PRODUCT.getType());
		return couponApplyObjectMapper.delete(queryWrapper);
	}
}
